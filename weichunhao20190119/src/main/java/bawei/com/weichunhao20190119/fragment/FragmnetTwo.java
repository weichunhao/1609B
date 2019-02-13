package bawei.com.weichunhao20190119.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import bawei.com.weichunhao20190119.R;

public class FragmnetTwo extends Fragment {
    private FlyBanner flyBanner;
    //有地址是为了得到地址里面的图片地址（使用HiJson查看）
    private String path = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenttwo, container, false);
        flyBanner = view.findViewById(R.id.banner);
        return view;
    }
        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            //网络的
            List<String> list=new ArrayList<>();
            list.add("http://f.expoon.com/sub/news/2016/01/21/887844_230x162_0.jpg");
            list.add("http://f.expoon.com/sub/news/2016/01/21/580828_230x162_0.jpg");
            list.add("http://f.expoon.com/sub/news/2016/01/21/745921_230x162_0.jpg");
            flyBanner.setImagesUrl(list);

            //本地图片
//            List<Integer> lists = new ArrayList<>();
//            lists.add(R.mipmap.b);
//            lists.add(R.mipmap.c);
//            lists.add(R.mipmap.d);
//            flyBanner.setImages(lists);
    }
}
