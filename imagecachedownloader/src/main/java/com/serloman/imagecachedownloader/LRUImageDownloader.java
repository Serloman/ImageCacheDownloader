package com.serloman.imagecachedownloader;

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
    }

}
