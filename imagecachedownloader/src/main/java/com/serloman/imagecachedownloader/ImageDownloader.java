package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Serloman on 15/01/2015.
 */
public abstract class ImageDownloader {

    protected ImageCache cache;
    protected DownloadImageAsyncTaskFactory downloadImageTaskFactory;

    public ImageDownloader(){
        cache = new NoImageCache();
        downloadImageTaskFactory = new DownloadImageAsyncTaskBasicFactory();
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

            AbstractDownloadImageAsyncTask task = downloadImageTaskFactory.newTask(cache, listener);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            else
                task.execute(url);
        }
    }
}
