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

package com.serloman.imagecachedownloader.cache;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 15/01/2015.
 */
public class LRUImageCache implements ImageCache {

    public interface EvictListener{
        void imageEvicted(boolean evicted, String key, Bitmap oldImage, Bitmap newImage);
    }
    private List<EvictListener> listeners;

    private LruCache<String, Bitmap> mMemoryCache;

    public LRUImageCache(){
        this((int) (Runtime.getRuntime().maxMemory() / 1024));
    }

    public LRUImageCache(int maxMemory){
        int cacheSize = maxMemory / 4;

        listeners = new ArrayList<EvictListener>();

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.getByteCount() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);

                for(EvictListener listener : listeners)
                    listener.imageEvicted(evicted, key, oldValue, newValue);
            }
        };
    }


    // ImageCache Interface

    @Override
    public boolean hasImage(String url) {
        return null != getImage(url);
    }

    @Override
    public Bitmap getImage(String url) {
        return mMemoryCache.get(getKey(url));
    }

    @Override
    public void put(String url, Bitmap image) {
        synchronized (mMemoryCache) {

            String key = getKey(url);

            if(!hasImage(key))
                mMemoryCache.put(key, image);
        }
    }

    @Override
    public void remove(String url) {
        mMemoryCache.remove(getKey(url));
    }

    @Override
    public void evictAll() {
        mMemoryCache.evictAll();
    }

    private String getKey(String url){
        return Uri.encode(url);
    }

    public void addEvictListener(EvictListener listener){
        listeners.add(listener);
    }

    public void removeEvictListner(EvictListener listener){
        listeners.remove(listener);
    }
}
