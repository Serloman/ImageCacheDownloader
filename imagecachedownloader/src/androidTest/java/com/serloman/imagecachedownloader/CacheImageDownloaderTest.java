package com.serloman.imagecachedownloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Serloman on 15/01/2015.
 */
public class CacheImageDownloaderTest extends AndroidTestCase {

    public void testLRUCache(){
        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        ImageCache cache = new LRUImageCache();
        String key = "logo";

        assertFalse("Cache hasn't key object", cache.hasImage(key));

        cache.put(key, logo);

        assertTrue("Cache has key object", cache.hasImage(key));

        Bitmap cachedLogo = cache.getImage(key);
        assertEquals("Object cached is equal to original object", logo, cachedLogo);
    }

    public void testDiskCache(){
        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        ImageCache cache = new DiskImageCache(getContext());
        String key = "logo";

        cache.put(key, logo);

        assertTrue("Cache has key object", cache.hasImage(key));

        Bitmap cachedLogo = cache.getImage(key);
        assertEquals("Object cached is equal to original object", logo.getByteCount(), cachedLogo.getByteCount());
    }




/** /
    public void testImageDownloader(){
        String urlImage = "http://serlocorp.com/whatsart/images/whatsart_icon_app.png";

        CacheImageDownloader downloader = CacheImageDownloader.getInstance();

        CountDownLatch signal = new CountDownLatch(1);
        TestDownloadImageListener listener = new TestDownloadImageListener(signal);
        downloader.downloadImage(urlImage, listener);

        CacheImageDownloader anotherDownloader = CacheImageDownloader.getInstance();

        assertEquals("Testing Singleton", downloader, anotherDownloader);
    }

    private static class TestDownloadImageListener implements DownloadImageListener{

        CountDownLatch signal;

        public TestDownloadImageListener(CountDownLatch signal){
            this.signal = signal;

            try {
                signal.await(10,  TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void downloadStarted() {

        }

        @Override
        public void imageDownloaded(Bitmap image) {
            assertNotNull("Testing image was downloaded", image);

            assertTrue(true);
            assertTrue(false);

            signal.countDown();
        }

        @Override
        public void imageError() {
            assertTrue(true);
            assertTrue(false);

            signal.countDown();
        }
    }
/**/

}
