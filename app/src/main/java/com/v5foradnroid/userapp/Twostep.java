package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.reginald.patternlockview.PatternLockView;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import org.antlr.runtime.debug.DebugEventListener;
import org.json.JSONException;
import org.json.JSONObject;

public class Twostep extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final PatternLockView.Password sDefaultPassword = new PatternLockView.Password(Arrays.asList(new Integer[]{1, 2, 3, 4}));
    String FinalJSonObject;

    /* renamed from: ck */
    String f286ck;
    String device;
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public RadioButton email;
    int flag = 0;
    /* Foysal Tech && Ict Foysal */
    public RadioButton google;
    TextView googletxt;
    String hash;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private PatternLockView mCurLockView;
    private PatternLockView.Password mPassword = sDefaultPassword;
    /* Foysal Tech && Ict Foysal */
    public RadioButton mobile;
    private EditText mobile_number;
    String number;
    /* Foysal Tech && Ict Foysal */
    public RadioButton off;
    private ProgressDialog pDialog;
    /* Foysal Tech && Ict Foysal */
    public RadioButton pattern;
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
        setContentView(R.layout.twostep);
        this.radioGroup = (RadioGroup) findViewById(R.id.bal);
        this.off = (RadioButton) findViewById(R.id.off);
        this.email = (RadioButton) findViewById(R.id.email);
        this.mobile = (RadioButton) findViewById(R.id.mobile);
        this.pattern = (RadioButton) findViewById(R.id.pattern);
        this.google = (RadioButton) findViewById(R.id.google);
        this.googletxt = (TextView) findViewById(R.id.googlet);
        this.login = (Button) findViewById(R.id.sub);
        this.f286ck = "0";
        verifystep();
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isOnline(Twostep.this)) {
                    Toast.makeText(Twostep.this, "No network connection", Toast.LENGTH_LONG).show();
                    return;
                }
                Twostep.this.f286ck = DebugEventListener.PROTOCOL_VERSION;
                Twostep.this.verifystep();
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
                Twostep.this.FinalJSonObject = str;
                Twostep twostep = Twostep.this;
                new ParseJSonDataClass(twostep).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Twostep.this.dialog.dismiss();
                Toast.makeText(Twostep.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("tar", "4");
                int checkedRadioButtonId = Twostep.this.radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.email) {
                    Twostep.this.type = ExifInterface.GPS_MEASUREMENT_3D;
                } else if (checkedRadioButtonId == R.id.mobile) {
                    Twostep.this.type = "4";
                } else if (checkedRadioButtonId == R.id.pattern) {
                    Twostep.this.type = "5";
                } else if (checkedRadioButtonId == R.id.google) {
                    Twostep.this.type = DebugEventListener.PROTOCOL_VERSION;
                } else {
                    Twostep.this.type = "0";
                }
                hashtable.put("ck", Twostep.this.f286ck);
                hashtable.put("token", Twostep.this.token);
                hashtable.put("type", Twostep.this.type);
                hashtable.put("username", Twostep.this.number);
                hashtable.put("password", Twostep.this.pwd);
                if (!TextUtils.isEmpty(Twostep.this.hash)) {
                    hashtable.put("hash", Twostep.this.hash);
                }
                hashtable.put("deviceid", Twostep.this.device);
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
                JSONObject jSONObject = new JSONObject(Twostep.this.FinalJSonObject);
                jSONObject.getString("message");
                final int i = jSONObject.getInt("type");
                if (jSONObject.getInt("success") == 1) {
                    Twostep.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (i == 0) {
                                Twostep.this.off.setChecked(true);
                            }
                            if (i == 1) {
                                Twostep.this.googletxt.setVisibility(View.VISIBLE);
                                Twostep.this.google.setVisibility(View.VISIBLE);
                                Twostep.this.google.setChecked(true);
                            }
                            if (i == 3) {
                                Twostep.this.email.setChecked(true);
                            }
                            if (i == 4) {
                                Twostep.this.mobile.setChecked(true);
                            }
                            if (i == 5) {
                                Twostep.this.pattern.setChecked(true);
                            }
                            if (Twostep.this.f286ck.equals(DebugEventListener.PROTOCOL_VERSION)) {
                                Toast.makeText(Twostep.this, "Setting updated", Toast.LENGTH_LONG).show();
                                Twostep.this.startActivity(new Intent(Twostep.this.getApplicationContext(), Welcome.class));
                                Twostep.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                                Twostep.this.finish();
                            }
                        }
                    });
                    Twostep.this.flag = 0;
                    return null;
                }
                Twostep.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            if (Twostep.this.flag == 1) {
                Toast.makeText(Twostep.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            Twostep.this.dialog.dismiss();
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

    public void action(View view) {
        pattern_lock_view(this);
    }

    public void pattern_lock_view(Activity activity) {
        @SuppressLint("ResourceType") final Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.pattern_lock);
        this.mCurLockView = (PatternLockView) dialog2.findViewById(R.id.lock_view);
        final Button button = (Button) dialog2.findViewById(R.id.sub);
        this.mCurLockView.setPatternVisible(true);
        this.mPassword = sDefaultPassword;
        this.mCurLockView.stopPasswordAnim();
        this.mCurLockView.reset();
        this.mCurLockView.setCallBack(new PatternLockView.CallBack() {
            public int onFinish(PatternLockView.Password password) {
                if (password.list.size() >= 4) {
                    button.setVisibility(View.VISIBLE);
                    Twostep.this.hash = password.string;
                }
                return 0;
            }
        });
        this.mCurLockView.setOnNodeTouchListener(new PatternLockView.OnNodeTouchListener() {
            public void onNodeTouched(int i) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Twostep.this.f286ck = DebugEventListener.PROTOCOL_VERSION;
                Twostep.this.verifystep();
            }
        });
        dialog2.show();
    }
}
