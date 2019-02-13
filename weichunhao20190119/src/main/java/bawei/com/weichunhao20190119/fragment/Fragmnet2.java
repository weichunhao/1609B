package bawei.com.weichunhao20190119.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xlistviewlibrary.utils.NetWordUtils;
import com.bwie.xlistviewlibrary.view.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bawei.com.weichunhao20190119.R;
import bawei.com.weichunhao20190119.bean.Goods;
import bawei.com.weichunhao20190119.dao.UserDao;
import bawei.com.weichunhao20190119.netword.NetWord;

public class Fragmnet2 extends Fragment {
    String url = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    int page;
    private XListView xListView;
    List<Goods.DataBean> list = new ArrayList<>(); //大集合
    private MAdapter mAdapter;
    private ImageLoader imageLoaderInstances;
    private UserDao dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);
        xListView = view.findViewById(R.id.xlv);
        dao = new UserDao(getActivity());
        xListView.setPullLoadEnable(true);
        imageLoaderInstances = ImageLoader.getInstance();
        mAdapter = new MAdapter();
        xListView.setAdapter(mAdapter);
        initData(page); //请求网络数据

        //设置上下拉的逻辑
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                page = 0;
                initData(page);

            }

            //上拉加载更多;
            @Override
            public void onLoadMore() {
                page++;
                initData(page);
            }
        });
        return view;
    }
    private void initData(int page) {
//        String mUrl = baseUrl + page;
//        new MAsycnTask().execute(mUrl);
        //调用网络判断
        boolean connection = NetWord.isconnection(getActivity());
        if(connection){//有网
            String urls=url+page;
            new MAsycnTask().execute(urls);
        }else {
            //没有网
            Toast.makeText(getActivity(),"请检查网络再试",Toast.LENGTH_LONG).show();
            //根据url地址。从数据库中获取数据
            String jsonData = dao.queryData(url);//调取数据库查询的方法
            if (!"".equals(jsonData)){
                //解析gson
                Gson gson=new Gson();
                Goods myBean = gson.fromJson(jsonData, Goods.class);
                List<Goods.DataBean> data = myBean.getData();
                list.addAll(data);


            }
        }

    }
    private class MAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewItem = View.inflate(getActivity(), R.layout.mybase1, null);
            TextView textView = (TextView) viewItem.findViewById(R.id.textView);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            textView.setText(list.get(i).getNews_title());
            imageLoaderInstances.displayImage(list.get(i).getPic_url(), imageView);


            return viewItem;
        }
    }
    class  MAsycnTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return NetWordUtils.getNetjson(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Goods goods = gson.fromJson(s, Goods.class);
            List<Goods.DataBean> data = goods.getData();

            list.addAll(data);
            mAdapter.notifyDataSetChanged();
            uiComplete();//让刷新头和刷新底部隐藏;


        }
    }
    private void uiComplete() {
        xListView.setRefreshTime("刚刚");
        xListView.stopRefresh();//隐藏刷新头部
        xListView.stopLoadMore();//隐藏刷新脚部


    }
}
