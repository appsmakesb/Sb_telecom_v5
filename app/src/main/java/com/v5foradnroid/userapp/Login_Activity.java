package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Login_Activity extends AppCompatActivity {

    Toolbar toolbar;
    Intent intent;
    LinearLayout lay_whatsapp, lay_telegram, lay_facebook, lay_call;
    String shop, telegram, whatsapp, youtube;
    private static final String Pint = "otp";
    String FinalJSonObject;
    String device;
    Dialog dialog;
    int flag = 0;
    Button login, reg;
    private EditText mobile_number;
    String msg, number, pwd, token, url;
    public EditText password;
    /* Foysal Tech && ict Foysal */

    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log In");
        login = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.reg);
        mobile_number = (EditText) findViewById(R.id.mobile_number);
        password = (EditText) findViewById(R.id.password);
        lay_whatsapp = findViewById(R.id.whatsapp);
        lay_telegram = findViewById(R.id.telegram);
        lay_facebook = findViewById(R.id.facebook);
        lay_call = findViewById(R.id.call);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (password.length() < 4) {
                    Toast.makeText(Login_Activity.this, "Please Enter correct password", Toast.LENGTH_LONG).show();
                } else if (!isOnline(Login_Activity.this)) {
                    Toast.makeText(Login_Activity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    start();
                }
            }
            private boolean isOnline(Context context) {
                @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isOnline(Login_Activity.this)) {
                    Toast.makeText(Login_Activity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(Login_Activity.this, Addpic.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            }
            private boolean isOnline(Context context) {
                @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public static String getPref2(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    public static String random() {
        char[] charArray = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 36; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void onLoginClickb(View view) {
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/policy";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        url = "https://" + url;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    /* Foysal Tech && Ict Foysal */
    public void start() {
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
        url = "https://" + url;
        number = this.mobile_number.getText().toString();
        pwd = this.password.getText().toString();
        device = getPref2("device", getApplicationContext());
        token = getPref("token", getApplicationContext());
        if (device.indexOf("no") >= 0) {
            SavePreferences("device", random());
        }
        device = getPref2("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, url + FirebaseAnalytics.Param.INDEX, new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                Login_Activity loginActivity = Login_Activity.this;
                new ParseJSonDataClass(loginActivity).execute(new Void[0]);
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
                hashtable.put("username", number);
                hashtable.put("password", pwd);
                hashtable.put("deviceid", device);
                hashtable.put("aut", "auto");
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


        lay_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whatsapp != null && !whatsapp.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(whatsapp));
                    Patterns.WEB_URL.matcher(whatsapp).matches();
                    startActivity(intent);
                }
            }
        });

        lay_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telegram != null && !telegram.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(telegram));
                    Patterns.WEB_URL.matcher(telegram).matches();
                    startActivity(intent);
                }
            }
        });

        lay_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtube != null && !youtube.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(youtube));
                    Patterns.WEB_URL.matcher(youtube).matches();
                    startActivity(intent);
                }
            }
        });

    }

    public void forgotAction(View view) {
        Toast.makeText(this, "Why did you forget? Try to remember", Toast.LENGTH_SHORT).show();
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ParseJSonDataClass(Context context2) {
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
                if (jSONObject.getInt("stat") == 1) {
                    Login_Activity loginActivity = Login_Activity.this;
                    loginActivity.SavePreferences("phone", loginActivity.number);
                    Login_Activity loginActivity2 = Login_Activity.this;
                    loginActivity2.SavePreferences("pass", loginActivity2.pwd);
                }
                msg = jSONObject.getString("message");
                if (jSONObject.getInt("success") == 1) {
                    token = jSONObject.getString("token");
                    Login_Activity loginActivity3 = Login_Activity.this;
                    loginActivity3.SavePreferences("token", loginActivity3.token);
                    Login_Activity loginActivity4 = Login_Activity.this;
                    loginActivity4.SavePreferences("phone", loginActivity4.number);
                    Login_Activity loginActivity5 = Login_Activity.this;
                    loginActivity5.SavePreferences("pass", loginActivity5.pwd);
                    SavePreferences("otpchoose", jSONObject.getString("choseotp"));
                    SavePreferences("postlevel", jSONObject.getString("postlevel"));
                    flag = 0;
                    if (jSONObject.getInt(Login_Activity.Pint) == 1) {
                        Intent intent = new Intent(getApplicationContext(), pinver.class);
                        intent.putExtra("mobile_number", number);
                        intent.putExtra("password", pwd);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                        finish();
                        return null;
                    }
                    Intent intent2 = new Intent(getApplicationContext(), Welcome.class);
                    intent2.putExtra("mobile_number", number);
                    intent2.putExtra("password", pwd);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    finish();
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
            dialog.dismiss();
            if (flag == 1) {
                Toast.makeText(Login_Activity.this, "" + msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
