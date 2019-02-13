package bawei.com.weichunhao20190119;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bawei.com.weichunhao20190119.fragment.FragmnetOne;
import bawei.com.weichunhao20190119.fragment.FragmnetThree;
import bawei.com.weichunhao20190119.fragment.FragmnetTwo;

public class Main2Activity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup group;
    private ArrayList<Fragment> list = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer);
        listView = findViewById(R.id.list_text);
        imageView = findViewById(R.id.imageview);

        viewPager = findViewById(R.id.viewPage);
        group = findViewById(R.id.group);
        group.check(group.getChildAt(0).getId());
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Toast.makeText(Main2Activity.this,"已被打开",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Toast.makeText(Main2Activity.this,"已被关闭",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawer(Gravity.START);
            }
        });

        list.add(new FragmnetOne());
        list.add(new FragmnetTwo());
        list.add(new FragmnetThree());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.btu1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.btu2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.btu3:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                group.check(group.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



        final List<String> list_text = new ArrayList<>();
        list_text.add("首页");
        list_text.add("我的");
        list_text.add("视频");
        list_text.add("新闻");
        list_text.add("热点");

        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_text));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                viewPager.setCurrentItem(position);

                drawerLayout.closeDrawers();
            }
        });
    }
}
