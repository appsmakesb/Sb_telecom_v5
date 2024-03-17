package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

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
import java.util.concurrent.Executor;

import org.json.JSONException;
import org.json.JSONObject;

public class pinver extends AppCompatActivity {

    /* Foysal Tech && Ict Foysal */
    Toolbar toolbar;
    public static final String TAG = Login_Activity.class.getName();
    private static final PatternLockView.Password sDefaultPassword = new PatternLockView.Password(Arrays.asList(new Integer[]{1, 2, 3, 4}));
    String FinalJSonObject;
    int bio = 0;
    BiometricPrompt biometricPrompt;
    String device;
    Dialog dialog;
    Executor executor;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private PatternLockView mCurLockView;
    private PatternLockView.Password mPassword = sDefaultPassword;
    String number;
    String otpc;
    int otpck;
    /* Foysal Tech && Ict Foysal */
    public EditText password;
    BiometricPrompt.PromptInfo promptInfo;
    String pwd, pwdp, token, url;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.pin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Verify your PIN");

        login = findViewById(R.id.pin_verBtn);
        password =  findViewById(R.id.ver_pin);

        pwdp = getPref("pin", getApplicationContext());
        String pref = getPref("otpchoose", getApplicationContext());
        otpc = pref;
        otpck = 0;

        try {
            otpck = Integer.parseInt(pref);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse " + e);
        }
        if (otpck == 5) {
            pattern_lock_view(this);
        }

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt((FragmentActivity) this, executor, (BiometricPrompt.AuthenticationCallback) new BiometricPrompt.AuthenticationCallback() {
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
                super.onAuthenticationSucceeded(authenticationResult);
                bio = 1;
                pwd = pinver.getPref("pass", getApplicationContext());
                start();
            }

            public void onAuthenticationError(int i, CharSequence charSequence) {
                super.onAuthenticationError(i, charSequence);
                Log.d("osman", String.valueOf(charSequence));
                Toast.makeText(pinver.this, charSequence, Toast.LENGTH_LONG).show();
            }

            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(pinver.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
        if (!TextUtils.isEmpty(pwdp) && pwdp.length() > 3 && otpck != 5) {
            BiometricPrompt.PromptInfo build = new BiometricPrompt.PromptInfo.Builder().setTitle("Use Fingerprint").setDescription("Touch Fingerprint sensor no pin required").setNegativeButtonText("Use Pin").build();
            promptInfo = build;
            biometricPrompt.authenticate(build);
        }
        if (otpck == 0) {
            password.setHint((CharSequence) "PIN");
        } else {
            password.setHint((CharSequence) "OTP");
        }

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (password.length() < 4) {
                    Toast.makeText(pinver.this, "Please Enter correct pin", Toast.LENGTH_LONG).show();
                } else if (!isOnline(pinver.this)) {
                    Toast.makeText(pinver.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    bio = 0;
                    start();
                }
            }

            private boolean isOnline(Context context) {
                @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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
        device = getPref("device", getApplicationContext());
        token = getPref("token", getApplicationContext());
        number = getPref("phone", getApplicationContext());
        if (bio == 0) {
            pwdp = password.getText().toString();
        }
        StringRequest r1 = new StringRequest(1, url + "pinchk", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", pwdp);
                FinalJSonObject = str;
                new ParseJSonDataClass(pinver.this).execute(new Void[0]);
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
                hashtable.put("token", token);
                hashtable.put("pin", pwdp);
                hashtable.put("deviceid", device);
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
                if (new JSONObject(FinalJSonObject).getInt("success") == 1) {
                    pinver pinver = pinver.this;
                    pinver.SavePreferences("pin", pinver.pwdp);
                    flag = 0;
                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    intent.putExtra("mobile_number", number);
                    intent.putExtra("password", pwd);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    finish();
                    return null;
                }
                SavePreferences("pin", "0");
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
                Toast.makeText(pinver.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pattern_lock_view(Activity activity) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.pattern_lock);
        PatternLockView patternLockView = (PatternLockView) dialog2.findViewById(R.id.lock_view);
        mCurLockView = patternLockView;
        patternLockView.setPatternVisible(true);
        mPassword = sDefaultPassword;
        mCurLockView.stopPasswordAnim();
        mCurLockView.reset();
        mCurLockView.setCallBack(new PatternLockView.CallBack() {
            public int onFinish(PatternLockView.Password password) {
                Log.d(pinver.TAG, "password length " + password.list.size());
                if (password.list.size() < 4) {
                    return 0;
                }
                Log.d(pinver.TAG, "password  " + password.string);
                pwdp = password.string;
                bio = 1;
                start();
                return 0;
            }
        });
        mCurLockView.setOnNodeTouchListener(new PatternLockView.OnNodeTouchListener() {
            public void onNodeTouched(int i) {
                Log.d(pinver.TAG, "node " + i + " has touched!");
            }
        });
        dialog2.show();
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
