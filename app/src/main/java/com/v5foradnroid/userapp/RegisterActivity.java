package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    ArrayAdapter aaa;
    private EditText amount;
    AutoLinkTextView autoLinkTextView;
    /* Foysal Tech && Ict Foysal */
    public EditText birth;
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public EditText email;
    int flag = 0;
    String ibirth;
    String iname;
    String inid;
    JSONParser jsonParser = new JSONParser();
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    /* Foysal Tech && Ict Foysal */
    public EditText nick;
    /* Foysal Tech && Ict Foysal */
    public EditText nid;
    /* Foysal Tech && Ict Foysal */
    public EditText pass;
    LinearLayout payline;
    /* Foysal Tech && Ict Foysal */
    public EditText phone;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    LinearLayout reglin;
    Button sub;
    String text;
    /* Foysal Tech && Ict Foysal */
    public EditText trnx;
    int type = 0;

    /* Foysal Tech && ict Foysal */
    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_register);
        getWindow().setSoftInputMode(2);
        String[] split = Addres.getPref("all_level", getApplicationContext()).split("\",\"|\\[\"|\"\\]");
        changeStatusBarColor();
        sub = (Button) findViewById(R.id.reg);
        autoLinkTextView = findViewById(R.id.pp);
        reglin = findViewById(R.id.re);
        payline = findViewById(R.id.pay);
        trnx = findViewById(R.id.trnx);
        amount = findViewById(R.id.amount);
        nick = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pin = findViewById(R.id.pin);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        phone = findViewById(R.id.mobile_number);
        nid = findViewById(R.id.nid);
        birth = findViewById(R.id.birth);
        Spinner spinner = (Spinner) findViewById(R.id.lev);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.custom_spinner_item, split);
        aaa = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(aaa);
        Intent intent = getIntent();
        inid = intent.getStringExtra("nid");
        iname = intent.getStringExtra(AppMeasurementSdk.ConditionalUserProperty.NAME);
        String stringExtra = intent.getStringExtra("birth");
        ibirth = stringExtra;
        birth.setText(stringExtra);
        nid.setText(inid);
        name.setText(iname);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                text = adapterView.getItemAtPosition(i).toString();
            }
        });
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (nick.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                    return;
                }
                name.length();
                if (trnx.length() > 2) {
                    type = 1;
                }
                new loginAccess().execute(new String[0]);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (nick.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                    return;
                }
                name.length();
                if (trnx.length() > 2) {
                    type = 1;
                }
                new loginAccess().execute(new String[0]);
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login_Activity.class));
        overridePendingTransition(0, 0);
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(RegisterActivity.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Welcome.getPref("token", getApplicationContext());
            Welcome.getPref("device", getApplicationContext());
            final String obj = nick.getText().toString();
            String obj2 = email.getText().toString();
            String obj3 = name.getText().toString();
            String obj4 = phone.getText().toString();
            final String obj5 = pass.getText().toString();
            String obj6 = pin.getText().toString();
            String obj7 = nid.getText().toString();
            String obj8 = birth.getText().toString();
            String obj9 = trnx.getText().toString();
            String str = text;
            String str2 = FirebaseAnalytics.Param.SUCCESS;
            arrayList.add(new BasicNameValuePair("level", str));
            arrayList.add(new BasicNameValuePair("type", "" + type));
            arrayList.add(new BasicNameValuePair("trnx", obj9));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("username", obj));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj3));
            arrayList.add(new BasicNameValuePair("email", obj2));
            arrayList.add(new BasicNameValuePair("birth", obj8));
            arrayList.add(new BasicNameValuePair("nid", obj7));
            arrayList.add(new BasicNameValuePair("phone", obj4));
            arrayList.add(new BasicNameValuePair("client_types", "16840"));
            arrayList.add(new BasicNameValuePair("password", obj5));
            arrayList.add(new BasicNameValuePair("resellerpin", obj6));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Welcome.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "resellerautoadd", HttpPost.METHOD_NAME, arrayList);
            } catch (NullPointerException | IOException e) {
                throw new RuntimeException(e);
            }
            String str3 = str2;
            try {
                int i = makeHttpRequest.getInt(str3);
                int i2 = makeHttpRequest.getInt(str3);
                if (type == 0 && makeHttpRequest.getString("amount").equals("0")) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            type = 1;
                            new loginAccess().execute(new String[0]);
                        }
                    });
                }
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showError(RegisterActivity.this, string);
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
                        reglin.setVisibility(View.GONE);
                        payline.setVisibility(View.VISIBLE);
                        setTextInTextViewb(string);
                        if (type == 1) {
                            loginAccess.this.SavePreferences("phone", obj);
                            loginAccess.this.SavePreferences("pass", obj5);
                            loginAccess.this.SavePreferences("pin", obj5);
                            loginAccess.this.SavePreferences(ClientCookie.PATH_ATTR, "");
                            loginAccess.this.SavePreferences("pathb", "");
                            Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Start.class));
                            finish();
                        }
                    }
                });
                return null;
            } catch (NullPointerException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(String str) {
            dialog.dismiss();
            if (flag == 1) {
                Toast.makeText(RegisterActivity.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }

        public void SavePreferences(String str, String str2) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            edit.putString(str, str2);
            edit.commit();
        }
    }

    public void setTextInTextViewb(String str) {
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_PHONE);
        autoLinkTextView.setAutoLinkText(str);
        autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @SuppressLint("WrongConstant")
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String str) {
                ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence) null, str));
                Toast.makeText(RegisterActivity.this, "Copied " + str, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType")
        Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, Login_Activity.class));
        overridePendingTransition(0, 0);
        finish();
    }

}
