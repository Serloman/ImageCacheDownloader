package com.serloman.imagecachedownloader;

/**
 * Created by Serloman on 20/01/2015.
 */
public class DownloadImageAsyncTaskBasicFactory implements DownloadImageAsyncTaskFactory {

    @Override
    public AbstractDownloadImageAsyncTask newTask(ImageCache cache, DownloadImageListener listener) {
        return new DownloadImageAsyncTask(cache, listener);
    }
}
