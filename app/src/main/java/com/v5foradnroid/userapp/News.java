package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class News extends AppCompatActivity {

    public static String TAG = Welcome.class.getSimpleName();
    static String date = "date";
    static String notice = "notice";
    Noticeadafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    Intent intent;
    JSONArray jsonarray;
    JSONObject jsonobject;
    private EditText f211mn;
    public ProgressDialog pDialog;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.noticeboard);

        this.intent = getIntent();
        new DownloadJSONy().execute(new Void[0]);

    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = News.this.pDialog = new ProgressDialog(News.this);
            News.this.pDialog.setMessage("please wait...");
            News.this.pDialog.setIndeterminate(false);
            News.this.pDialog.setCancelable(true);
            News.this.pDialog.show();
            News news = News.this;
            if (!news.isOnline(news)) {
                News.this.pDialog.dismiss();
                News.this.finish();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            News news = News.this;
            if (!news.isOnline(news)) {
                return null;
            }
            News.getPref("token", News.this.getApplicationContext());
            News.getPref("device", News.this.getApplicationContext());
            News.this.arraylist = new ArrayList<>();
            News.this.jsonobject = JSONfunctions.getJSONfromURL((News.getPref(ImagesContract.URL, News.this.getApplicationContext()) + "/apiapp/") + "/notice");
            try {
                News news2 = News.this;
                news2.jsonarray = news2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", News.this.jsonarray.toString());
                for (int i = 0; i < News.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    News news3 = News.this;
                    news3.jsonobject = news3.jsonarray.getJSONObject(i);
                    hashMap.put("notice", News.this.jsonobject.getString("notice"));
                    hashMap.put("date", News.this.jsonobject.getString("date"));
                    News.this.arraylist.add(hashMap);
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
            News news = News.this;
            if (news.isOnline(news)) {
                News news2 = News.this;
                News news3 = News.this;
                news2.adapter = new Noticeadafter(news3, news3.arraylist);
                ((ListView) News.this.findViewById(R.id.listview)).setAdapter(News.this.adapter);
                News.this.pDialog.dismiss();
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
}
