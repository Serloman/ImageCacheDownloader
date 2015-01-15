package com.serloman.imagecachedownloader;

/**
 * Created by Serloman on 15/01/2015.
 */
public class CacheImageDownloader extends ImageDownloader{
    private static CacheImageDownloader ourInstance = new CacheImageDownloader();

    public static CacheImageDownloader getInstance() {
        return ourInstance;
    }

    private CacheImageDownloader() {
        this.cache = new LRUImageCache();
    }

}
