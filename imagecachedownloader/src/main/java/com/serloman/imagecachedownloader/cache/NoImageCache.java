/*
 * Copyright (C) 2015 Sergio L�pez M�rquez <serloman@gmail.com>
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

/**
 * Created by Serloman on 21/01/2015.
 */
public class NoImageCache  implements ImageCache{

    @Override
    public boolean hasImage(String url) {
        return false;
    }

    @Override
    public Bitmap getImage(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap image) {

    }

    @Override
    public void remove(String url) {

    }

    @Override
    public void evictAll() {

    }
}
