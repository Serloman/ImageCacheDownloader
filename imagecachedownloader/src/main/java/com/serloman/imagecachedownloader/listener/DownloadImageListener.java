package com.serloman.imagecachedownloader.listener;

import android.graphics.Bitmap;

/**
 * Created by Serloman on 15/01/2015.
 */
public interface DownloadImageListener {
    public void downloadStarted();
    public void imageDownloaded(Bitmap image);
    public void imageError();
}
