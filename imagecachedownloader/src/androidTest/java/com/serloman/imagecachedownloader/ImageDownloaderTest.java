package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Serloman on 21/01/2015.
 */
public class ImageDownloaderTest extends InstrumentationTestCase {

    protected void setUp() throws Exception{
        super.setUp();
    }

    protected void tearDown() throws Exception{
        super.tearDown();
    }

    public void testImageDownloads() throws Throwable {
        executeDownloadTest(LRUImageDownloader.getInstance());
    }

    private void executeDownloadTest(final ImageDownloader downloader) throws Throwable {
        final String url = "http://serlocorp.com/whatsart/images/whatsart_icon_app.png";
        final CountDownLatch signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            public void run() {

                DownloadImageListener listener = new DownloadImageListener(){

                    @Override
                    public void downloadStarted() {

                    }

                    @Override
                    public void imageDownloaded(Bitmap image) {
                        assertTrue(false);

                        signal.countDown();
                    }

                    @Override
                    public void imageError() {
                        assertTrue(false);

                        signal.countDown();
                    }
                };

                final DownloadImageAsyncTask task = new DownloadImageAsyncTask(new LRUImageCache(), listener){
                    @Override
                    protected Bitmap doInBackground(String... urls) {
                        try{
                            return super.doInBackground(urls);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }

                        return null;
                    }
                };
                task.execute(url);

//                downloader.downloadImage(url, listener);
            }
        });

        signal.await(10, TimeUnit.SECONDS);

    }



/** /
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
/**/
}
