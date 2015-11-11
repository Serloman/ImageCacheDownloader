package com.serloman.imagecachedownloader.cache;

import android.graphics.Bitmap;

/**
 * Created by Serloman on 21/01/2015.
 */
public class NoImageCache  implements ImageCache{

    @Override
    public boolean hasImage(String url) {
        return false;
    }

    @Override
    public Bitmap getImage(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap image) {

    }

    @Override
    public void remove(String url) {

    }

    @Override
    public void evictAll() {

    }
}
