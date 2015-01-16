package com.serloman.imagedowloaderapptest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import com.serloman.imagecachedownloader.CacheImageDownloader;
import com.serloman.imagecachedownloader.DiskImageDownloader;
import com.serloman.imagecachedownloader.ImageDownloader;
import com.serloman.imagecachedownloader.ImageViewListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ImageView imageView = (ImageView)rootView.findViewById(R.id.imageViewTest);

            ImageDownloader downloader;
            downloader = DiskImageDownloader.getInstance(getActivity());
//            downloader = CacheImageDownloader.getInstance();
            downloader.downloadImage("http://fc00.deviantart.net/fs71/f/2015/013/3/c/3c026edbe356b22c802e7be0db6fbd0b-d8dt0go.jpg", new ImageViewListener(imageView){
                @Override
                public void imageError() {
                    super.imageError();

                    Toast.makeText(getActivity(),"Error downloading image", Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }
    }
}
