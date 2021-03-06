package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;

import com.serloman.imagecachedownloader.cache.DiskImageCache;
import com.serloman.imagecachedownloader.cache.ImageCache;
import com.serloman.imagecachedownloader.cache.LRUImageCache;
import com.serloman.imagecachedownloader.cache.MixImageCache;

/**
 * Created by Serloman on 15/01/2015.
 */
public class ImageCacheTest extends AndroidTestCase {

    public void testCaches(){
        doTestCache(new LRUImageCache());
        doTestCache(new DiskImageCache(getContext()));
        doTestCache(new MixImageCache(getContext()));
    }

    private void doTestCache(ImageCache cache){
        Bitmap logo = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
        String key = "logo";

        cache.evictAll();

        assertFalse("Cache hasn't key object", cache.hasImage(key));
        cache.put(key, logo);
        assertTrue("Cache has key object", cache.hasImage(key));

        Bitmap cachedLogo = cache.getImage(key);
        assertEquals("Object cached is equal to original object", logo.getByteCount(), cachedLogo.getByteCount());
    }
}
