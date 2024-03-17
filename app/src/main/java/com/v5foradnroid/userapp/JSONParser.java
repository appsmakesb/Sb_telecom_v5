package com.v5foradnroid.userapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    /* renamed from: is */
    static InputStream f198is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject makeHttpRequest(String str, String str2, List<NameValuePair> list) throws IOException {
        if (str2 == HttpPost.METHOD_NAME) {
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                defaultHttpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Linux; Android " + getAndroidVersion() + "; " + getDeviceName() + " Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
                HttpPost httpPost = new HttpPost(str);
                httpPost.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) list, "UTF-8"));
                f198is = defaultHttpClient.execute((HttpUriRequest) httpPost).getEntity().getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } else if (str2 == HttpGet.METHOD_NAME) {
            f198is = new DefaultHttpClient().execute((HttpUriRequest) new HttpGet(str + "?" + URLEncodedUtils.format((List<? extends NameValuePair>) list, "utf-8"))).getEntity().getContent();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f198is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine + "\n");
            }
            f198is.close();
            json = sb.toString();
        } catch (Exception e4) {
            Log.e("Buffer Error", "Error converting result " + e4.toString());
        }
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e5) {
            Log.e("JSON Parser", "Error parsing data " + e5.toString());
        }
        return jObj;
    }

    public String getAndroidVersion() {
        String str = Build.VERSION.RELEASE;
        int i = Build.VERSION.SDK_INT;
        return str;
    }

    private boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public String getDeviceName() {
        String str = Build.MANUFACTURER;
        return Build.MODEL;
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
