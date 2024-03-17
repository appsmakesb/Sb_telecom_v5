package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Operator extends AppCompatActivity {

    Toolbar toolbar;
    GridView gridView;
    public static String TAG = Welcome.class.getSimpleName();
    static String drive = "drive";
    static String img = "img";
    static String number = "number";
    static String opn = "opname";
    static String f212ot = "pcode";
    static String otype = "type";
    static String serv = "service";
    Operator_adafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    Dialog dialog;
    Intent intent;
    JSONArray jsonarray;
    JSONObject jsonobject;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.operator);

        gridView = findViewById(R.id.atachview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Operator");

        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

        intent = getIntent();

        new DownloadJSONy().execute(new Void[0]);
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Operator.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
            Operator operator = Operator.this;
            if (!operator.isOnline(operator)) {
                dialog.dismiss();
                finish();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            if (!isOnline(Operator.this)) {
                return null;
            }
            getPref("token", getApplicationContext());
            getPref("device", getApplicationContext());
            arraylist = new ArrayList<>();
            jsonobject = JSONfunctions.getJSONfromURL((Operator.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "/oparetorList");
            try {
                Operator operator2 = Operator.this;
                operator2.jsonarray = operator2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    Operator operator3 = Operator.this;
                    operator3.jsonobject = operator3.jsonarray.getJSONObject(i);
                    hashMap.put("opname", jsonobject.getString("opname"));
                    hashMap.put("pcode", jsonobject.getString("pcode"));
                    hashMap.put("img", jsonobject.getString("img"));
                    hashMap.put("type", intent.getExtras().getString("type"));
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, intent.getExtras().getString("type3"));
                    if (intent.hasExtra("number")) {
                        hashMap.put("number", intent.getExtras().getString("number"));
                    }
                    if (intent.hasExtra("drive")) {
                        hashMap.put("drive", intent.getExtras().getString("drive"));
                    } else {
                        hashMap.put("drive", "x");
                    }
                    arraylist.add(hashMap);
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            if (isOnline(Operator.this)) {
                adapter = new Operator_adafter(Operator.this, arraylist);
                gridView.setAdapter(adapter);
                dialog.dismiss();
            }
        }
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
