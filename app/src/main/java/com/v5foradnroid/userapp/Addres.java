package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Addres extends AppCompatActivity {

    Toolbar toolbar;
    ArrayAdapter aaa;
    public EditText f154bl;
    public int f155dd;
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public EditText ebirth;
    /* Foysal Tech && Ict Foysal */
    public EditText email;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    /* Foysal Tech && Ict Foysal */
    public int f156mm;
    /* Foysal Tech && Ict Foysal */
    public EditText name;
    /* Foysal Tech && Ict Foysal */
    public EditText nick;
    public EditText nid;
    public EditText opin;
    private ProgressDialog pDialog;
    /* Foysal Tech && Ict Foysal */
    public EditText pass;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    String text;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add);

        getWindow().setSoftInputMode(2);
        Button button = (Button) findViewById(R.id.reg);
        String[] split = getPref("postlevel", getApplicationContext()).split("\",\"|\\[\"|\"\\]");
        ebirth = findViewById(R.id.birth);
        nick = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pin = findViewById(R.id.pin);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        f154bl = findViewById(R.id.mobile_number);
        nid = findViewById(R.id.nid);
        opin = findViewById(R.id.opin);
        Spinner spinner = findViewById(R.id.lev);
        toolbar = findViewById(R.id.addusertv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add User");

        ebirth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Addres.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        ebirth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                    }
                }, 2000, f156mm, f155dd);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                datePickerDialog.show();
            }
        });
        getPref("level", getApplicationContext());
        aaa = new ArrayAdapter(this, R.layout.custom_spinner_item, split);
        if (aaa != null) {
            aaa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
            spinner.setAdapter(aaa);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                text = adapterView.getItemAtPosition(i).toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Addres addres = Addres.this;
                if (!addres.isOnline(addres)) {
                    Toast.makeText(Addres.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (nick.length() < 4) {
                    Toast.makeText(Addres.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
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
            dialog = new Dialog(Addres.this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Addres.getPref("token", getApplicationContext());
            String pref2 = Addres.getPref("device", getApplicationContext());
            String obj = nick.getText().toString();
            String obj2 = email.getText().toString();
            String obj3 = name.getText().toString();
            String obj4 = f154bl.getText().toString();
            String obj5 = pass.getText().toString();
            String obj6 = pin.getText().toString();
            String obj7 = opin.getText().toString();
            String obj8 = nid.getText().toString();
            String obj9 = ebirth.getText().toString();
            arrayList.add(new BasicNameValuePair("level", text));
            arrayList.add(new BasicNameValuePair("deviceid", pref2));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("username", obj));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj3));
            arrayList.add(new BasicNameValuePair("email", obj2));
            arrayList.add(new BasicNameValuePair("pincode", obj7));
            arrayList.add(new BasicNameValuePair("phone", obj4));
            arrayList.add(new BasicNameValuePair("client_types", "16840"));
            arrayList.add(new BasicNameValuePair("password", obj5));
            arrayList.add(new BasicNameValuePair("nid", obj8));
            arrayList.add(new BasicNameValuePair("birth", obj9));
            arrayList.add(new BasicNameValuePair("resellerpin", obj6));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((Addres.getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/") + "reselleradd", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String str = "success";
            try {
                int i = makeHttpRequest.getInt(str);
                int i2 = makeHttpRequest.getInt(str);
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    flag = 0;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showError(Addres.this, string);
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
                        Toast.makeText(Addres.this, string, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Myreseller.class));
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
            if (flag == 1) {
                Toast.makeText(Addres.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
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

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ((TextView) dialog.findViewById(R.id.cop)).setText(str4);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(0, 0);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
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

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
