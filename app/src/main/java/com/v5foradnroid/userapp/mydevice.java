package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class mydevice extends Activity {
    private static final String TAG_SUCCESS = "success";
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private EditText mobile_number;
    private ProgressDialog pDialog;
    /* Foysal Tech && Ict Foysal */
    public EditText password;
    Button signin;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.device);
        this.login = (Button) findViewById(R.id.login);
        EditText editText = (EditText) findViewById(R.id.passwordpin);
        this.password = editText;
        editText.setText(getDeviceName());
        this.login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                ((ClipboardManager) mydevice.this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence) null, mydevice.this.password.getText().toString()));
                Toast.makeText(mydevice.this, "Copied", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public String getDeviceName() {
        String str = Build.MANUFACTURER;
        return capitalize(Build.MODEL);
    }

    private String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char charAt = str.charAt(0);
        if (Character.isUpperCase(charAt)) {
            return str;
        }
        return Character.toUpperCase(charAt) + str.substring(1);
    }
}
