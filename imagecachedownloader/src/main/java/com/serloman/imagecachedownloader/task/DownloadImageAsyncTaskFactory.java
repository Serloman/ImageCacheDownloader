package com.serloman.imagecachedownloader.task;

import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.listener.DownloadImageListener;
import com.serloman.imagecachedownloader.task.AbstractDownloadImageAsyncTask;

/**
 * Created by Serloman on 20/01/2015.
 */
public interface DownloadImageAsyncTaskFactory {
    AbstractDownloadImageAsyncTask newTask(ImageCache cache, DownloadImageListener listener);
}
