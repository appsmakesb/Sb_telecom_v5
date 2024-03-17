package com.v5foradnroid.userapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Smssend extends Activity {

    public EditText f276am;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    public dialog f278md;
    public EditText f279mn;
    public ProgressDialog pDialog;
    public EditText pin;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.smsac);
        
        f279mn =  findViewById(R.id.number);
        pin = findViewById(R.id.pin);
        
        findViewById(R.id.sub).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isOnline(Smssend.this)) {
                    Toast.makeText(Smssend.this, "No network connection", Toast.LENGTH_LONG).show();
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
            ProgressDialog unused = pDialog = new ProgressDialog(Smssend.this);
            pDialog.setMessage("please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Smssend.getPref("phone", getApplicationContext());
            String pref2 = Smssend.getPref("pass", getApplicationContext());
            String pref3 = Smssend.getPref("pin", getApplicationContext());
            Smssend smssend = Smssend.this;
            EditText unused = smssend.f276am = (EditText) smssend.findViewById(R.id.text);
            String obj = f279mn.getText().toString();
            String obj2 = f276am.getText().toString();
            String obj3 = pin.getText().toString();
            String pref4 = Smssend.getPref("token", getApplicationContext());
            String pref5 = Smssend.getPref("device", getApplicationContext());
            String str = "pass";
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("key", pref2));
            arrayList.add(new BasicNameValuePair("buy", "ok"));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_MESSAGE, obj2));
            arrayList.add(new BasicNameValuePair("number", obj));
            arrayList.add(new BasicNameValuePair("item", "sms"));
            arrayList.add(new BasicNameValuePair("pincode", obj3));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Smssend.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt(NotificationCompat.CATEGORY_STATUS);
                final String string = makeHttpRequest.getString("Message");
                makeHttpRequest.getString("cost");
                if (i2 == 0) {
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialog unused = f278md = new dialog(Smssend.this);
                            f278md.build("Faild", string);
                        }
                    });
                }
                if (i == 1) {
                    flag = 0;
                } else {
                    flag = 1;
                    SavePreferences("phone", "no");
                    SavePreferences(str, "no");
                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                    finish();
                }
                if (i2 != 1) {
                    return null;
                }
                flag = 0;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Smssend.this, "successful  Send sms ", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Welcome.class));
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
                Toast.makeText(Smssend.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
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

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ((TextView) dialog.findViewById(R.id.cop)).setText(str4);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    new loginAccess().execute(new String[0]);
                    dialog.dismiss();
                }
            });
            ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
