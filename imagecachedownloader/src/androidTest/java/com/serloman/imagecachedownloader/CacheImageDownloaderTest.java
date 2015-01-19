package com.serloman.imagecachedownloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Serloman on 15/01/2015.
 */
public class CacheImageDownloaderTest extends AndroidTestCase {

    public void testCaches(){
        doTestCache(new LRUImageCache());
        doTestCache(new DiskImageCache(getContext()));
        doTestCache(new MixImageCache(getContext()));
    }

    private void doTestCache(ImageCache cache){
        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        String key = "logo";

        cache.evictAll();

        assertFalse("Cache hasn't key object", cache.hasImage(key));
        cache.put(key, logo);
        assertTrue("Cache has key object", cache.hasImage(key));

        Bitmap cachedLogo = cache.getImage(key);
        assertEquals("Object cached is equal to original object", logo.getByteCount(), cachedLogo.getByteCount());
    }

/** /
    public void testImageDownload() throws Throwable{
        final CountDownLatch signal = new CountDownLatch(1);

        DownloadImageAsyncTaskTest testDownload = new DownloadImageAsyncTaskTest(new LRUImageCache(), new AssertDownloadImageListener(), signal);

        String url = "http://serlocorp.com/whatsart/images/whatsart_icon_app.png";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            testDownload.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        else
            testDownload.execute(url);

        try {
            signal.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(false);
    }

    private static class AssertDownloadImageListener implements DownloadImageListener{

        @Override
        public void downloadStarted() {
//            assertTrue(false);
        }

        @Override
        public void imageDownloaded(Bitmap image) {
//            assertTrue(false);
        }

        @Override
        public void imageError() {
//            assertTrue(false);
        }
    }

    private static class DownloadImageAsyncTaskTest extends DownloadImageAsyncTask{

        CountDownLatch signal;

        public DownloadImageAsyncTaskTest(ImageCache cache, DownloadImageListener listener, final CountDownLatch signal) {
            super(cache, listener);

            this.signal = signal;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            return super.doInBackground(urls);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            signal.countDown();
        }
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
