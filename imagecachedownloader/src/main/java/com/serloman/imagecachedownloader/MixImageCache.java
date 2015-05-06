package com.serloman.imagecachedownloader;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Serloman on 18/01/2015.
 */
public class MixImageCache implements ImageCache, LRUImageCache.EvictListener{
    private Context context;
    private LRUImageCache memoryCache;
    private DiskImageCache diskCache;

    public MixImageCache(Context context){
        this.context = context;

        memoryCache = new LRUImageCache();
        diskCache = new DiskImageCache(context);

        memoryCache.addEvictListener(this);
    }

    @Override
    public boolean hasImage(String url) {
        return memoryCache.hasImage(url) || (diskCache.isExternalStorageWritable() && diskCache.hasImage(url));
    }

    @Override
    public Bitmap getImage(String url) {
        Bitmap image;

        image = memoryCache.getImage(url);

        if(null==image && diskCache.isExternalStorageWritable()){
            image = diskCache.getImage(url);

            if(image!=null)
                memoryCache.put(url, image);
        }

        return image;
    }

    @Override
    public void put(String url, Bitmap image) {
        diskCache.put(url, image);
        memoryCache.put(url, image);
    }

    @Override
    public void remove(String url) {
        memoryCache.remove(url);
        diskCache.remove(url);
    }

    @Override
    public void evictAll() {
        memoryCache.evictAll();
        diskCache.evictAll();
    }

    @Override
    public void imageEvicted(boolean evicted, String key, Bitmap oldImage, Bitmap newImage) {
        if(evicted && diskCache.isExternalStorageWritable())
            diskCache.put(key, oldImage);
    }
}
