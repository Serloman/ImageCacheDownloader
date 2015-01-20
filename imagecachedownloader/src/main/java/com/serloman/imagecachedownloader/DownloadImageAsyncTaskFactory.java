package com.serloman.imagecachedownloader;

/**
 * Created by Serloman on 20/01/2015.
 */
public interface DownloadImageAsyncTaskFactory {
    public AbstractDownloadImageAsyncTask newTask(ImageCache cache, DownloadImageListener listener);
}
