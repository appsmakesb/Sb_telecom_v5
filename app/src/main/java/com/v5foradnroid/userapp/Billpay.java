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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

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

public class Billpay extends AppCompatActivity {


    /* Foysal Tech && Ict Foysal */
    public EditText amount;
    ArrayList<HashMap<String, String>> arraylist;
    String balanc;
    /* Foysal Tech && Ict Foysal */
    public EditText branch;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    Button login;
    private com.v5foradnroid.userapp.dialog mdd;
    String nam;
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    /* Foysal Tech && Ict Foysal */
    public EditText nuumber;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    /* Foysal Tech && Ict Foysal */
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
        setContentView(R.layout.bank);

        spinner2 = (Spinner) findViewById(R.id.flot);
        name = (EditText) findViewById(R.id.number);
        nuumber = (EditText) findViewById(R.id.number);
        amount = (EditText) findViewById(R.id.amount);
        pin = (EditText) findViewById(R.id.pin);
        branch = (EditText) findViewById(R.id.branch);
        remark = (EditText) findViewById(R.id.remark);

        new DownloadJSONy().execute(new Void[0]);
        this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Billpay.this.text = adapterView.getItemAtPosition(i).toString();
            }
        });
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            private String paid;

            public void onClick(View view) {
                Billpay billpay = Billpay.this;
                billpay.snumber = billpay.nuumber.getText().toString();
                Billpay billpay2 = Billpay.this;
                billpay2.samount = billpay2.amount.getText().toString();
                Billpay billpay3 = Billpay.this;
                billpay3.sname = billpay3.name.getText().toString();
                Billpay billpay4 = Billpay.this;
                billpay4.sbranch = billpay4.branch.getText().toString();
                Billpay billpay5 = Billpay.this;
                billpay5.sremarks = billpay5.remark.getText().toString();
                Billpay billpay6 = Billpay.this;
                if (!billpay6.isOnline(billpay6)) {
                    Toast.makeText(Billpay.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Billpay.this.snumber.length() < 5) {
                    Toast.makeText(Billpay.this, "Please Enter correct AC number", Toast.LENGTH_LONG).show();
                } else {
                    ViewDialog viewDialog = new ViewDialog();
                    Billpay billpay7 = Billpay.this;
                    viewDialog.showDialog(billpay7, billpay7.sname, Billpay.this.samount, Billpay.this.sbranch, Billpay.this.text);
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
            Billpay.this.dialog = new Dialog(Billpay.this);
            Billpay.this.dialog.requestWindowFeature(1);
            Billpay.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Billpay.this.dialog.setCancelable(false);
            Billpay.this.dialog.setContentView(R.layout.custom_progress);
            Billpay.this.dialog.show();
            Billpay billpay = Billpay.this;
            if (!billpay.isOnline(billpay)) {
                Billpay.this.findViewById(R.id.progressbar).setVisibility(View.GONE);
                Billpay.this.finish();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            Billpay billpay = Billpay.this;
            if (!billpay.isOnline(billpay)) {
                return null;
            }
            String pref = Billpay.getPref("token", Billpay.this.getApplicationContext());
            String pref2 = Billpay.getPref("device", Billpay.this.getApplicationContext());
            Billpay.this.arraylist = new ArrayList<>();
            Billpay.this.jsonobject = JSONfunctions.getJSONfromURL((Billpay.getPref(ImagesContract.URL, Billpay.this.getApplicationContext()) + "/apiapp/") + "billpayProvider?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                Billpay billpay2 = Billpay.this;
                billpay2.jsonarray = billpay2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", Billpay.this.jsonarray.toString());
                for (int i = 0; i < Billpay.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    Billpay billpay3 = Billpay.this;
                    billpay3.jsonobject = billpay3.jsonarray.getJSONObject(i);
                    Billpay billpay4 = Billpay.this;
                    billpay4.nam = billpay4.jsonobject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                    Billpay.this.responseList.add(Billpay.this.nam);
                    Billpay.this.arraylist.add(hashMap);
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
            Billpay billpay = Billpay.this;
            if (billpay.isOnline(billpay)) {
                Billpay.this.dialog.dismiss();
                Billpay billpay2 = Billpay.this;
                ArrayAdapter arrayAdapter = new ArrayAdapter(billpay2, R.layout.custom_spinner_item, billpay2.responseList);
                arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                Billpay.this.spinner2.setAdapter(arrayAdapter);
            }
        }
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            Billpay.this.dialog = new Dialog(Billpay.this);
            Billpay.this.dialog.requestWindowFeature(1);
            Billpay.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Billpay.this.dialog.setCancelable(false);
            Billpay.this.dialog.setContentView(R.layout.custom_progress);
            Billpay.this.dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Billpay.getPref("phone", Billpay.this.getApplicationContext());
            String pref2 = Billpay.getPref("pass", Billpay.this.getApplicationContext());
            String pref3 = Billpay.getPref("pin", Billpay.this.getApplicationContext());
            String obj = Billpay.this.pin.getText().toString();
            String pref4 = Billpay.getPref("token", Billpay.this.getApplicationContext());
            String pref5 = Billpay.getPref("device", Billpay.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("key", pref2));
            arrayList.add(new BasicNameValuePair("amount", Billpay.this.samount));
            arrayList.add(new BasicNameValuePair("bank_name", Billpay.this.text));
            arrayList.add(new BasicNameValuePair("note", Billpay.this.sremarks));
            arrayList.add(new BasicNameValuePair("number", Billpay.this.snumber));
            arrayList.add(new BasicNameValuePair("area", Billpay.this.sbranch));
            arrayList.add(new BasicNameValuePair("holdername", Billpay.this.sname));
            arrayList.add(new BasicNameValuePair("pinn", obj));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, "8"));
            arrayList.add(new BasicNameValuePair("item", Billpay.this.type));
            arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Billpay.this.jsonParser.makeHttpRequest((Billpay.getPref(ImagesContract.URL, Billpay.this.getApplicationContext()) + "/apiapp/") + "bank", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                Billpay.this.balanc = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Billpay.this.flag = 0;
                    Billpay.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Billpay.this.showError(Billpay.this, Billpay.this.balanc);
                        }
                    });
                }
                if (i == 1) {
                    Billpay.this.flag = 0;
                } else {
                    Billpay.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Billpay.this.flag = 0;
                Billpay.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Billpay.this, Billpay.this.balanc, Toast.LENGTH_LONG).show();
                        Billpay.this.startActivity(new Intent(Billpay.this.getApplicationContext(), Welcome.class));
                        Billpay.this.finish();
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
            Billpay.this.dialog.dismiss();
            if (Billpay.this.flag == 1) {
                Toast.makeText(Billpay.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType")
        Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void setTextInTextView(String str) {
        this.err.setText(str);
    }

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirmbill);
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
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }
}
