package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Start extends AppCompatActivity {

    private static final String Pint = "otp";
    String FinalJSonObject;
    String ims = "L21kc2FiYmlyLnBocA==";
    String device, ColourCode;
    int flag = 0;
    String msg;
    String number;
    String pwd;
    Shimmer shimmer;
    String token;
    ShimmerTextView f280tv;
    String url;

    /* Foysal Tech && ict Foysal */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.my2);
        if (Develop.DeV!=BuildConfig.WEB_URL){
            Toast.makeText(this, "The app is not genuine!", Toast.LENGTH_SHORT).show();
            return;
        }
        Dev(Develop.DeV);
        FirebaseMessaging.getInstance().subscribeToTopic(getPref("url", getApplicationContext()).replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""));
        isOnline(this);

        String LvUrl = getPref("fourth", getApplicationContext());
        fetchData(LvUrl);

       /*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            public void onComplete(Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    SavePreferences("ftoken", task.getResult().getToken());
                }
            }
        });*/

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(Task<String> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    String token = task.getResult();
                    SavePreferences("ftoken", token);
                }
            }
        });
        start();


        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
//                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

    }

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public static String getldata(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Dev(String str) {
        if (Develop.DeV!=BuildConfig.WEB_URL){
            Toast.makeText(this, "App is not genuine!", Toast.LENGTH_SHORT).show();
            return;
        }
        String genAuthKey = genAuthKey(str);
        String imsOutD = genAuthKey(ims);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(ImagesContract.URL, genAuthKey);
        edit.putString("twoe", genAuthKey + "/ecommerce");
        edit.putString("threes", genAuthKey + "/store");
        edit.putString("fourth", genAuthKey + imsOutD);
        edit.commit();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getPref2(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    @SuppressLint("WrongConstant")
    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
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

    private void start() {
        f280tv = findViewById(R.id.progressBar);
        shimmer = new Shimmer();
        shimmer.start(f280tv);
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + url;
        url = str2;
        Log.d("osman", str2);
        number = getldata("phone", getApplicationContext());
        pwd = getldata("pass", getApplicationContext());
        String pref2 = getPref2("device", getApplicationContext());
        device = pref2;
        if (pref2.indexOf("no") >= 0) {
            SavePreferences("device", random());
        }
        device = getPref2("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, url + "index", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ParseJSonDataClass(Start.this).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                shimmer.cancel();
                ((TextView) findViewById(R.id.er)).setVisibility(View.VISIBLE);
                ((ImageButton) findViewById(R.id.ret)).setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("username", number);
                hashtable.put("ftoken", getPreff("ftoken", getApplicationContext()));
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
                msg = jSONObject.getString("message");
                if (jSONObject.getInt("success") == 1) {
                    token = jSONObject.getString("token");
                    SavePreferences("otpchoose", jSONObject.getString("choseotp"));
                    SavePreferences("postlevel", jSONObject.getString("postlevel"));
                    SavePreferences("phone", number);
                    SavePreferences("pass", pwd);
                    SavePreferences("token", token);
                    flag = 0;
                    if (jSONObject.getInt(Start.Pint) == 1) {
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
                SavePreferences("all_level", jSONObject.getString("alllevel"));
                Intent intent3 = new Intent(getApplicationContext(), Login_Activity.class);
                intent3.putExtra("mobile_number", number);
                intent3.putExtra("password", pwd);
                startActivity(intent3);
                finish();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            if (flag == 1) {
                Toast.makeText(Start.this, "" + msg, Toast.LENGTH_LONG).show();
            }
            shimmer.cancel();
        }
    }

    public void rettry(View view) {
        start();
    }

    public static String getPreff(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    public void fetchData(String lvurl) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, lvurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("AppColour");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ColourCode = jsonObject1.getString("ColourCode");
                        storeColor(Color.parseColor(ColourCode));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Handle error
            }
        });
        Volley.newRequestQueue(Start.this).add(jsonObjectRequest);
    }

    private void storeColor(int color) {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("AppColorCode", color);
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String genAuthKey(String str) {
        byte[] decode = Base64.decode(str.getBytes(), 0);
        Log.e("key", new String(decode, StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
