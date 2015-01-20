package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Serloman on 20/01/2015.
 */
public abstract class AbstractDownloadImageAsyncTask  extends AsyncTask<String, Void, Bitmap> {

    private ImageCache cache;
    private DownloadImageListener listener;
    private String url;

    public AbstractDownloadImageAsyncTask(ImageCache cache, DownloadImageListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        listener.downloadStarted();
        url = urls[0];

        return downloadImage(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result!=null) {
            cache.put(url, result);
            listener.imageDownloaded(result);
        }else
            listener.imageError();
    }

    public abstract Bitmap downloadImage(String url);
}
