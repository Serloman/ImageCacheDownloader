/*
 * Copyright (C) 2015 Sergio López Márquez <serloman@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.serloman.imagecachedownloader.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;

import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.listener.DownloadImageListener;

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
