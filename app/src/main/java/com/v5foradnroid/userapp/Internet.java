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
import android.widget.LinearLayout;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Internet extends AppCompatActivity {
    static String COMM = "com";
    static String Drive = "drive";
    static String FROM = "from";
    static String f192ID = "id";
    static String Number = "number";
    static String OPN = "opname";
    static String PPRICE = "price";
    static String Paid = "paid";
    static String REG = "reg";
    static String ROL = "role";
    static String Service = "service";
    static String TITLE = "title";
    String FinalJSonObject;
    Internet_after adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Dialog dialog;
    JSONArray jsonarray;
    ListView listview;
    String number, opn, pwd, rol, service, token, url;
    LinearLayout tyi;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.internet_main);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Intent intent = getIntent();
        opn = intent.getExtras().getString("opt");
        service = intent.getExtras().getString("type3");
        number = intent.getExtras().getString("number");

        tyi = findViewById(R.id.tyi);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                item_list();
            }
        });
        item_list();

        if (loadColor() != 0) {
            tyi.setBackgroundColor(loadColor());
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    /* Foysal Tech && Ict Foysal */
    public void item_list() {
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_progress);
        this.dialog.show();
        String str = getPref(ImagesContract.URL, this) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("osman", str2);
        this.pwd = getPref("pass", this);
        this.token = getPref("token", this);
        this.device = getPref("device", this);
        if (getIntent().getExtras().getString("drive").indexOf("drive") >= 0) {
            this.rol = "getdrive";
        } else {
            this.rol = "getinternet";
        }
        EditText editText = (EditText) findViewById(R.id.numbers);
        StringRequest r1 = new StringRequest(1, this.url + "/" + this.rol + "?ot=" + this.opn + "&text=" + editText.getText().toString() + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ItemParseJSonDataClass(Internet.this).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(Internet.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
                return hashtable;
            }
        };
        r1.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(this).add(r1);
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
            arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(FinalJSonObject);
                jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = jsonarray.getJSONObject(i);
                    hashMap.put("id", jSONObject2.getString("id"));
                    hashMap.put("price", jSONObject2.getString("price"));
                    hashMap.put("title", jSONObject2.getString("title"));
                    hashMap.put("opname", jSONObject2.getString("opname"));
                    hashMap.put("com", jSONObject2.getString("com"));
                    hashMap.put("opname", opn);
                    hashMap.put("number", number);
                    hashMap.put("drive", "drive");
                    hashMap.put("paid", "");
                    hashMap.put("role", rol);
                    hashMap.put("service", service);
                    hashMap.put("reg", jSONObject2.getString("reg"));
                    hashMap.put("from", "direct");
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
        public void onPostExecute(Void voidR) {
            dialog.dismiss();
            listview = (ListView) findViewById(R.id.atachview);
            adapter = new Internet_after(Internet.this, arraylist);
            listview.setAdapter(adapter);
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

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
