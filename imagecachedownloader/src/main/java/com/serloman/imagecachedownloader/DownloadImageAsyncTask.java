package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Serloman on 19/01/2015.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private ImageCache cache;
    private DownloadImageListener listener;
    private String url;

    public DownloadImageAsyncTask(ImageCache cache, DownloadImageListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    protected Bitmap doInBackground(String... urls) {
        listener.downloadStarted();
        url = urls[0];

        return getImage(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result!=null) {
            cache.put(url, result);
            listener.imageDownloaded(result);
        }else
            listener.imageError();
    }


    private Bitmap getImage(String url) {

        Bitmap bitmap = null;
        if(url!=null)
        {
            final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            final HttpGet request = new HttpGet(url);

            try {
                HttpResponse response = client.execute(request);
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK)
                    bitmap = getRedirectedImage(response);
                else
                    bitmap = getImageFromResponse(response);

            } catch (IOException e) {
                request.abort();
            } finally {
                if (client != null)
                    client.close();
            }
        }

        return bitmap;
    }

    private Bitmap getRedirectedImage(HttpResponse response){
        Header[] headers = response.getHeaders("Location");

        if (headers != null && headers.length != 0) {
            String newUrl = headers[headers.length - 1].getValue();
            return getImage(newUrl);
        }

        return null;
    }

    private Bitmap getImageFromResponse(HttpResponse response){
        final HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream inputStream;
            try {
                inputStream = entity.getContent();

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                if (inputStream != null)
                    inputStream.close();

                entity.consumeContent();

                return image;
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return null;
    }
}
