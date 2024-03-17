package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Transfer extends AppCompatActivity {

    Toolbar toolbar;
    public EditText f281am;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    public EditText msg;
    public ProgressDialog pDialog;
    String paid;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    public RadioGroup radioGroup;
    public EditText username;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.transfer);

        username = (EditText) findViewById(R.id.username);
        f281am = (EditText) findViewById(R.id.amount);
        pin = (EditText) findViewById(R.id.pin);
        msg = (EditText) findViewById(R.id.msg);
        radioGroup = (RadioGroup) findViewById(R.id.bal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Balance Transfer");

        findViewById(R.id.sub).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Transfer transfer = Transfer.this;
                if (!transfer.isOnline(transfer)) {
                    Toast.makeText(Transfer.this, "No network connection", Toast.LENGTH_LONG).show();
                    return;
                }
                paid = "Transfer";
                f281am.getText().toString();
                username.getText().toString();

                new loginAccess().execute(new String[0]);
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = pDialog = new ProgressDialog(Transfer.this);
            pDialog.setMessage("please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Transfer.getPref("phone", getApplicationContext());
            Transfer.getPref("pass", getApplicationContext());
            Transfer.getPref("pin", getApplicationContext());
            String pref = Transfer.getPref("token", getApplicationContext());
            String pref2 = Transfer.getPref("device", getApplicationContext());
            Transfer transfer = Transfer.this;
            EditText unused = transfer.f281am = (EditText) transfer.findViewById(R.id.amount);
            String obj = msg.getText().toString();
            String obj2 = username.getText().toString();
            String obj3 = f281am.getText().toString();
            String obj4 = pin.getText().toString();
            arrayList.add(new BasicNameValuePair("deviceid", pref2));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("amount", obj3));
            arrayList.add(new BasicNameValuePair("ucid", obj2));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_MESSAGE, obj));
            arrayList.add(new BasicNameValuePair("item", "transfer"));
            arrayList.add(new BasicNameValuePair("type", "Transfer"));
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.main) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "main"));
            } else if (checkedRadioButtonId == R.id.drive) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "drive"));
            } else {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "bank"));
            }
            arrayList.add(new BasicNameValuePair("pincode", obj4));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Transfer.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "payment", HttpPost.METHOD_NAME, arrayList);
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
                            showError(Transfer.this, string);
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
                        Toast.makeText(Transfer.this, "successful", Toast.LENGTH_LONG).show();
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
            pDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(Transfer.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog = new Dialog(activity, 2131886564);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog.findViewById(R.id.dialogOpen)).setText(str);
        dialog.show();
    }
}
