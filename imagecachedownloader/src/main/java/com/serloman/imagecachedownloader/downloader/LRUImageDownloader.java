package com.serloman.imagecachedownloader.downloader;

import com.serloman.imagecachedownloader.task.DownloadImageAsyncTaskBasicFactory;
import com.serloman.imagecachedownloader.cache.LRUImageCache;

/**
 * Created by Serloman on 15/01/2015.
 */
public class LRUImageDownloader extends ImageDownloader{
    private static LRUImageDownloader ourInstance = new LRUImageDownloader();

    public static LRUImageDownloader getInstance() {
        return ourInstance;
    }

    private LRUImageDownloader() {
        this.cache = new LRUImageCache();
        this.downloadImageTaskFactory = new DownloadImageAsyncTaskBasicFactory();
    }

}
