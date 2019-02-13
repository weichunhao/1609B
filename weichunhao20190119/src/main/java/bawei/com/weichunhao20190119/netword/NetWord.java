package bawei.com.weichunhao20190119.netword;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWord {
    public  static  boolean isconnection(Context context){
        if (context!=null){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return  networkInfo.isConnected();
            }
        }
        return  false;

    }
}
