package com.v5foradnroid.userapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    ExecutorService executorService;
    FileCache fileCache;
    Handler handler = new Handler();
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap());
    MemoryCache memoryCache = new MemoryCache();
    final int stub_id = R.drawable.temp_img;

    public ImageLoader(Context context) {
        this.fileCache = new FileCache(context);
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void DisplayImage(String str, ImageView imageView) {
        this.imageViews.put(imageView, str);
        Bitmap bitmap = this.memoryCache.get(str);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        queuePhoto(str, imageView);
        imageView.setImageResource(R.drawable.temp_img);
    }

    private void queuePhoto(String str, ImageView imageView) {
        this.executorService.submit(new PhotosLoader(new PhotoToLoad(str, imageView)));
    }

    /* Foysal Tech && Ict Foysal */
    public Bitmap getBitmap(String str) {
        File file = this.fileCache.getFile(str);
        Bitmap decodeFile = decodeFile(file);
        if (decodeFile != null) {
            return decodeFile;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setInstanceFollowRedirects(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Utils.CopyStream(inputStream, fileOutputStream);
            fileOutputStream.close();
            httpURLConnection.disconnect();
            return decodeFile(file);
        } catch (Throwable th) {
            th.printStackTrace();
            if (!(th instanceof OutOfMemoryError)) {
                return null;
            }
            this.memoryCache.clear();
            return null;
        }
    }

    private Bitmap decodeFile(File file) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            FileInputStream fileInputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(fileInputStream, (Rect) null, options);
            fileInputStream.close();
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (true) {
                if (i2 / 2 < 70) {
                    break;
                } else if (i3 / 2 < 70) {
                    break;
                } else {
                    i2 /= 2;
                    i3 /= 2;
                    i *= 2;
                }
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i;
            FileInputStream fileInputStream2 = new FileInputStream(file);
            Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream2, (Rect) null, options2);
            fileInputStream2.close();
            return decodeStream;
        } catch (FileNotFoundException unused) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class PhotoToLoad {
        public ImageView imageView;
        public String url;

        public PhotoToLoad(String str, ImageView imageView2) {
            this.url = str;
            this.imageView = imageView2;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad2) {
            this.photoToLoad = photoToLoad2;
        }

        public void run() {
            try {
                if (!ImageLoader.this.imageViewReused(this.photoToLoad)) {
                    Bitmap access$000 = ImageLoader.this.getBitmap(this.photoToLoad.url);
                    ImageLoader.this.memoryCache.put(this.photoToLoad.url, access$000);
                    if (!ImageLoader.this.imageViewReused(this.photoToLoad)) {
                        ImageLoader.this.handler.post(new BitmapDisplayer(access$000, this.photoToLoad));
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean imageViewReused(PhotoToLoad photoToLoad) {
        String str = this.imageViews.get(photoToLoad.imageView);
        return str == null || !str.equals(photoToLoad.url);
    }

    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap bitmap2, PhotoToLoad photoToLoad2) {
            this.bitmap = bitmap2;
            this.photoToLoad = photoToLoad2;
        }

        public void run() {
            if (!ImageLoader.this.imageViewReused(this.photoToLoad)) {
                if (this.bitmap != null) {
                    this.photoToLoad.imageView.setImageBitmap(this.bitmap);
                } else {
                    this.photoToLoad.imageView.setImageResource(R.drawable.temp_img);
                }
            }
        }
    }

    public void clearCache() {
        this.memoryCache.clear();
        this.fileCache.clear();
    }
}
