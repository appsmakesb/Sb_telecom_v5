package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Myreseller extends AppCompatActivity {
    static String Birth = "birth";
    static String Dbal = "dbalance";
    static String Email = "email";
    static String FLAG = "flag";
    static String LINK = "link";
    static String Model = "id";
    static String NID = "nid";
    static String Phone = "phone";
    static String TIME = "time";
    static String TITLE = "title";
    static String TYPe = "type";
    static String Teei = "tel";
    static String Uptime = "uptime";
    static String bbalance = "bbalance";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket", "Bank", "All"};
    static String last = "lastlogin";
    static String mmm = "model";
    static String name = "username";
    String FinalJSonObject;
    Reselleradafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Dialog dialog;
    JSONArray jsonarray;
    JSONObject jsonobject;
    ListView listview;
    private ImageButton login;
    ProgressDialog mProgressDialog;
    String msg;
    String number;
    String pwd;

    /* renamed from: tn */
    private EditText f207tn;
    String token;
    String type;
    String type2;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.listview_main_r);
        getWindow().setSoftInputMode(2);
        reseller_list();
        ImageButton imageButton = (ImageButton) findViewById(R.id.search);
        this.login = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Myreseller.this.reseller_list();
            }
        });
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Myreseller.this.startActivity(new Intent(Myreseller.this.getApplicationContext(), Addres.class));
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    /* Foysal Tech && Ict Foysal */
    public void reseller_list() {
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_progress);
        this.dialog.show();
        EditText editText = (EditText) findViewById(R.id.numbers);
        this.f207tn = editText;
        String obj = editText.getText().toString();
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("osman", str2);
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        StringRequest r2 = new StringRequest(1, this.url + "/reseller?number=" + obj + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                Myreseller.this.FinalJSonObject = str;
                Myreseller myreseller = Myreseller.this;
                new ItemParseJSonDataClass(myreseller).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Myreseller.this.dialog.dismiss();
                Toast.makeText(Myreseller.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
                return hashtable;
            }
        };
        r2.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(r2);
    }

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            Myreseller.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(Myreseller.this.FinalJSonObject);
                Myreseller.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                for (int i = 0; i < Myreseller.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = Myreseller.this.jsonarray.getJSONObject(i);
                    hashMap.put("username", jSONObject2.getString("username"));
                    hashMap.put("link", jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                    hashMap.put("title", jSONObject2.getString("resellerstatus"));
                    hashMap.put("email", jSONObject2.getString("email"));
                    hashMap.put("flag", jSONObject2.getString("type"));
                    hashMap.put("phone", jSONObject2.getString("balance"));
                    hashMap.put("bbalance", jSONObject2.getString("bbalance"));
                    hashMap.put("dbalance", jSONObject2.getString("dbalance"));
                    hashMap.put("id", jSONObject2.getString("id"));
                    hashMap.put("uptime", jSONObject2.getString("createdate"));
                    hashMap.put("type", jSONObject2.getString("type"));
                    hashMap.put("lastlogin", jSONObject2.getString("lastlogin"));
                    hashMap.put("tel", jSONObject2.getString("tel"));
                    hashMap.put("birth", jSONObject2.getString("birth"));
                    hashMap.put("nid", jSONObject2.getString("nid"));
                    hashMap.put("model", "");
                    hashMap.put("tel", jSONObject2.getString("tel"));
                    Myreseller.this.arraylist.add(hashMap);
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
            Myreseller myreseller = Myreseller.this;
            myreseller.listview = (ListView) myreseller.findViewById(R.id.listview_r);
            Myreseller myreseller2 = Myreseller.this;
            Myreseller myreseller3 = Myreseller.this;
            myreseller2.adapter = new Reselleradafter(myreseller3, myreseller3.arraylist);
            Myreseller.this.listview.setEmptyView(Myreseller.this.findViewById(R.id.empty_list_view));
            Myreseller.this.listview.setAdapter(Myreseller.this.adapter);
            Myreseller.this.dialog.dismiss();
        }
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
