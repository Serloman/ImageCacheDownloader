package com.serloman.imagecachedownloader.downloader;

import android.content.Context;

import com.serloman.imagecachedownloader.task.DownloadImageAsyncTaskBasicFactory;
import com.serloman.imagecachedownloader.cache.MixImageCache;

/**
 * Created by Serloman on 18/01/2015.
 */
public class MixImageDownloader extends ImageDownloader{
    private static MixImageDownloader ourInstance;

    public static MixImageDownloader getInstance(Context context) {
        if(ourInstance==null)
        {
            synchronized (DiskImageDownloader.class){
                if(ourInstance==null){
                    ourInstance = new MixImageDownloader(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private MixImageDownloader(Context context) {
        this.cache = new MixImageCache(context);
        this.downloadImageTaskFactory = new DownloadImageAsyncTaskBasicFactory();
    }
}
