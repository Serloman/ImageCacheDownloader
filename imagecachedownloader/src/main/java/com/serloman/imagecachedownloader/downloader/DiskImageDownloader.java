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

package com.serloman.imagecachedownloader.downloader;

import android.content.Context;

import com.serloman.imagecachedownloader.cache.DiskImageCache;

/**
 * Created by Serloman on 16/01/2015.
 */
public class DiskImageDownloader extends ImageDownloader {
    private static DiskImageDownloader ourInstance;

    public static DiskImageDownloader getInstance(Context context) {
        if(ourInstance==null)
        {
            synchronized (DiskImageDownloader.class){
                if(ourInstance==null){
                    ourInstance = new DiskImageDownloader(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private DiskImageDownloader(Context context) {
        this.cache = new DiskImageCache(context);
    }
}
