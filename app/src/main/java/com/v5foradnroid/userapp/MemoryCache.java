package com.v5foradnroid.userapp;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryCache {
    private static final String TAG = "MemoryCache";
    private Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap(10, 1.5f, true));
    private long limit = 1000000;
    private long size = 0;

    public MemoryCache() {
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public void setLimit(long j) {
        this.limit = j;
        StringBuilder append = new StringBuilder().append("MemoryCache will use up to ");
        double d = (double) this.limit;
        Double.isNaN(d);
        Log.i(TAG, append.append((d / 1024.0d) / 1024.0d).append("MB").toString());
    }

    public Bitmap get(String str) {
        try {
            if (!this.cache.containsKey(str)) {
                return null;
            }
            return this.cache.get(str);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(String str, Bitmap bitmap) {
        try {
            if (this.cache.containsKey(str)) {
                this.size -= getSizeInBytes(this.cache.get(str));
            }
            this.cache.put(str, bitmap);
            this.size += getSizeInBytes(bitmap);
            checkSize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkSize() {
        Log.i(TAG, "cache size=" + this.size + " length=" + this.cache.size());
        if (this.size > this.limit) {
            Iterator<Map.Entry<String, Bitmap>> it = this.cache.entrySet().iterator();
            while (it.hasNext()) {
                this.size -= getSizeInBytes((Bitmap) it.next().getValue());
                it.remove();
                if (this.size <= this.limit) {
                    break;
                }
            }
            Log.i(TAG, "Clean cache. New size " + this.cache.size());
        }
    }

    public void clear() {
        try {
            this.cache.clear();
            this.size = 0;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return (long) (bitmap.getRowBytes() * bitmap.getHeight());
    }
}
