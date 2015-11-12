/*
 * Copyright (C) 2015 Sergio Lopez Marquez <serloman@gmail.com>
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
import android.os.AsyncTask;

import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.listener.DownloadImageListener;

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
