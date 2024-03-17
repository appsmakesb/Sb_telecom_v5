package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;

import java.util.Hashtable;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Password extends AppCompatActivity {

    Toolbar toolbar;
    String FinalJSonObject;
    /* Foysal Tech && Ict Foysal */
    public EditText conpass, newpass, opass;
    Dialog dialog;
    int flag = 0;
    /* Foysal Tech && Ict Foysal */ String device, pwd, token, url, f215cp, f217np, number, f218op;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.password);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        opass = (EditText) findViewById(R.id.oldpass);
        conpass = (EditText) findViewById(R.id.conpass);
        newpass = (EditText) findViewById(R.id.newpass);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f217np = newpass.getText().toString();
                f218op = opass.getText().toString();
                f215cp = conpass.getText().toString();
                if (!isOnline(Password.this)) {
                    Toast.makeText(Password.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (newpass.length() < 6) {
                    Toast.makeText(Password.this, "Please Enter minimum 6 digit Password", Toast.LENGTH_LONG).show();
                } else {
                    password();
                }
            }
        });
    }

    /* Foysal Tech && Ict Foysal */
    public void password() {
        Dialog dialog2 = new Dialog(this);
        dialog = dialog2;
        dialog2.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress);
        dialog.show();
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + url;
        url = str2;
        Log.d("osman", str2);
        number = getPref("phone", getApplicationContext());
        pwd = getPref("pass", getApplicationContext());
        token = getPref("token", getApplicationContext());
        device = getPref("device", getApplicationContext());
        getPref("pin", getApplicationContext());
        final String pref = getPref("token", getApplicationContext());
        final String pref2 = getPref("device", getApplicationContext());
        StringRequest r3 = new StringRequest(1, url + "password", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ParseJSonDataClass(Password.this).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("oldpass", f218op);
                hashtable.put("newpass", f217np);
                hashtable.put("cnewpass", f215cp);
                hashtable.put("token", pref);
                hashtable.put("username", number);
                hashtable.put("password", pwd);
                hashtable.put("deviceid", pref2);
                return hashtable;
            }
        };
        r3.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(r3);
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ParseJSonDataClass(Context context2) {
            context = context2;
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            try {
                if (new JSONObject(FinalJSonObject).getInt("success") == 1) {
                    SavePreferences("pass", f215cp);
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Password.this, "Password change successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Welcome.class));
                            finish();
                        }
                    });
                    return null;
                }
                flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            if (flag == 1) {
                Toast.makeText(Password.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
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

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
