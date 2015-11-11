package com.serloman.imagecachedownloader.cache;

import android.graphics.Bitmap;

/**
 * Created by Serloman on 15/01/2015.
 */
public interface ImageCache {
    boolean hasImage(String url);
    Bitmap getImage(String url);
    void put(String url, Bitmap image);
    void remove(String url);
    void evictAll();
}
