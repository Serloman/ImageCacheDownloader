package com.serloman.imagecachedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.test.ActivityUnitTestCase;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Serloman on 21/01/2015.
 */
public class ImageDownloaderTest extends AndroidTestCase {

    public void testImageDownloads() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        final ListenerExecuted assertion = new ListenerExecuted();
        final MockListener listener = new MockListener(signal, getLogoMock(getContext()), assertion);
        final MockImageDownloader mockImageDownloader = new MockImageDownloader(getContext());
        final String mockUrl = "Download Bitmap is mocked. Url not needed";

        mockImageDownloader.downloadImage(mockUrl, listener);

        signal.await(10, TimeUnit.SECONDS);

        assertTrue(assertion.started);
        assertTrue(assertion.finished);
    }

    private static Bitmap getLogoMock(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
    }


    private static class MockImageDownloader extends ImageDownloader{

        public MockImageDownloader(Context context){
            this.cache = new LRUImageCache();
            this.downloadImageTaskFactory = new MockFactory(context);
        }
    }

    private static class MockFactory implements DownloadImageAsyncTaskFactory{

        private Context context;

        public MockFactory(Context context){
            this.context = context;
        }

        @Override
        public AbstractDownloadImageAsyncTask newTask(ImageCache cache, DownloadImageListener listener) {
            return new MockDownloadTask(context, cache, listener);
        }
    }

    private static class MockDownloadTask extends AbstractDownloadImageAsyncTask{

        private Context context;

        public MockDownloadTask(Context context, ImageCache cache, DownloadImageListener listener) {
            super(cache, listener);

            this.context = context;
        }

        @Override
        public Bitmap downloadImage(String url) {
            return getLogoMock(context);
        }
    };

    private static class MockListener implements DownloadImageListener{

        CountDownLatch signal;
        Bitmap expectedImage;
        ListenerExecuted assertion;

        public MockListener(final CountDownLatch signal, Bitmap expectedImage, ListenerExecuted assertion){
            this.signal = signal;

            this.expectedImage = expectedImage;
            this.assertion = assertion;
        }

        @Override
        public void downloadStarted() {
            assertion.started = true;
        }

        @Override
        public void imageDownloaded(Bitmap image) {
            assertion.finished = true;

            assertEquals(expectedImage.getByteCount(), image.getByteCount());

            signal.countDown();
        }

        @Override
        public void imageError() {
            assertion.finished = true;

            signal.countDown();
        }
    }

    private static class ListenerExecuted{
        boolean started;
        boolean finished;

        public ListenerExecuted(){
            this.started = false;
            this.finished = false;
        }
    }

}
