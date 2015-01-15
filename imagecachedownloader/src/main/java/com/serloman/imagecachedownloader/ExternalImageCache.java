package com.serloman.imagecachedownloader;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Serloman on 15/01/2015.
 */
@Deprecated
public class ExternalImageCache implements ImageCache {

    private Context context;

    public ExternalImageCache(Context context){
        this.context = context;
    }

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
}
