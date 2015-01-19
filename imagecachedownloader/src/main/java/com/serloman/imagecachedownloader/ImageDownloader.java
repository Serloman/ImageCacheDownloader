package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Serloman on 15/01/2015.
 */
public abstract class ImageDownloader {

    protected ImageCache cache;

    public ImageDownloader(){
        cache = new NoImageCache();
    }

    public void downloadImage(String url, DownloadImageListener listener){
        if(listener!=null)
        {
            Bitmap image = cache.getImage(url);

            if(null!=image)
            {
                listener.imageDownloaded(image);
                return;
            }

            DownloadImageAsyncTask task = new DownloadImageAsyncTask(cache, listener);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            else
                task.execute(url);
        }
    }


    private static class NoImageCache implements ImageCache{

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

        @Override
        public void remove(String url) {

        }

        @Override
        public void evictAll() {

        }
    }
}
