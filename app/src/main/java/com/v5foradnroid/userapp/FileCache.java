package com.v5foradnroid.userapp;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class FileCache {
    private File cacheDir;

    public FileCache(Context context) {
        createDirectoryAndSaveFile();
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.cacheDir = new File(Environment.getExternalStorageDirectory(), ".flexiload");
        } else {
            this.cacheDir = context.getCacheDir();
        }
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
        }
    }

    public File getFile(String str) {
        return new File(this.cacheDir, String.valueOf(str.hashCode()));
    }

    public void clear() {
        File[] listFiles = this.cacheDir.listFiles();
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }

    private void createDirectoryAndSaveFile() {
        if (!new File(Environment.getExternalStorageDirectory() + "/.flexiload").exists()) {
            new File("/sdcard/.flexiload/").mkdirs();
        }
    }
}
