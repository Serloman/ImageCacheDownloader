package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

/**
 * Created by Serloman on 15/01/2015.
 */
public class LRUImageCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public LRUImageCache(){
        this((int) (Runtime.getRuntime().maxMemory() / 1024));
    }

    public LRUImageCache(int maxMemory){
        int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

    }


    // ImageCache Interface

    @Override
    public boolean hasImage(String url) {
        return null != getImage(url);
    }

    @Override
    public Bitmap getImage(String url) {
        return mMemoryCache.get(getKey(url));
    }

    @Override
    public void put(String url, Bitmap image) {
        synchronized (mMemoryCache) {

            String key = getKey(url);

            if(!hasImage(key))
                mMemoryCache.put(key, image);
        }
    }

    private String getKey(String url){
        return Uri.encode(url);
    }
}
