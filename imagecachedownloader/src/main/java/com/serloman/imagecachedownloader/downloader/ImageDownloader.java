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

package com.serloman.imagecachedownloader.downloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import com.serloman.imagecachedownloader.task.AbstractDownloadImageAsyncTask;
import com.serloman.imagecachedownloader.task.DownloadImageAsyncTaskBasicFactory;
import com.serloman.imagecachedownloader.task.DownloadImageAsyncTaskFactory;
import com.serloman.imagecachedownloader.listener.DownloadImageListener;
import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.cache.NoImageCache;

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
