package com.v5foradnroid.userapp;

import static com.v5foradnroid.userapp.view.MainActivityc.tampBal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.ImagesContract;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.antlr.runtime.debug.DebugEventListener;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Frag1 extends Fragment {


    public EditText f180am;
    ArrayList<HashMap<String, String>> arraylist;
    int f181bi = 0;
    int flag = 0;
    String f182h, f183id;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    ImageButton login;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            charSequence.length();
        }
    };
    public dialog f184md;
    private dialog mdd;
    /* Foysal Tech && Ict Foysal */
    public dialogs mds;
    /* Foysal Tech && Ict Foysal */
    public TextView f185mn;
    String nam;
    /* Foysal Tech && Ict Foysal */
    public String number;
    ImageView opl;
    String opn;
    String optr;
    /* Foysal Tech && Ict Foysal */
    public ProgressDialog pDialog;
    String paid;

    ProgressBar f186pg;
    /* Foysal Tech && Ict Foysal */
    public EditText pin;
    /* Foysal Tech && Ict Foysal */
    public RadioGroup radioGroup;
    String type, type2, type3;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frag1_layout, viewGroup, false);

        login = (ImageButton) inflate.findViewById(R.id.next);
        TextView textView = (TextView) inflate.findViewById(R.id.ten);
        TextView textView2 = (TextView) inflate.findViewById(R.id.twenty);
        TextView textView3 = (TextView) inflate.findViewById(R.id.fifty);
        TextView textView4 = (TextView) inflate.findViewById(R.id.hundread);

        f180am = (EditText) inflate.findViewById(R.id.amount);
        f186pg = (ProgressBar) ((DisplayActivity) getContext()).findViewById(R.id.wait);
//        f187pp = (TextView) inflate.findViewById(R.id.pp);
        EditText editText = (EditText) inflate.findViewById(R.id.amount);
        f180am = editText;
        editText.requestFocus();

        String amNewByFt = getPrefAm("amNewByFt", getActivity().getBaseContext());
        f180am.setText(amNewByFt);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                f180am.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                f180am.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
            }
        }, 200);
        f185mn = (TextView) ((DisplayActivity) getContext()).findViewById(R.id.number);
        intent = getActivity().getIntent();
        opl = (ImageView) ((DisplayActivity) getContext()).findViewById(R.id.opera);
        type = intent.getExtras().getString("type");
        type2 = intent.getExtras().getString("type2");
        type3 = intent.getExtras().getString("type3");
        opn = intent.getExtras().getString("opt");
        optr = intent.getExtras().getString("opt2");
        number = intent.getExtras().getString("number");
        radioGroup = (RadioGroup) ((DisplayActivity) getContext()).findViewById(R.id.typep);
        intent.hasExtra("img");
        if (intent.getExtras().getString("type2").indexOf("internet") >= 0) {
            new ViewDialog().showDialog(getActivity(), intent.getExtras().getString("number"), intent.getExtras().getString("amount"), type2, optr);
        }
        intent.hasExtra("img");


        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                f180am.setText("150");
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                f180am.setText("20");
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                f180am.setText("50");
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                f180am.setText("100");
            }
        });
        opl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), Operator.class);
                intent.putExtra("type", type);
                intent.putExtra("type3", type3);
                intent.putExtra("type2", type2);
                intent.putExtra("number", number);
                startActivity(intent);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.skitto) {
                    optr = "Skitto";
                    opn = "SK";
                    f181bi = 1;
                    new Getop().execute(new Void[0]);
                }
            }
        });
        if (opn.equals("GP")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.grameenphone));
        } else if (opn.equals("RB")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.robi));
        } else if (opn.equals("BL")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.banglalink));
        } else if (opn.equals("AT")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.airtel));
        } else if (opn.equals("SK")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.skitto));
        } else if (opn.equals("TT")) {
            opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.teletalk));
        }
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str;
                if (!isOnline(getActivity().getBaseContext())) {
                    Toast.makeText(getActivity().getBaseContext(), "No network connection", Toast.LENGTH_LONG).show();
                } else if (f185mn.length() < 10) {
                    Toast.makeText(getActivity().getBaseContext(), "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.postpaid) {
                        str = "Postpaid";
                    } else {
                        str = "Prepaid";
                    }
                    paid = str;
                    if (checkedRadioButtonId == R.id.skitto) {
                        optr = "Skitto";
                        opn = "SK";
                    }
                    Intent intent = new Intent(getActivity().getBaseContext(), PinActivity.class);
                    intent.putExtra("paid", paid);
                    intent.putExtra("opt", opn);
                    intent.putExtra("opt2", optr);
                    intent.putExtra("type", type);
                    intent.putExtra("amount", f180am.getText().toString());
                    intent.putExtra("number", number);
                    intent.putExtra("type3", type3);
                    intent.putExtra("type2", type2);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                }
            }
        });

        if (tampBal != null && !tampBal.isEmpty()) {
            f180am.setEnabled(false);
            login.callOnClick();
        }

        return inflate;
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity().getBaseContext());
            pDialog.setMessage("please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = getPref("phone", getActivity().getBaseContext());
            String pref2 = getPref("pass", getActivity().getBaseContext());
            String pref3 = getPref("pin", getActivity().getBaseContext());
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            f182h = f180am.getText().toString();
            f183id = f185mn.getText().toString();
            String obj = pin.getText().toString();
            String pref4 = getPref("token", getActivity().getBaseContext());
            String pref5 = getPref("device", getActivity().getBaseContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("service", type3));
            arrayList.add(new BasicNameValuePair("item", type));
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
                arrayList.add(new BasicNameValuePair("number", f183id));
                arrayList.add(new BasicNameValuePair("amount", f182h));
            }
            arrayList.add(new BasicNameValuePair("optn", opn));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = jsonParser.makeHttpRequest((getPref(ImagesContract.URL, getActivity().getBaseContext()) + "/apiapp/") + "" + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    flag = 0;
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            dialog unused = f184md = new dialog(getActivity());
                            f184md.build("Faild", string);
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
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mds = new dialogs(getActivity());
                        Toast.makeText(getActivity().getBaseContext(), "Request Sent Successfull", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity().getBaseContext(), Welcome.class));
                        getActivity().finish();
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
            pDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(getActivity().getBaseContext(), "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Getop extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private Getop() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            f186pg.setVisibility(View.VISIBLE);
            if (!isOnline(getActivity().getBaseContext())) {
                f186pg.setVisibility(View.GONE);
            }
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            String str;
            if (!isOnline(getActivity().getBaseContext())) {
                return null;
            }
            getPref("token", getActivity().getBaseContext());
            getPref("device", getActivity().getBaseContext());
            String str2 = getPref("url", getActivity().getBaseContext()) + "/apiapp/";
            arraylist = new ArrayList<>();
            if (f181bi == 1) {
                str = "113";
            } else {
                str = f185mn.getText().toString();
                if (str.length() > 3) {
                    str = str.substring(0, 3);
                }
            }
            jsonobjects = JSONfunctions.getJSONfromURL(str2 + "/oparetorList?three=" + str);
            try {
                jsonarray = jsonobjects.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    new HashMap();
                    jsonobject = jsonarray.getJSONObject(i);
                    if (i == 0) {
                        optr = jsonobject.getString("opname");
                        opn = jsonobject.getString("pcode");
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    if (opn.equals("GP")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.grameenphone));
                                    } else if (opn.equals("RB")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.robi));
                                    } else if (opn.equals("BL")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.banglalink));
                                    } else if (opn.equals("AT")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.airtel));
                                    } else if (opn.equals("SK")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.skitto));
                                    } else if (opn.equals("TT")) {
                                        opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.teletalk));
                                    } else {
                                        Picasso.get().load(jsonobject.getString("img")).into(opl);
                                    }
                                } catch (JSONException unused) {
                                }
                            }
                        });
                    }
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(Void voidR) {
            f186pg.setVisibility(View.GONE);
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
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.getWindow().setLayout(-1, -1);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ((TextView) dialog.findViewById(R.id.cop)).setText(str4);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    new loginAccess().execute(new String[0]);
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

    public static String getPrefAm(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

}
