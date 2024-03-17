package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Editres extends AppCompatActivity {


    /* Foysal Tech && Ict Foysal */
    public EditText birth;
    /* Foysal Tech && Ict Foysal */
    public String birtn;
    Toolbar toolbar;
    /* Foysal Tech && Ict Foysal */
    public int f173dd;
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public EditText email;
    private String emaili;
    int flag = 0;
    /* Foysal Tech && Ict Foysal */
    public String f174id;
    JSONParser jsonParser = new JSONParser();
    public int f176mm;
    private String mom;
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    private String namei;
    /* Foysal Tech && Ict Foysal */
    public EditText nick;
    private String nicki;
    /* Foysal Tech && Ict Foysal */
    public EditText nid;
    private String nidn;
    private ProgressDialog pDialog;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    /* Foysal Tech && Ict Foysal */

    /* renamed from: te */
    public EditText f177te;
    private String tel;

    /* renamed from: yy */
    private int f178yy;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.editr);

        getWindow().setSoftInputMode(2);
        Button button = (Button) findViewById(R.id.reg);
        nick =  findViewById(R.id.nick);
        email =  findViewById(R.id.email);
        pin =  findViewById(R.id.opin);
        name =  findViewById(R.id.aname);
        f177te =  findViewById(R.id.phn);
        birth =  findViewById(R.id.birth);
        nid =  findViewById(R.id.nid);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit user");

        Intent intent = getIntent();
        nicki = intent.getStringExtra("nick");
        namei = intent.getStringExtra(AppMeasurementSdk.ConditionalUserProperty.NAME);
        emaili = intent.getStringExtra("email");
        f174id = intent.getStringExtra("id");
        tel = intent.getStringExtra("tel");
        mom = intent.getStringExtra("model");
        birtn = intent.getStringExtra("birth");
        nidn = intent.getStringExtra("nid");
        nick.setText(nicki);
        name.setText(namei);

        birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (birtn.indexOf("null") >= 0) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Editres.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            birth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                        }
                    }, 2000, f176mm, f173dd);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    datePickerDialog.show();
                }
            }
        });
        if (nidn.indexOf("null") < 0) {
            nid.setText(nidn);
            nid.setClickable(false);
            nid.setFocusable(false);
        }
        if (birtn.indexOf("null") < 0) {
            birth.setText(birtn);
        }
        String str = emaili;
        if (str != null) {
            email.setText(str);
        }
        if (tel.indexOf("null") < 0) {
            f177te.setText(tel);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Editres editres = Editres.this;
                if (!editres.isOnline(editres)) {
                    Toast.makeText(Editres.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (nick.length() < 4) {
                    Toast.makeText(Editres.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                } else {
                    new loginAccess().execute(new String[0]);
                }
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Editres.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            getPref("phone", getApplicationContext());
            getPref("pass", getApplicationContext());
            getPref("pin", getApplicationContext());
            nick.getText().toString();
            String obj = email.getText().toString();
            String obj2 = name.getText().toString();
            String obj3 = f177te.getText().toString();
            String obj4 = pin.getText().toString();
            String obj5 = nid.getText().toString();
            String obj6 = birth.getText().toString();
            String pref = getPref("token", getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", Editres.getPref("device", getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("email", obj));
            arrayList.add(new BasicNameValuePair("id", f174id));
            arrayList.add(new BasicNameValuePair("mobile", obj3));
            arrayList.add(new BasicNameValuePair("nid", obj5));
            arrayList.add(new BasicNameValuePair("birth", obj6));
            arrayList.add(new BasicNameValuePair("mypin", obj4));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj2));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Editres.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "resellerEdit", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Editres.this, "Faild:" + string, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (i == 1) {
                    flag = 0;
                } else {
                    flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                flag = 0;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Editres.this, "Update successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Myreseller.class));
                        finish();
                    }
                });
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(String str) {
            dialog.dismiss();
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

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(0, 0);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
