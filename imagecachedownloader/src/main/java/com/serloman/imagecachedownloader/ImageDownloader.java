package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;

/**
 * Created by Serloman on 15/01/2015.
 */
public abstract class ImageDownloader {

    protected ImageCache cache;

    public void downloadImage(String url, DownloadImageListener listener){
        Bitmap image = cache.getImage(url);

        if(null!=image)
        {
            listener.imageDownloaded(image);
            return;
        }

        DownloadImageTask task = new DownloadImageTask(cache, listener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        else
            task.execute(url);
    };

    private static Bitmap downloadBitmap(String url) {

        Bitmap bitmap = null;
        if(url!=null)
        {
            final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            final HttpGet request = new HttpGet(url);

            try {
                HttpResponse response = client.execute(request);
                final int statusCode =
                        response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Header[] headers = response.getHeaders("Location");

                    if (headers != null && headers.length != 0) {
                        String newUrl = headers[headers.length - 1].getValue();
                        // call again with new URL

                        return downloadBitmap(newUrl);
                    } else {
                        return null;
                    }
                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        inputStream = entity.getContent();

                        // do your work here
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                request.abort();
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        }

        return bitmap;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageCache cache;
        private DownloadImageListener listener;
        private String url;

        public DownloadImageTask(ImageCache cache, DownloadImageListener listener) {
            this.cache = cache;
            this.listener = listener;
        }

        protected Bitmap doInBackground(String... urls) {
            url = urls[0];

            return downloadBitmap(url);
        }

        protected void onPostExecute(Bitmap result) {
            cache.put(url, result);
            listener.imageDownloaded(result);
        }

    }
}
