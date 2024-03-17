package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.internal.ImagesContract;

import java.io.IOException;
import java.util.ArrayList;

import org.antlr.runtime.debug.DebugEventListener;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class PinActivity extends AppCompatActivity {

    ViewDialog alert = new ViewDialog();
    /* Foysal Tech && Ict Foysal */
    public String amount;
    int f225bi = 0;
    Dialog dialog;
    int flag = 0;
    String f226h;
    String f227id;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    Button login;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            charSequence.length();
        }
    };

    public TextView f229mn;
    private String number;
    ImageView opl;
    String opn;
    String optr;
    /* Foysal Tech && Ict Foysal */
    public String orderid;
    String paid;
    String paidtype;
    public EditText pin;
    private TextView pkgn;
    /* Foysal Tech && Ict Foysal */
    public RadioButton radioButton;
    public RadioGroup radioGroup;
    private TextView txtam, cost;
    String type;
    String type2;
    String type3;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pin_layout);

        f229mn = (TextView) findViewById(R.id.number);
        pkgn = (TextView) findViewById(R.id.pkgname);
        txtam = (TextView) findViewById(R.id.amount);
        cost = (TextView) findViewById(R.id.cost);
        EditText editText = (EditText) findViewById(R.id.pin);
        pin = editText;

        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditText editText = (EditText) findViewById(R.id.pin);
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
            }
        }, 200);

        login = findViewById(R.id.next);
        intent = getIntent();
        opl = (ImageView) findViewById(R.id.opera);
        type = intent.getExtras().getString("type");
        type2 = intent.getExtras().getString("type2");
        type3 = intent.getExtras().getString("type3");
        opn = intent.getExtras().getString("opt");
        optr = intent.getExtras().getString("opt2");
        if (intent.hasExtra("pkg")) {
            pkgn.setText(intent.getExtras().getString("pkg"));
            orderid = intent.getExtras().getString("id");
        } else {
            pkgn.setVisibility(View.GONE);
        }
        if (intent.hasExtra("paid")) {
            paidtype = intent.getExtras().getString("paid");
            RadioButton radioButton2 = (RadioButton) findViewById(R.id.prepaid);
            RadioButton radioButton3 = (RadioButton) findViewById(R.id.postpaid);
            RadioButton radioButton4 = (RadioButton) findViewById(R.id.skitto);
            if (paidtype.indexOf("Prepaid") >= 0) {
                radioButton2.setChecked(true);
            } else if (paidtype.indexOf("Postpaid") >= 0) {
                radioButton3.setChecked(true);
            }
            if (opn.indexOf("SK") >= 0) {
                radioButton4.setChecked(true);
            }
        }

        number = intent.getExtras().getString("number");
        amount = intent.getExtras().getString("amount");
        f229mn.setText(number);
        txtam.setText(amount);
        cost.setText(amount);
        radioGroup = (RadioGroup) findViewById(R.id.typep);
        if (opn.equals("GP")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.grameenphone));
        } else if (opn.equals("RB")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.robi));
        } else if (opn.equals("BL")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.banglalink));
        } else if (opn.equals("AT")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.airtel));
        } else if (opn.equals("SK")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.skitto));
        } else if (opn.equals("TT")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.teletalk));
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.skitto) {
                    optr = "Skitto";
                    opn = "SK";
                    f225bi = 1;
                    opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.skitto));
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str;
                PinActivity pinActivity;
                PinActivity pinActivity2 = PinActivity.this;
                if (!pinActivity2.isOnline(pinActivity2)) {
                    Toast.makeText(PinActivity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (f229mn.length() < 10) {
                    Toast.makeText(PinActivity.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.postpaid) {
                        pinActivity = PinActivity.this;
                        str = "Postpaid";
                    } else {
                        pinActivity = PinActivity.this;
                        str = "Prepaid";
                    }
                    pinActivity.paid = str;
                    if (checkedRadioButtonId == R.id.skitto) {
                        optr = "Skitto";
                        opn = "SK";
                        paid = "Skitto";
                    }
                    f226h = amount;
                    f229mn.getText().toString();
                    ViewDialog viewDialog = alert;
                    PinActivity pinActivity4 = PinActivity.this;
                    viewDialog.showDialog(pinActivity4, pinActivity4.intent.getExtras().getString("number"), intent.getExtras().getString("amount"), paid, opn);
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
            dialog = new Dialog(PinActivity.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = getPref("phone", getApplicationContext());
            String pref2 = getPref("pass", getApplicationContext());
            String pref3 = getPref("pin", getApplicationContext());
            radioGroup = (RadioGroup) findViewById(R.id.typep);
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(checkedRadioButtonId);
            f226h = amount;
            f227id = f229mn.getText().toString();
            String obj = pin.getText().toString();
            String pref4 = getPref("token", getApplicationContext());
            String pref5 = getPref("device", getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, type3));
            arrayList.add(new BasicNameValuePair("item", type));
            if (intent.hasExtra("rol")) {
                if (intent.getExtras().getString("rol").indexOf("getdrive") >= 0) {
                    arrayList.add(new BasicNameValuePair("orderid", orderid));
                } else if (intent.getExtras().getString("rol").indexOf("getinternet") >= 0) {
                    arrayList.add(new BasicNameValuePair("orderid", orderid));
                } else {
                    arrayList.add(new BasicNameValuePair("orderid", "0"));
                }
            }
            arrayList.add(new BasicNameValuePair("drive", "2022"));
            if (checkedRadioButtonId == R.id.postpaid) {
                arrayList.add(new BasicNameValuePair("type", "2"));
            } else {
                arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            }
            if (intent.getExtras().getString("type2").indexOf("internet") >= 0) {
                arrayList.add(new BasicNameValuePair("pincode", intent.getExtras().getString("pin")));
                arrayList.add(new BasicNameValuePair("number", intent.getExtras().getString("number")));
                arrayList.add(new BasicNameValuePair("amount", intent.getExtras().getString("amount")));
            } else {
                arrayList.add(new BasicNameValuePair("pincode", obj));
                arrayList.add(new BasicNameValuePair("number", f227id));
                arrayList.add(new BasicNameValuePair("amount", f226h));
            }
            arrayList.add(new BasicNameValuePair("optn", opn));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((PinActivity.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "" + "NewRequest", HttpPost.METHOD_NAME, arrayList);
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
                            showError(PinActivity.this, string);
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
                        Toast.makeText(PinActivity.this, "Request Sent Successfull", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Welcome.class));
                        finish();
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

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
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

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public class ViewDialog {
        boolean isStart;
        int progressBarValue = 0;
        Handler progressHandler = new Handler();

        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            @SuppressLint("ResourceType") final Dialog dialog = new Dialog(activity, 2131886564);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            opl = (ImageView) dialog.findViewById(R.id.opera);
            if (str4.equals("GP")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.grameenphone));
            } else if (str4.equals("RB")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.robi));
            } else if (str4.equals("BL")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.banglalink));
            } else if (str4.equals("AT")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.airtel));
            } else if (str4.equals("SK")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.skitto));
            } else if (str4.equals("TT")) {
                opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.teletalk));
            }
            final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.horizontal_progress_bar);
            progressBar.setProgressDrawable(new SeperatedProgressbar(PinActivity.this));
            ((RelativeLayout) dialog.findViewById(R.id.tap)).setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        isStart = true;
                        return true;
                    } else if (action != 1) {
                        return false;
                    } else {
                        isStart = false;
                        return true;
                    }
                }
            });
            Handler r3 = new Handler() {
                public void handleMessage(Message message) {
                    if (isStart) {
                        progressBarValue++;
                    } else {
                        progressBarValue = 0;
                    }
                    progressBar.setProgress(progressBarValue);
                    if (progressBarValue == 100) {
                        dialog.dismiss();
                        new loginAccess().execute(new String[0]);
                    }
                    progressHandler.sendEmptyMessageDelayed(0, 1);
                }
            };
            progressHandler = r3;
            r3.sendEmptyMessage(0);
            dialog.show();
        }
    }

    @SuppressLint("WrongConstant")
    public void showKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    @SuppressLint("WrongConstant")
    public void closeKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(1, 0);
    }
}
