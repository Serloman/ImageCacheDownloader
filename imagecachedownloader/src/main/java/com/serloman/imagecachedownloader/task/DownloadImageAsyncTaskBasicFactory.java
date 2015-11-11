package com.serloman.imagecachedownloader.task;

import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.listener.DownloadImageListener;

/**
 * Created by Serloman on 20/01/2015.
 */
public class DownloadImageAsyncTaskBasicFactory implements DownloadImageAsyncTaskFactory {

    @Override
    public AbstractDownloadImageAsyncTask newTask(ImageCache cache, DownloadImageListener listener) {
        return new DownloadImageAsyncTask(cache, listener);
    }
}
