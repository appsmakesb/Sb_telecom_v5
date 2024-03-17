package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Myprofile extends AppCompatActivity {
    String FinalJSonObject;
    /* Foysal Tech && Ict Foysal */
    public EditText birth;
    public String birtn;
    public int f201dd;
    String device;
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public EditText email;
    /* Foysal Tech && Ict Foysal */
    public String emaili;
    int flag = 0;
    public String f202id;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    public int f204mm;
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    /* Foysal Tech && Ict Foysal */
    public String namei;
    /* Foysal Tech && Ict Foysal */
    public EditText nick;
    /* Foysal Tech && Ict Foysal */
    public String nicki;
    /* Foysal Tech && Ict Foysal */
    public EditText nid;
    /* Foysal Tech && Ict Foysal */
    public String nidn;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    public EditText f205te;
    /* Foysal Tech && Ict Foysal */
    public String tel;
    String token;
    String url;
    Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.myprofile);

        getWindow().setSoftInputMode(2);
        nick = (EditText) findViewById(R.id.nick);
        email = (EditText) findViewById(R.id.email);
        pin = (EditText) findViewById(R.id.opin);
        name = (EditText) findViewById(R.id.aname);
        f205te = (EditText) findViewById(R.id.phn);
        birth = (EditText) findViewById(R.id.birth);
        nid = (EditText) findViewById(R.id.nid);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        
        myinfo();
        
        birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = birth.getText().toString();
                if (obj == null || obj.isEmpty() || obj.equals("null")) {
                    @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(Myprofile.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            birth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                        }
                    }, 2000, f204mm, f201dd);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    datePickerDialog.show();
                }
            }
        });
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Myprofile myprofile = Myprofile.this;
                if (!myprofile.isOnline(myprofile)) {
                    Toast.makeText(Myprofile.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (nick.length() < 4) {
                    Toast.makeText(Myprofile.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                } else {
                    new loginAccess().execute(new String[0]);
                }
            }
        });

        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
            
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Myprofile.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Myprofile.getPref("phone", getApplicationContext());
            Myprofile.getPref("pass", getApplicationContext());
            Myprofile.getPref("pin", getApplicationContext());
            nick.getText().toString();
            String obj = email.getText().toString();
            String obj2 = name.getText().toString();
            String obj3 = f205te.getText().toString();
            String obj4 = pin.getText().toString();
            String obj5 = nid.getText().toString();
            String obj6 = birth.getText().toString();
            String pref = Myprofile.getPref("token", getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", Myprofile.getPref("device", getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("email", obj));
            arrayList.add(new BasicNameValuePair("id", f202id));
            arrayList.add(new BasicNameValuePair("mobile", obj3));
            arrayList.add(new BasicNameValuePair("nid", obj5));
            arrayList.add(new BasicNameValuePair("birth", obj6));
            arrayList.add(new BasicNameValuePair("mypin", obj4));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj2));
            arrayList.add(new BasicNameValuePair("self", "yes"));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Myprofile.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "resellerEdit", HttpPost.METHOD_NAME, arrayList);
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
                            Toast.makeText(Myprofile.this, "Faild: " + string, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Myprofile.this, "Update successful", Toast.LENGTH_LONG).show();
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
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    private void myinfo() {
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
        token = getPref("token", getApplicationContext());
        device = getPref("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, url + "/reseller?self=yes&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                Myprofile myprofile = Myprofile.this;
                new ItemParseJSonDataClass(myprofile).execute(new Void[0]);
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
            this.context = context2;
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(FinalJSonObject);
                jsonarray = jSONObject.getJSONArray("bmtelbd");
                for (int i = 0; i < jsonarray.length(); i++) {
                    new HashMap();
                    JSONObject jSONObject2 = jsonarray.getJSONObject(i);
                    nicki = jSONObject2.getString("username");
                    namei = jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                    emaili = jSONObject2.getString("email");
                    f202id = jSONObject2.getString("id");
                    tel = jSONObject2.getString("tel");
                    birtn = jSONObject2.getString("birth");
                    nidn = jSONObject2.getString("nid");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            nick.setText(nicki);
                            name.setText(namei);
                            if (nidn.indexOf("null") < 0) {
                                nid.setText(nidn);
                                nid.setClickable(false);
                                nid.setFocusable(false);
                            }
                            if (birtn.indexOf("null") < 0) {
                                birth.setText(birtn);
                            }
                            if (emaili != null) {
                                email.setText(emaili);
                            }
                            if (tel.indexOf("null") < 0) {
                                f205te.setText(tel);
                            }
                        }
                    });
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

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
