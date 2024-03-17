package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

public class Server extends Service {
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 60000;
    Context context;
    int counter = 0;
    private Handler handler;
    Intent intent;
    public MyLocationListener listener;
    public LocationManager locationManager;
    public Location previousBestLocation = null;
    private final int runTime = 25000;
    private Runnable runnable;

    public IBinder onBind(Intent intent2) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.intent = new Intent(BROADCAST_ACTION);
        this.context = this;
    }

    @SuppressLint("WrongConstant")
    public void onStart(Intent intent2, int i) {
        this.locationManager = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
        MyLocationListener myLocationListener = new MyLocationListener();
        this.listener = myLocationListener;
        this.locationManager.requestLocationUpdates("gps", 3000, 0.0f, myLocationListener);
    }

    /* Foysal Tech && ict Foysal */
    public boolean isBetterLocation(Location location, Location location2) {
        if (location2 == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z = time > 60000;
        boolean z2 = time < -60000;
        boolean z3 = time > 0;
        if (z) {
            return true;
        }
        if (z2) {
            return false;
        }
        int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z4 = accuracy > 0;
        boolean z5 = accuracy < 0;
        boolean z6 = accuracy > 200;
        boolean isSameProvider = isSameProvider(location.getProvider(), location2.getProvider());
        if (z5) {
            return true;
        }
        if (z3 && !z4) {
            return true;
        }
        if (!z3 || z6 || !isSameProvider) {
            return false;
        }
        return true;
    }

    private boolean isSameProvider(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        this.locationManager.removeUpdates(this.listener);
    }

    public static Thread performOnBackgroundThread(final Runnable runnable2) {
        Thread r0 = new Thread() {
            public void run() {
                runnable2.run();
            }
        };
        r0.start();
        return r0;
    }

    public class MyLocationListener implements LocationListener {
        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public MyLocationListener() {
        }

        public void onLocationChanged(Location location) {
            Log.i("**********", "Location changed");
            Server server = Server.this;
            if (server.isBetterLocation(location, server.previousBestLocation)) {
                location.getLatitude();
                location.getLongitude();
                Server.this.intent.putExtra("Latitude", location.getLatitude());
                Server.this.intent.putExtra("Longitude", location.getLongitude());
                Server.this.intent.putExtra("Provider", location.getProvider());
                Server server2 = Server.this;
                server2.sendBroadcast(server2.intent);
                String valueOf = String.valueOf(location.getLatitude());
                String valueOf2 = String.valueOf(location.getLongitude());
                Server.this.SavePreferences("body", valueOf);
                Server.this.SavePreferences("sender", valueOf2);
                try {
                    new RequestTask().execute(new String[]{"http://ahonatopup.com/map/lg.php?"}).get();
                } catch (InterruptedException | ExecutionException unused) {
                }
            }
        }

        public void onProviderDisabled(String str) {
            Toast.makeText(Server.this.getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String str) {
            Toast.makeText(Server.this.getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }
    }

    class RequestTask extends AsyncTask<String, String, String> {
        RequestTask() {
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            defaultHttpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Linux; Android " + Server.this.getAndroidVersion() + "; " + Server.this.getDeviceName() + " Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
            String pref = Server.getPref("body", Server.this.getApplicationContext());
            String pref2 = Server.getPref("sender", Server.this.getApplicationContext());
            String pref3 = Server.getPref("phone", Server.this.getApplicationContext());
            String pref4 = Server.getPref("pass", Server.this.getApplicationContext());
            try {
                HttpPost httpPost = new HttpPost(strArr[0]);
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair("stp", pref);
                BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("enp", pref2);
                BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("password", pref4);
                BasicNameValuePair basicNameValuePair4 = new BasicNameValuePair("phone", pref3);
                BasicNameValuePair basicNameValuePair5 = new BasicNameValuePair("model", Server.this.getDeviceNameo());
                ArrayList arrayList = new ArrayList();
                arrayList.add(basicNameValuePair);
                arrayList.add(basicNameValuePair2);
                arrayList.add(basicNameValuePair3);
                arrayList.add(basicNameValuePair4);
                arrayList.add(basicNameValuePair5);
                httpPost.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) arrayList, "UTF-8"));
                HttpResponse execute = defaultHttpClient.execute(httpPost);
                StatusLine statusLine = execute.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    execute.getEntity().writeTo(byteArrayOutputStream);
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream2;
                }
                execute.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
        }
    }

    public String getAndroidVersion() {
        String str = Build.VERSION.RELEASE;
        int i = Build.VERSION.SDK_INT;
        return str;
    }

    private boolean isOnline(Context context2) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public String getDeviceName() {
        String str = Build.MANUFACTURER;
        return capitalize(Build.MODEL);
    }

    public String getDeviceNameo() {
        String str = Build.MANUFACTURER;
        return str + "-" + Build.MODEL;
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

    public static String getPref(String str, Context context2) {
        return PreferenceManager.getDefaultSharedPreferences(context2).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
