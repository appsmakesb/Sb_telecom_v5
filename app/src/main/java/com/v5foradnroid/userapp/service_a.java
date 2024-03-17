package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class service_a extends Activity {


    static String CONTENT = FirebaseAnalytics.Param.CONTENT;
    static String Model = "id";
    static String OPN = "opname";
    static String f340OT = "ot";
    static String Pini = "pin";
    static String Service_id = NotificationCompat.CATEGORY_SERVICE;
    static String Service_n = AppMeasurementSdk.ConditionalUserProperty.NAME;
    static String TIME = "time";
    static String TYPe = "type";
    static String Uptime = "uptime";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket", "Bank", "All"};
    static String img = "img";
    static String mmm = "model";
    service_adafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    JSONArray jsonarray;
    JSONObject jsonobject;
    ProgressDialog mProgressDialog;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.service_main);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        new DownloadJSONy().execute(new Void[0]);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            service_a.this.findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
            service_a service_a = service_a.this;
            if (!service_a.isOnline(service_a)) {
                service_a.this.findViewById(R.id.progressbar).setVisibility(View.GONE);
                service_a.this.finish();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            service_a service_a = service_a.this;
            if (!service_a.isOnline(service_a)) {
                return null;
            }
            String pref = service_a.getPref("token", service_a.this.getApplicationContext());
            String pref2 = service_a.getPref("device", service_a.this.getApplicationContext());
            service_a.this.arraylist = new ArrayList<>();
            service_a.this.jsonobject = JSONfunctions.getJSONfromURL((service_a.getPref(ImagesContract.URL, service_a.this.getApplicationContext()) + "/apiapp/") + "/role?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                service_a service_a2 = service_a.this;
                service_a2.jsonarray = service_a2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", service_a.this.jsonarray.toString());
                for (int i = 0; i < service_a.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    service_a service_a3 = service_a.this;
                    service_a3.jsonobject = service_a3.jsonarray.getJSONObject(i);
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, service_a.this.jsonobject.getString(NotificationCompat.CATEGORY_SERVICE));
                    hashMap.put(AppMeasurementSdk.ConditionalUserProperty.NAME, service_a.this.jsonobject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                    hashMap.put("img", service_a.this.jsonobject.getString("img"));
                    service_a.this.arraylist.add(hashMap);
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
            service_a service_a = service_a.this;
            if (service_a.isOnline(service_a)) {
                service_a service_a2 = service_a.this;
                service_a service_a3 = service_a.this;
                service_a2.adapter = new service_adafter(service_a3, service_a3.arraylist);
                ((GridView) service_a.this.findViewById(R.id.atachview)).setAdapter(service_a.this.adapter);
                service_a.this.findViewById(R.id.progressbar).setVisibility(View.GONE);
            }
        }
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
