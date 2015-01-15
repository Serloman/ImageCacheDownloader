package com.serloman.imagecachedownloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Serloman on 15/01/2015.
 */
public class ImageViewListener implements DownloadImageListener {

    private ImageView imageView;

    public ImageViewListener(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    public void imageDownloaded(Bitmap image) {
        imageView.setImageBitmap(image);
    }

    @Override
    public void imageError() {
        imageView.setImageBitmap(null);
    }
}
