package bawei.com.weichunhao20190119.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bawei.com.weichunhao20190119.R;

import static com.andy.library.ChannelActivity.REQUEST_CODE;
import static com.andy.library.ChannelActivity.RESULT_JSON_KEY;

public class FragmnetOne extends Fragment {
    private TabLayout tabLayout;
    private Button button;
    private ViewPager viewPager;
    private View view;
    private  String  jsonStr="";
    private PagerAdapter pagerAdapter;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private  ArrayList<ChannelBean> channelBeanlist = new ArrayList<ChannelBean>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentone, container, false);
        tabLayout = view.findViewById(R.id.tab);
        button = view.findViewById(R.id.but);
        viewPager = view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initData();
    }
    private void initview() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String jsonArray = gson.toJson(channelBeanlist);
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                intent.putExtra(RESULT_JSON_KEY, jsonArray);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
        private void initData () {
            pagerAdapter = new PagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(pagerAdapter);
            channelBeanlist = new ArrayList<>();
            channelBeanlist.add(new ChannelBean("新闻", true));
            channelBeanlist.add(new ChannelBean("视频", true));
            channelBeanlist.add(new ChannelBean("焦点", true));
            channelBeanlist.add(new ChannelBean("音乐", false));
            channelBeanlist.add(new ChannelBean("娱乐", false));
            channelBeanlist.add(new ChannelBean("首页", false));
            pagerAdapter.setData(isSelectPD(channelBeanlist));
            //联动
            tabLayout.setupWithViewPager(viewPager);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String jsonArray = gson.toJson(channelBeanlist);
                    Intent intent = new Intent(getActivity(), ChannelActivity.class);
                    intent.putExtra(RESULT_JSON_KEY, jsonArray);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
        public List<ChannelBean> isSelectPD (List < ChannelBean > list) {
            List<ChannelBean> isok = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect()) {
                    isok.add(list.get(i));
                }
            }
            return isok;
        }
        @Override
        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
                //获取tab要展示的内容
                jsonStr = data.getStringExtra(RESULT_JSON_KEY);
                //清空之前的栏目
                tabLayout.removeAllTabs();
                Type type = new TypeToken<ArrayList<ChannelBean>>() {
                }.getType();
                channelBeanlist = new Gson().fromJson(jsonStr, type);
                //传给适配器
                pagerAdapter.setData(isSelectPD(channelBeanlist));
                tabLayout.setupWithViewPager(viewPager);
            }
        }
        private class PagerAdapter extends FragmentPagerAdapter {
            private List<ChannelBean> list = new ArrayList<>();

            public PagerAdapter(FragmentManager fm) {
                super(fm);
            }

            public void setData(List<ChannelBean> list) {
                this.list = list;
                notifyDataSetChanged();
            }

            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return new Fragmnet1();
                    case 1:
                        return new Fragmnet2();
                    case 2:
                        return new Fragmnet3();
                    default:
                        return new Fragmnet3();
                }
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position).getName();
            }

        }
    }

