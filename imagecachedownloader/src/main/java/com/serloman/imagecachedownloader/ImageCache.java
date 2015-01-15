package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;

/**
 * Created by Serloman on 15/01/2015.
 */
public interface ImageCache {
    public boolean hasImage(String url);
    public Bitmap getImage(String url);
    public void put(String url, Bitmap image);
}
