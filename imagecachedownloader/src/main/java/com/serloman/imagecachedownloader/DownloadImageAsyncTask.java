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
public class DownloadImageAsyncTask extends AbstractDownloadImageAsyncTask {

    public DownloadImageAsyncTask(ImageCache cache, DownloadImageListener listener) {
        super(cache, listener);
    }

    @Override
    public Bitmap downloadImage(String url) {

        Bitmap image = null;
        if(url!=null)
        {
            final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            final HttpGet request = new HttpGet(url);

            try {
                HttpResponse response = client.execute(request);
                image = tryDownloadImage(response);
            } catch (IOException e) {
                request.abort();
            } finally {
                client.close();
            }
        }

        return image;
    }

    private Bitmap tryDownloadImage(HttpResponse response) throws IOException {
        final int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK)
            return getRedirectedImage(response);

        return getImageFromResponse(response);
    }

    private Bitmap getRedirectedImage(HttpResponse response){
        Header[] headers = response.getHeaders("Location");

        if (headers != null && headers.length != 0) {
            String newUrl = headers[headers.length - 1].getValue();
            return downloadImage(newUrl);
        }

        return null;
    }

    private Bitmap getImageFromResponse(HttpResponse response){
        final HttpEntity entity = response.getEntity();

        if (entity != null) {
            try {
                return decodeImage(entity);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return null;
    }

    private Bitmap decodeImage(HttpEntity entity) throws IOException {
        InputStream inputStream = entity.getContent();

        Bitmap image = BitmapFactory.decodeStream(inputStream);

        if (inputStream != null)
            inputStream.close();

        entity.consumeContent();

        return image;
    }


}
