package com.v5foradnroid.userapp;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    /* Foysal Tech && Ict Foysal */
    public EditText f219am;
    private String bankbal;
    private String cNumber;
    Dialog dialog;
    private String drivebal;
    private EditText email_id;
    private TextView err;
    int flag = 0;
    /* Foysal Tech && Ict Foysal */
    
    public String f220id;
    JSONParser jsonParser = new JSONParser();
    Button login;

    /* textSize="13sp" */
    private com.v5foradnroid.userapp.dialog f221md;
    private com.v5foradnroid.userapp.dialog mdd;
    private dialogs mds;
    /* Foysal Tech && Ict Foysal */
    public EditText msg;
    private String nicki;
    private String oldam;
    String opn;
    String optr;
    String paid;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    /* Foysal Tech && Ict Foysal */
    public RadioGroup radioGroup;
    Button signin;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.payment);
        this.f219am = (EditText) findViewById(R.id.amount);
        this.pin = (EditText) findViewById(R.id.pin);
        this.msg = (EditText) findViewById(R.id.msg);
        Intent intent = getIntent();
        this.nicki = intent.getStringExtra("nick");
        this.f220id = intent.getStringExtra("id");
        this.oldam = intent.getStringExtra("amount");
        this.bankbal = intent.getStringExtra("bamount");
        this.drivebal = intent.getStringExtra("damount");
        ((TextView) findViewById(R.id.cba)).setText("Main:" + this.oldam + " Bank: " + this.bankbal + " Drive:" + this.drivebal);
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Payment payment = Payment.this;
                if (!payment.isOnline(payment)) {
                    Toast.makeText(Payment.this, "No network connection", Toast.LENGTH_LONG).show();
                    return;
                }
                Payment.this.paid = "Payment";
                Payment.this.f219am.getText().toString();
                new loginAccess().execute(new String[0]);
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            Payment.this.dialog = new Dialog(Payment.this);
            Payment.this.dialog.requestWindowFeature(1);
            Payment.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Payment.this.dialog.setCancelable(false);
            Payment.this.dialog.setContentView(R.layout.custom_progress);
            Payment.this.dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Payment.getPref("phone", Payment.this.getApplicationContext());
            Payment.getPref("pass", Payment.this.getApplicationContext());
            Payment.getPref("pin", Payment.this.getApplicationContext());
            String pref = Payment.getPref("token", Payment.this.getApplicationContext());
            String pref2 = Payment.getPref("device", Payment.this.getApplicationContext());
            Payment payment = Payment.this;
            EditText unused = payment.f219am = (EditText) payment.findViewById(R.id.amount);
            Payment payment2 = Payment.this;
            RadioGroup unused2 = payment2.radioGroup = (RadioGroup) payment2.findViewById(R.id.bal);
            int checkedRadioButtonId = Payment.this.radioGroup.getCheckedRadioButtonId();
            String obj = Payment.this.msg.getText().toString();
            String obj2 = Payment.this.f219am.getText().toString();
            String obj3 = Payment.this.pin.getText().toString();
            arrayList.add(new BasicNameValuePair("deviceid", pref2));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("amount", obj2));
            arrayList.add(new BasicNameValuePair("ucid", Payment.this.f220id));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_MESSAGE, obj));
            arrayList.add(new BasicNameValuePair("item", "pay"));
            arrayList.add(new BasicNameValuePair("type", "Transfer"));
            if (checkedRadioButtonId == R.id.main) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "main"));
            } else if (checkedRadioButtonId == R.id.drive) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "drive"));
            } else {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "bank"));
            }
            arrayList.add(new BasicNameValuePair("pincode", obj3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Payment.this.jsonParser.makeHttpRequest((Payment.getPref(ImagesContract.URL, Payment.this.getApplicationContext()) + "/apiapp/") + "payment", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Payment.this.flag = 0;
                    Payment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Payment.this.showError(Payment.this, string);
                        }
                    });
                }
                if (i == 1) {
                    Payment.this.flag = 0;
                } else {
                    Payment.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Payment.this.flag = 0;
                Payment.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Payment.this, "successful", Toast.LENGTH_LONG).show();
                        Payment.this.startActivity(new Intent(Payment.this.getApplicationContext(), Myreseller.class));
                        Payment.this.finish();
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
            Payment.this.dialog.dismiss();
        }
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
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

    public void setTextInTextView(String str) {
        this.err.setText(str);
    }

    public void showError(Activity activity, String str) {
        Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }
}
