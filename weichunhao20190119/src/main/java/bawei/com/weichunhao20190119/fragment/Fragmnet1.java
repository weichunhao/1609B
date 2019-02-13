package bawei.com.weichunhao20190119.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bawei.com.weichunhao20190119.R;
import bawei.com.weichunhao20190119.adapter.MyAdapter;
import bawei.com.weichunhao20190119.bean.Goods;
import bawei.com.weichunhao20190119.dao.UserDao;
import bawei.com.weichunhao20190119.netword.NetWord;

public class Fragmnet1 extends Fragment {
    private String url ="http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private PullToRefreshListView pullToRefreshListView;
    private  int page;
    List<Goods.DataBean> list = new ArrayList<>();
    private UserDao dao;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);
        dao = new UserDao(getActivity());

        pullToRefreshListView = view.findViewById(R.id.pv);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initpv();
        //请求网络
        data(page);

    }
    private void data(int page) {
        //调用网络判断
        boolean connection = NetWord.isconnection(getActivity());
        if(connection){//有网
            String urls=url+page;
            new MyTask().execute(urls);
        }else {
            //没有网
            Toast.makeText(getActivity(),"请检查网络再试",Toast.LENGTH_LONG).show();
            //根据url地址。从数据库中获取数据
            String jsonData = dao.queryData(url);//调取数据库查询的方法
            if (!"".equals(jsonData)){
                //解析gson
                Gson gson;
                gson = new Gson();
                Goods myBean = gson.fromJson(jsonData, Goods.class);
                List<Goods.DataBean> data = myBean.getData();
                list.addAll(data);
                getAdapter();
                //关闭上下拉刷新
                pullToRefreshListView.onRefreshComplete();
            }
        }
    }
    class MyTask extends AsyncTask<String ,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String str="";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(1000);
                urlConnection.setConnectTimeout(1000);
                urlConnection.setRequestMethod("GET");
                if(urlConnection.getResponseCode()==200){
                    InputStream inputStream = urlConnection.getInputStream();
                    str = getdata(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //将请求下来的json数据缓存在数据库中
            dao.add(url,s);
            Gson gson=new Gson();
            Goods myBean = gson.fromJson(s, Goods.class);
            List<Goods.DataBean> data = myBean.getData();
            list.addAll(data);
            getAdapter();
            //关闭上下拉刷新
            pullToRefreshListView.onRefreshComplete();
        }
    }
    private void initpv() {

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                list.clear();
                data(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page++;
                data(page);

            }
        });
    }
    public void getAdapter(){
        myAdapter= new MyAdapter(list,getActivity());
        pullToRefreshListView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    public String getdata(InputStream stream){

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String  tamp ;
        try {
            while ((tamp=reader.readLine())!=null){
                stringBuilder.append(tamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
