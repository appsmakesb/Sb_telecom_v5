package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class history_op extends AppCompatActivity {

    Toolbar toolbar;
    GridView  gridView;
    static String Service_id = "service";
    static String act = "act";
    static String img = "img";
    String FinalJSonObject;
    history_adafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    Dialog dialog;
    JSONArray jsonarray;
    String device, number, pwd, token, url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.service_main);

        gridView = findViewById(R.id.atachviewx);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");

        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

        item_list();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private void item_list() {
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
        StringRequest r1 = new StringRequest(1, url + "/role?item=history&token=" + URLEncoder.encode(token) + "&deviceid=" + URLEncoder.encode(device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ItemParseJSonDataClass(history_op.this).execute(new Void[0]);
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
        Volley.newRequestQueue(getApplicationContext()).add(r1);
    }

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            context = context2;
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
                    hashMap.put("service", jSONObject2.getString("service"));
                    hashMap.put("name", jSONObject2.getString("name"));
                    hashMap.put("img", jSONObject2.getString("img"));
                    hashMap.put("act", jSONObject2.getString("act"));
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
            adapter = new history_adafter(history_op.this, arraylist);
            gridView.setAdapter(adapter);
            dialog.dismiss();
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
