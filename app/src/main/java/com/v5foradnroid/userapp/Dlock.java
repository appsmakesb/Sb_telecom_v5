package com.v5foradnroid.userapp;

import android.app.Activity;
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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.Hashtable;
import java.util.Map;
import org.antlr.runtime.debug.DebugEventListener;
import org.json.JSONException;
import org.json.JSONObject;

public class Dlock extends Activity {
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;

    /* renamed from: ck */
    String f171ck;
    String device;
    Dialog dialog;
    int flag = 0;
    TextView googletxt;
    String hash;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private EditText mobile_number;
    String number;
    /* Foysal Tech && Ict Foysal */
    public RadioButton off;
    /* Foysal Tech && Ict Foysal */

    /* renamed from: on */
    public RadioButton f172on;
    private ProgressDialog pDialog;
    /* Foysal Tech && Ict Foysal */
    public EditText password;
    String pwd;
    /* Foysal Tech && Ict Foysal */
    public RadioGroup radioGroup;
    Button signin;
    String token;
    String type;
    Button unlocks;
    String url;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.dlock);
        this.radioGroup = (RadioGroup) findViewById(R.id.bal);
        this.off = (RadioButton) findViewById(R.id.off);
        this.f172on = (RadioButton) findViewById(R.id.on);
        this.login = (Button) findViewById(R.id.sub);
        this.password = (EditText) findViewById(R.id.opin);
        this.f171ck = "0";
        verifystep();
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Dlock.this.password.length() < 4) {
                    Toast.makeText(Dlock.this, "Please Enter correct pin", Toast.LENGTH_LONG).show();
                } else if (!isOnline(Dlock.this)) {
                    Toast.makeText(Dlock.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    Dlock.this.f171ck = DebugEventListener.PROTOCOL_VERSION;
                    Dlock.this.verifystep();
                }
            }

            private boolean isOnline(Context context) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            }
        });
    }

    /* Foysal Tech && Ict Foysal */
    public void verifystep() {
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
        StringRequest r1 = new StringRequest(1, this.url + "dlock", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                Dlock.this.FinalJSonObject = str;
                Dlock dlock = Dlock.this;
                new ParseJSonDataClass(dlock).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Dlock.this.dialog.dismiss();
                Toast.makeText(Dlock.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                if (Dlock.this.radioGroup.getCheckedRadioButtonId() == R.id.off) {
                    Dlock.this.type = DebugEventListener.PROTOCOL_VERSION;
                } else {
                    Dlock.this.type = "0";
                }
                if (Dlock.this.f171ck.equals("0")) {
                    Dlock.this.type = ExifInterface.GPS_MEASUREMENT_3D;
                }
                String obj = Dlock.this.password.getText().toString();
                hashtable.put("token", Dlock.this.token);
                hashtable.put("tar", Dlock.this.type);
                hashtable.put("pin", obj);
                hashtable.put("username", Dlock.this.number);
                hashtable.put("password", Dlock.this.pwd);
                hashtable.put("deviceid", Dlock.this.device);
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
                JSONObject jSONObject = new JSONObject(Dlock.this.FinalJSonObject);
                final int i = jSONObject.getInt("type");
                if (jSONObject.getInt("success") == 1) {
                    Dlock.this.runOnUiThread(new Runnable() {
                        public void run() {
                            (i == 1 ? Dlock.this.off : Dlock.this.f172on).setChecked(true);
                            if (Dlock.this.f171ck.equals(DebugEventListener.PROTOCOL_VERSION)) {
                                Toast.makeText(Dlock.this, "Setting updated", Toast.LENGTH_LONG).show();
                                Dlock.this.startActivity(new Intent(Dlock.this.getApplicationContext(), Welcome.class));
                                Dlock.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                                Dlock.this.finish();
                            }
                        }
                    });
                    Dlock.this.flag = 0;
                    return null;
                }
                Dlock.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            if (Dlock.this.flag == 1) {
                Toast.makeText(Dlock.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            Dlock.this.dialog.dismiss();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
