package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class makepay extends Activity {
    private static final String TAG_Balance = "message";
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    /* Foysal Tech && Ict Foysal */
    public EditText f337am;
    AutoLinkTextView autoLinkTextView;
    private String cNumber, datapi;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private dialogs mds;
    String opn;
    String optr;
    private ProgressDialog pDialog;
    String paid;
    private TextInputEditText textEmEdit;
    private TextView f338pp, show_tktv, var_transaction_tv;
    private ConstraintLayout conLayBg;
    private ImageView var_icon_img;
    Button signin;
    int receivedDrawableResId;
    public String source, amvar, var_mbtype;
    /* Foysal Tech && Ict Foysal */
    public EditText f339tr;
    private String lasttextData, fasttextData;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.new_uidi_three);
//        Button button = (Button) findViewById(R.id.cont);
        this.f339tr = (EditText) findViewById(R.id.trnx);
        this.f337am = (EditText) findViewById(R.id.amount);
        this.f338pp = (TextView) findViewById(R.id.pp);
        this.show_tktv = (TextView) findViewById(R.id.show_tktv);
        this.var_transaction_tv = (TextView) findViewById(R.id.transaction_id_uptv);
        this.var_icon_img = (ImageView) findViewById(R.id.icon_img);
        this.textEmEdit = findViewById(R.id.pin);
        this.autoLinkTextView = (AutoLinkTextView) findViewById(R.id.pp);
        this.conLayBg = findViewById(R.id.constraintLayout2);
        Intent intent = getIntent();
        receivedDrawableResId = intent.getIntExtra("drawableResId", 0);
        this.source = intent.getStringExtra(FirebaseAnalytics.Param.SOURCE);
        this.amvar = intent.getStringExtra("amvar");
        this.var_mbtype = intent.getStringExtra("mbtype");

        if (amvar != null) {
            f337am.setText(amvar);
            show_tktv.setText("৳ " + amvar);
        }
        var_icon_img.setImageResource(receivedDrawableResId);
        var_transaction_tv.setText("Enter " + var_mbtype + " Transaction Id");
        this.autoLinkTextView.setPhoneModeColor(ContextCompat.getColor(this, R.color.black));


        if (var_mbtype.equals("bkash personal")) {
            fasttextData = "Bkash";
            lasttextData = "dxj";
        } else if (var_mbtype.equals("Nagad personal")) {
            fasttextData = "Nogad";
            lasttextData = "jdc";
        } else if (var_mbtype.equals("Rocket personal")) {
            fasttextData = "Rocket";
            lasttextData = "mjh";
        } else if (var_mbtype.equals("Upay personal")) {
            fasttextData = "Upay";
            lasttextData = "hjk";
        }

        /*String colorCode = "#E43473";
        conLayBg.setBackgroundColor(Color.parseColor(colorCode));*/

        datapi = getPref("pin", getApplicationContext());
        textEmEdit.setText(datapi);

        new loginAccessb().execute(new String[0]);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                makepay makepay = makepay.this;
                if (!makepay.isOnline(makepay)) {
                    Toast.makeText(makepay.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (makepay.this.f339tr.length() < 4) {
                    Toast.makeText(makepay.this, "Please Enter correct Trnx id", Toast.LENGTH_LONG).show();
                } else {
                    makepay.this.f337am.getText().toString();
                    makepay.this.f339tr.getText().toString();
                    new loginAccess().execute(new String[0]);
                }
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            makepay.this.dialog = new Dialog(makepay.this);
            makepay.this.dialog.requestWindowFeature(1);
            makepay.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            makepay.this.dialog.setCancelable(false);
            makepay.this.dialog.setContentView(R.layout.custom_progress);
            makepay.this.dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            makepay.getPref("phone", makepay.this.getApplicationContext());
            makepay.getPref("pass", makepay.this.getApplicationContext());
            String pref = makepay.getPref("pin", makepay.this.getApplicationContext());
            makepay makepay = makepay.this;
            EditText unused = makepay.f337am = (EditText) makepay.findViewById(R.id.amount);
            String obj = makepay.this.f337am.getText().toString();
            String obj2 = makepay.this.f339tr.getText().toString();
            String pref2 = makepay.getPref("token", makepay.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", makepay.getPref("device", makepay.this.getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref2));
            arrayList.add(new BasicNameValuePair("amount", obj));
            arrayList.add(new BasicNameValuePair("number", obj2));
            arrayList.add(new BasicNameValuePair("payid", "ok"));
            arrayList.add(new BasicNameValuePair("item", "chk"));
            arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, makepay.this.source));
            arrayList.add(new BasicNameValuePair("pin", pref));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = makepay.this.jsonParser.makeHttpRequest((getPref(ImagesContract.URL, makepay.this.getApplicationContext()) + "/apiapp/") + "makepay", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d("Create Response", makeHttpRequest.toString());
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString(makepay.TAG_Balance);
                if (i2 == 0) {
                    makepay.this.flag = 0;
                    makepay.this.runOnUiThread(new Runnable() {
                        public void run() {
                            makepay.this.showError(makepay.this, string);
                        }
                    });
                }
                if (i == 1) {
                    makepay.this.flag = 0;
                } else {
                    makepay.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                makepay.this.flag = 0;
                makepay.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(makepay.this, "Successful :" + string, Toast.LENGTH_LONG).show();
                        makepay.this.startActivity(new Intent(makepay.this.getApplicationContext(), Welcome.class));
                        makepay.this.finish();
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
            makepay.this.dialog.dismiss();
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

    public void setTextInTextView(String str) {
        this.err.setText(str);
    }

    public void setTextInTextViewb(String str) {
        this.autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_PHONE);
        int indexOfBkash = str.indexOf(fasttextData);
        int lastIndexOfPossibility = str.lastIndexOf(lasttextData);
        if (indexOfBkash != -1 && lastIndexOfPossibility != -1) {
            String extractedText = str.substring(indexOfBkash, lastIndexOfPossibility + lasttextData.length());
            this.autoLinkTextView.setAutoLinkText(extractedText.replace("dxj", "").replace("jdc", "").replace("mjh", "").replace("hjk", ""));
            autoLinkTextView.setPhoneModeColor(ContextCompat.getColor(this, R.color.black));
        } else {
            System.out.println("Couldn't find 'Bkash' or 'sfd' in the text.");
        }

        this.autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @SuppressLint("WrongConstant")
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String str) {
                ((ClipboardManager) makepay.this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence) null, str));
                Toast.makeText(makepay.this, "Copied " + str, Toast.LENGTH_LONG).show();
            }
        });
    }

    class loginAccessb extends AsyncTask<String, String, String> {
        loginAccessb() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            makepay.this.dialog = new Dialog(makepay.this);
            makepay.this.dialog.requestWindowFeature(1);
            makepay.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            makepay.this.dialog.setCancelable(false);
            makepay.this.dialog.setContentView(R.layout.custom_progress);
            makepay.this.dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            makepay.getPref("phone", makepay.this.getApplicationContext());
            makepay.getPref("pass", makepay.this.getApplicationContext());
            String pref = makepay.getPref("pin", makepay.this.getApplicationContext());
            String pref2 = makepay.getPref("token", makepay.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", makepay.getPref("device", makepay.this.getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref2));
            arrayList.add(new BasicNameValuePair("payinfo", "ok"));
            arrayList.add(new BasicNameValuePair("item", "pi"));
            arrayList.add(new BasicNameValuePair("pin", pref));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = makepay.this.jsonParser.makeHttpRequest((makepay.getPref(ImagesContract.URL, makepay.this.getApplicationContext()) + "/apiapp/") + "payinfo", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d("Create Response", makeHttpRequest.toString());
            try {
                final String string = makeHttpRequest.getString(makepay.TAG_Balance);
                if (makeHttpRequest.getInt("success") == 1) {
                    makepay.this.runOnUiThread(new Runnable() {
                        public void run() {
                            makepay.this.setTextInTextViewb(string);
                        }
                    });
                    makepay.this.flag = 0;
                    return null;
                }
                makepay.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(String str) {
            makepay.this.dialog.dismiss();
            if (makepay.this.flag == 1) {
                Toast.makeText(makepay.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }
}
