package com.v5foradnroid.userapp.view;

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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.internal.ImagesContract;
import com.v5foradnroid.userapp.DisplayActivity;
import com.v5foradnroid.userapp.DisplayActivitya;
import com.v5foradnroid.userapp.JSONfunctions;
import com.v5foradnroid.userapp.PinActivity;
import com.v5foradnroid.userapp.R;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityc extends AppCompatActivity implements View.OnClickListener {

    public static String tampBal;
    public EditText f169am;
    ArrayList<HashMap<String, String>> arraylist;
    Dialog dialog;
    Intent intent;
    private boolean isSearch = false;
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    AutoCompleteTextView f341mn;
    String number, opn, optr;
    List<String> responseList = new ArrayList();
    String type, type2, type3;


    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.fragment_contact_list);

        f341mn = findViewById(R.id.et_search);
        f169am = (EditText) findViewById(R.id.amount);

        Intent intent2 = getIntent();
        intent = intent2;
        type = intent2.getExtras().getString("type");
        type2 = intent.getExtras().getString("type2");
        type3 = intent.getExtras().getString("type3");
        opn = intent.getExtras().getString("opt");
        optr = intent.getExtras().getString("opt2");

        new DownloadJSONy().execute(new Void[0]);

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String amountDa = f169am.getText().toString();
                if (amountDa != null && !amountDa.isEmpty()) {
                    if (Integer.parseInt(amountDa) < 10) {
                        Toast.makeText(getApplicationContext(), "পরিমাণ দশের কম...", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        edit.putString("amNewByFt", amountDa);
                        edit.commit();
                        tampBal = amountDa;
                    }
                } else {
                    Toast.makeText(MainActivityc.this, "এমাউন্ট ফাঁকা রাখা যাবে না ..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (f341mn.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please Enter correct Number", Toast.LENGTH_LONG).show();
                } else if (!type.equals("rc")) {
                    Intent intent = new Intent(getApplicationContext(), DisplayActivitya.class);
                    intent.putExtra("opt", opn);
                    intent.putExtra("opt2", optr);
                    intent.putExtra("type", type);
                    intent.putExtra("number", f341mn.getText().toString());
                    intent.putExtra("amountDa", amountDa);
                    intent.putExtra("type3", type3);
                    intent.putExtra("type2", type2);
                    startActivity(intent);
                } else if (intent.hasExtra("drive")) {
                    Intent intent2 = new Intent(getApplicationContext(), PinActivity.class);
                    intent2.putExtra("opt", intent.getExtras().getString("opt"));
                    intent2.putExtra("opt2", intent.getExtras().getString("opt2"));
                    intent2.putExtra("type", intent.getExtras().getString("type"));
                    intent2.putExtra("drive", intent.getExtras().getString("drive"));
                    intent2.putExtra("number", f341mn.getText().toString());
                    intent2.putExtra("amount", amountDa);
                    intent2.putExtra("type3", intent.getExtras().getString("type3"));
                    intent2.putExtra("type2", intent.getExtras().getString("type2"));
                    intent2.putExtra("id", intent.getExtras().getString("id"));
                    intent2.putExtra("paid", intent.getExtras().getString("paid"));
                    intent2.putExtra("amount", intent.getExtras().getString("amount"));
                    intent2.putExtra("pkg", intent.getExtras().getString("pkg"));
                    intent2.putExtra("rol", intent.getExtras().getString("rol"));
                    startActivity(intent2);
                } else {
                    new Getop().execute(new Void[0]);
                }
            }
        });
    }

    public void action(View view) {
        if (view.getId() == R.id.twenty) {
            f169am.setText("20");
        }
        if (view.getId() == R.id.fifty) {
            f169am.setText("49");
        }
        if (view.getId() == R.id.hundread) {
            f169am.setText("100");
        }
        if (view.getId() == R.id.hundread50) {
            f169am.setText("150");
        }
    }

    private class Getop extends AsyncTask<Void, Void, Void> {
        private Getop() {

        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MainActivityc.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
            if (!isOnline(MainActivityc.this)) {
                dialog.dismiss();
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            if (!isOnline(MainActivityc.this)) {
                return null;
            }
            DisplayActivitya.getPref("token", MainActivityc.this);
            DisplayActivitya.getPref("device", MainActivityc.this);
            String str = DisplayActivitya.getPref(ImagesContract.URL, MainActivityc.this) + "/apiapp/";
            arraylist = new ArrayList<>();
            String obj = f341mn.getText().toString();
            if (obj.length() > 3) {
                obj = obj.substring(0, 3);
            }
            jsonobjects = JSONfunctions.getJSONfromURL(str + "/oparetorList?three=" + obj);
            try {
                jsonarray = jsonobjects.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    new HashMap();
                    jsonobject = jsonarray.getJSONObject(i);
                    if (i == 0) {
                        optr = jsonobject.getString("opname");
                        opn = jsonobject.getString("pcode");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(MainActivityc.this, DisplayActivity.class);
                                intent.putExtra("opt", opn);
                                intent.putExtra("opt2", optr);
                                intent.putExtra("type", type);
                                intent.putExtra("number", f341mn.getText().toString());
                                intent.putExtra("type3", type3);
                                intent.putExtra("type2", type2);
                                startActivity(intent);
                            }
                        });
                    }
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            dialog.dismiss();
        }
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {

        private DownloadJSONy() {

        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            if (!isOnline(MainActivityc.this)) {
                return null;
            }
            String pref = DisplayActivitya.getPref("token", MainActivityc.this);
            String pref2 = DisplayActivitya.getPref("device", MainActivityc.this);
            arraylist = new ArrayList<>();
            jsonobject = JSONfunctions.getJSONfromURL((DisplayActivitya.getPref("url", MainActivityc.this) + "/apiapp/") + "/relnumber?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                jsonarray = jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    jsonobject = jsonarray.getJSONObject(i);
                    if (jsonobject.getInt("success") == 1) {
                        number = jsonobject.getString("number");
                    }
                    responseList.add(number);
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
            if (isOnline(MainActivityc.this)) {
                f341mn.setAdapter(new ArrayAdapter(MainActivityc.this, 17367043, responseList));
            }
        }
    }

    public void onClick(View view2) {
        if (view2.getId() == R.id.et_search) {
            isSearch = true;
        }
    }
}
