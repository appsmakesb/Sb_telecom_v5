package com.v5foradnroid.userapp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONfunctions {
    public static JSONObject getJSONfromURL(String str) {
        InputStream inputStream;
        String str2;
        try {
            inputStream = new DefaultHttpClient().execute(new HttpPost(str)).getEntity().getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
            inputStream = null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine + "\n");
            }
            inputStream.close();
            str2 = sb.toString();
        } catch (Exception e2) {
            Log.e("log_tag", "Error converting result " + e2.toString());
            str2 = "";
        }
        try {
            return new JSONObject(str2);
        } catch (JSONException e3) {
            Log.e("log_tag", "Error parsing data " + e3.toString());
            return null;
        }
    }
}
