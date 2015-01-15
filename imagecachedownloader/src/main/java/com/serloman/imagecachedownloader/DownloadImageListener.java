package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;

/**
 * Created by Serloman on 15/01/2015.
 */
public interface DownloadImageListener {
    public void imageDownloaded(Bitmap image);
    public void imageError();
}
