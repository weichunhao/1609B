package bawei.com.weichunhao20190119.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bawei.com.weichunhao20190119.R;
import bawei.com.weichunhao20190119.bean.Goods;

public class MyAdapter extends BaseAdapter {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<Goods.DataBean> list ;
    private Context context;

    public MyAdapter(List<Goods.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case 0:
                ViewHolder1 viewHolder1 = new ViewHolder1();
                if (convertView == null){

                    convertView = View.inflate(context ,R.layout.mybase1,null);

                    viewHolder1.textView =convertView.findViewById(R.id.textView);
                    viewHolder1.imageView = convertView.findViewById(R.id.imageView);
                    convertView.setTag(viewHolder1);
                }else {
                    viewHolder1= (ViewHolder1) convertView.getTag();
                }

                viewHolder1.textView.setText(list.get(position).getNews_title());
                imageLoader.displayImage(list.get(position).getPic_url(),viewHolder1.imageView);
                break;



            case 1:

                ViewHolder2 viewHolder2 = new ViewHolder2();
                if(convertView == null){

                    convertView = View.inflate(context,R.layout.mybase2,null);

                    viewHolder2.tv1 = convertView.findViewById(R.id.tv1);

                    convertView.setTag(viewHolder2);
                }else{
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                }
                viewHolder2.tv1.setText(list.get(position).getNews_title());

                break;
        }
        return convertView;
    }
    class ViewHolder1{

        ImageView imageView;
        TextView textView;
    }

    class ViewHolder2{
        TextView tv1;
    }
}
