package com.v5foradnroid.userapp;

import android.app.Activity;
import android.os.Bundle;


public class Develop extends Activity {

    public static String DeV = getDeV(BuildConfig.WEB_URL);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.develop);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public static String getDeV(String s) {
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }

}
