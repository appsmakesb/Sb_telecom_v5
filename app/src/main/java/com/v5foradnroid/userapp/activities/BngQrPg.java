package com.v5foradnroid.userapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class BngQrPg extends AppCompatActivity {

    Toolbar toolbar;
    String QrText, QrImage;
    ImageView showQrImg;
    TextView bng_QrTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bngqrpg);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("বাংলা QR");

        showQrImg = findViewById(R.id.showQrImg);
        bng_QrTv = findViewById(R.id.bng_QrTv);

        String LvUrl = getPref("fourth", getApplicationContext());
        fetchData(LvUrl);

        findViewById(R.id.qr_dnw).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    startDownloading(QrImage);
                } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
                } else {
                    startDownloading(QrImage);
                }
            }
        });

    }


    public void fetchData(String lvurl) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                lvurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("BanglaQr");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        QrImage = jsonObject1.getString("QrImage");
                        QrText = jsonObject1.getString("QrText");
                    }

                    if (QrText != null && !QrText.isEmpty()) {
                        bng_QrTv.setText(QrText);
                    }
                    try {
                        Picasso.get().load(QrImage).placeholder((int)
                                R.drawable.progressbar_ic).error((int) R.drawable.loading_error).into(showQrImg);
                    } catch (NullPointerException e) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Handle error
            }
        });
        Volley.newRequestQueue(BngQrPg.this).add(jsonObjectRequest);
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    @SuppressLint("WrongConstant")
    public void startDownloading(String UrlData) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(UrlData));
        request.setAllowedNetworkTypes(3);
        request.setTitle("Download");
        request.setDescription("Downloading file...");
        request.setMimeType("image/jpeg");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        String str = Environment.DIRECTORY_PICTURES;
        request.setDestinationInExternalPublicDir(str, File.separator + System.currentTimeMillis() + ".jpg");
        ((DownloadManager) getSystemService("download")).enqueue(request);
        Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            } else {
                startDownloading(QrImage);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}