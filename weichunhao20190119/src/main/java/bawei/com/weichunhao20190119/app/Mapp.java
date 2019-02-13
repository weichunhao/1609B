package bawei.com.weichunhao20190119.app;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class Mapp extends Application {
    File file = new File(Environment.getExternalStorageDirectory()+"/"+"image");
    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(file))
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(configuration);
        // ImageLoader.getInstance().init(configuration);
    }
}
