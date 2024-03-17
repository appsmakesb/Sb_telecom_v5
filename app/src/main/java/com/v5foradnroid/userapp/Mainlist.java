package com.v5foradnroid.userapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import androidx.core.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mainlist extends AppCompatActivity {
    static String BALANCE = "balance";
    static String CONTENT = "content";
    static String FLAG = "flag";
    static String LINK = "link";
    static String Model = "model";
    static String Pcode = "pcode";
    static String Phone = "phone";
    static String SENder = "sender";
    static String TIME = "time";
    static String TITLE = "title";
    static String TYPe = "type";
    static String Uptime = "uptime";
    static String credit = "credit";
    static String debit = "debit";
    static String img = "img";
    static String service_id = "service";
    static String f199ut = "ut";
    String FinalJSonObject;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Dialog dialog;
    JSONArray jsonarray;
    ListView listview;
    private ImageButton login;
    String number;
    String pwd;
    private ImageButton ref;
    String token;
    String type2;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.listview_main);

        overridePendingTransition(0, 0);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(2);
        this.type2 = intent.getExtras().getString("type");
        this.login = (ImageButton) findViewById(R.id.search);
        this.ref = (ImageButton) findViewById(R.id.refresh);
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Mainlist.this.item_list();
            }
        });
        this.ref.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Mainlist.this.item_list();
            }
        });
        item_list();
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
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("osman", str2);
        this.number = getPref("phone", getApplicationContext());
        this.pwd = getPref("pass", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        EditText editText = findViewById(R.id.numbers);
        String obj = editText.getText().toString();
        String str3 = "Otherhistory";
        String str4 = this.type2.equals("32") ? str3 : "RechargeHistory";
        if (this.type2.equals("8")) {
            str4 = str3;
        }
        if (this.type2.equals("8")) {
            str4 = str3;
        }
        if (!this.type2.equals("524288")) {
            str3 = str4;
        }
        StringRequest r3 = new StringRequest(1, this.url + "/" + str3 + "?number=" + obj + "&uif=" + this.type2 + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                Mainlist.this.FinalJSonObject = str;
                Mainlist mainlist = Mainlist.this;
                new ItemParseJSonDataClass(mainlist).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Mainlist.this.dialog.dismiss();
                Toast.makeText(Mainlist.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
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
            Mainlist.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(Mainlist.this.FinalJSonObject);
                Mainlist.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", Mainlist.this.jsonarray.toString());
                for (int i = 0; i < Mainlist.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = Mainlist.this.jsonarray.getJSONObject(i);
                    hashMap.put("type", jSONObject2.getString("serviceName"));
                    hashMap.put("link", jSONObject2.getString("trxID"));
                    hashMap.put("title", jSONObject2.getString("number"));
                    hashMap.put(FirebaseAnalytics.Param.CONTENT, jSONObject2.getString("cost"));
                    hashMap.put("flag", jSONObject2.getString(NotificationCompat.CATEGORY_STATUS));
                    hashMap.put("img", jSONObject2.getString("img"));
                    hashMap.put("phone", jSONObject2.getString("balance"));
                    hashMap.put("model", jSONObject2.getString("type"));
                    hashMap.put("uptime", jSONObject2.getString("time"));
                    hashMap.put("balance", jSONObject2.getString("prebalance"));
                    hashMap.put("sender", jSONObject2.getString("sender"));
                    hashMap.put("ut", jSONObject2.getString("ut"));
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, jSONObject2.getString(NotificationCompat.CATEGORY_SERVICE));
                    hashMap.put("pcode", jSONObject2.getString("pcode"));
                    if (jSONObject2.getInt(NotificationCompat.CATEGORY_SERVICE) == 11) {
                        hashMap.put("debit", jSONObject2.getString("debit"));
                        hashMap.put("credit", jSONObject2.getString("credit"));
                    }
                    Mainlist.this.arraylist.add(hashMap);
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
            Mainlist mainlist = Mainlist.this;
            mainlist.listview = (ListView) mainlist.findViewById(R.id.listview);
            Mainlist mainlist2 = Mainlist.this;
            Mainlist mainlist3 = Mainlist.this;
            mainlist2.adapter = new ListViewAdapter(mainlist3, mainlist3.arraylist);
            Mainlist.this.listview.setEmptyView(Mainlist.this.findViewById(R.id.empty_list_view));
            Mainlist.this.listview.setAdapter(Mainlist.this.adapter);
            Mainlist.this.dialog.dismiss();
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

}
