package com.serloman.imagecachedownloader;

import android.content.Context;

/**
 * Created by Serloman on 16/01/2015.
 */
public class DiskImageDownloader extends ImageDownloader {
    private static DiskImageDownloader ourInstance;

    public static DiskImageDownloader getInstance(Context context) {
        if(ourInstance==null)
        {
            synchronized (DiskImageDownloader.class){
                if(ourInstance==null){
                    ourInstance = new DiskImageDownloader(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private DiskImageDownloader(Context context) {
        this.cache = new DiskImageCache(context);
    }
}
