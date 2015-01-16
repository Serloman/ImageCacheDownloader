package com.serloman.imagecachedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Serloman on 15/01/2015.
 */
public class DiskImageCache extends LRUImageCache {

    private Context context;

    public DiskImageCache(Context context){
        this.context = context;
    }

    @Override
    public boolean hasImage(String url) {
        String key = getKey(url);
        if(isExternalStorageWritable()){
            File file = new File(context.getExternalCacheDir(), key);
            return file.exists();
        }else
            return super.hasImage(url);
    }

    @Override
    public Bitmap getImage(String url) {
        if(isExternalStorageWritable()){
            String key = getKey(url);
            File file;
            file = new File(context.getExternalCacheDir(), key);

            FileInputStream input;

            if(file.exists()){	// Cached file
                try {
                    input = new FileInputStream(file);
                    return BitmapFactory.decodeStream(input);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else
            return super.getImage(url);

        return null;
    }

    @Override
    public void put(String url, Bitmap image) {
        if(isExternalStorageWritable()) {
            String key = getKey(url);
            File file = new File(context.getExternalCacheDir(), key);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else
            super.put(url, image);
    }

    private String getKey(String url){
        return Uri.encode(url);
    }

    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
