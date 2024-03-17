package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.ImagesContract;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.debug.DebugEventListener;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Banktransfer extends AppCompatActivity {

    public EditText amount;
    ArrayList<HashMap<String, String>> arraylist;
    String balanc;
    /* Foysal Tech && Ict Foysal */
    public EditText branch;
    Dialog dialog;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    String nam;
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    /* Foysal Tech && Ict Foysal */
    public EditText nuumber;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    public EditText remark;
    List<String> responseList = new ArrayList();
    String samount;
    String sbranch;
    String sname;
    String snumber;
    Spinner spinner2;
    String sremarks;
    String text;
    String type;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.banktransfer);

        this.spinner2 = (Spinner) findViewById(R.id.flot);
        this.name = (EditText) findViewById(R.id.name);
        this.nuumber = (EditText) findViewById(R.id.number);
        this.amount = (EditText) findViewById(R.id.amount);
        this.pin = (EditText) findViewById(R.id.pin);
        this.branch = (EditText) findViewById(R.id.branch);
        this.remark = (EditText) findViewById(R.id.remark);

        new DownloadJSONy().execute(new Void[0]);
        this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                text = adapterView.getItemAtPosition(i).toString();
            }
        });

        findViewById(R.id.sub).setOnClickListener(new View.OnClickListener() {
            private String paid;

            public void onClick(View view) {
                snumber = nuumber.getText().toString();
                samount = amount.getText().toString();
                sname = name.getText().toString();
                sbranch = branch.getText().toString();
                sremarks = remark.getText().toString();
                if (!isOnline(Banktransfer.this)) {
                    Toast.makeText(Banktransfer.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (snumber.length() < 5) {
                    Toast.makeText(Banktransfer.this, "Please Enter correct AC number", Toast.LENGTH_LONG).show();
                } else {
                    ViewDialog viewDialog = new ViewDialog();
                    viewDialog.showDialog(Banktransfer.this, sname, samount, sbranch, text);
                }
            }
        });
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Banktransfer.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
            Banktransfer banktransfer = Banktransfer.this;
            if (!banktransfer.isOnline(banktransfer)) {
                findViewById(R.id.progressbar).setVisibility(View.GONE);
                finish();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            if (!isOnline(Banktransfer.this)) {
                return null;
            }
            String pref = getPref("token", getApplicationContext());
            String pref2 = getPref("device", getApplicationContext());
            arraylist = new ArrayList<>();
            jsonobject = JSONfunctions.getJSONfromURL((getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "banklist?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                jsonarray = jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    jsonobject = jsonarray.getJSONObject(i);
                    nam = jsonobject.getString("name");
                    responseList.add(nam);
                    arraylist.add(hashMap);
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        @SuppressLint("ResourceType")
        public void onPostExecute(Void voidR) {
            Banktransfer banktransfer = Banktransfer.this;
            if (banktransfer.isOnline(banktransfer)) {
                dialog.dismiss();
                Banktransfer banktransfer2 = Banktransfer.this;
                ArrayAdapter arrayAdapter = new ArrayAdapter(banktransfer2, R.layout.custom_spinner_item, banktransfer2.responseList);
                arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
            }
        }
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Banktransfer.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Banktransfer.getPref("phone", getApplicationContext());
            String pref2 = Banktransfer.getPref("pass", getApplicationContext());
            String pref3 = Banktransfer.getPref("pin", getApplicationContext());
            String obj = pin.getText().toString();
            String pref4 = Banktransfer.getPref("token", getApplicationContext());
            String pref5 = Banktransfer.getPref("device", getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("key", pref2));
            arrayList.add(new BasicNameValuePair("amount", samount));
            arrayList.add(new BasicNameValuePair("bank_name", text));
            arrayList.add(new BasicNameValuePair("note", sremarks));
            arrayList.add(new BasicNameValuePair("number", snumber));
            arrayList.add(new BasicNameValuePair("area", sbranch));
            arrayList.add(new BasicNameValuePair("holdername", sname));
            arrayList.add(new BasicNameValuePair("pinn", obj));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            arrayList.add(new BasicNameValuePair("service", "32"));
            arrayList.add(new BasicNameValuePair("item", type));
            arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Banktransfer.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "bank", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                balanc = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showError(Banktransfer.this, balanc);
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
                        Toast.makeText(Banktransfer.this, balanc, Toast.LENGTH_LONG).show();
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
            dialog.dismiss();
            if (flag == 1) {
                Toast.makeText(Banktransfer.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirmbank);
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

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(0, 0);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }
}
