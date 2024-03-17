package com.v5foradnroid.userapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Frag2 extends Fragment {
    String FinalJSonObject;
    Internet_after adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Intent intent;
    JSONArray jsonarray;
    JSONObject jsonobject;
    ListView listview;
    String number;
    String opn;
    String paid;
    String pwd;
    private RadioGroup radioGroup;
    String rol;
    String token;
    String type;
    String url;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frag2_layout, viewGroup, false);
        Intent intent2 = getActivity().getIntent();
        this.intent = intent2;
        this.number = intent2.getExtras().getString("number");
        this.opn = this.intent.getExtras().getString("opt");
        RadioGroup radioGroup2 = (RadioGroup) ((DisplayActivity) getContext()).findViewById(R.id.typep);
        this.radioGroup = radioGroup2;
        int checkedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
        this.paid = checkedRadioButtonId == R.id.postpaid ? "Postpaid" : "Prepaid";
        if (checkedRadioButtonId == R.id.skitto) {
            this.opn = "SK";
        }
        item_list(inflate);
        return inflate;
    }

    private void item_list(final View view) {
        String str = getPref(ImagesContract.URL, getActivity().getBaseContext()) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("osman", str2);
        this.pwd = getPref("pass", getActivity().getBaseContext());
        this.token = getPref("token", getActivity().getBaseContext());
        this.device = getPref("device", getActivity().getBaseContext());
        this.rol = "getdrive";
        StringRequest r1 = new StringRequest(1, this.url + "/" + this.rol + "?ot=" + this.opn + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ItemParseJSonDataClass(getActivity().getBaseContext(), view).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity().getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* Foysal Tech && ict Foysal */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
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
        Volley.newRequestQueue(getActivity().getBaseContext()).add(r1);
    }

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        public View view;

        public ItemParseJSonDataClass(Context context2, View view2) {
            this.view = view2;
            this.context = context2;
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* Foysal Tech && ict Foysal */
        public Void doInBackground(Void... voidArr) {
            arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(FinalJSonObject);
                jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = jsonarray.getJSONObject(i);
                    hashMap.put("id", jSONObject2.getString("id"));
                    hashMap.put("price", jSONObject2.getString("price"));
                    hashMap.put("title", jSONObject2.getString("title"));
                    hashMap.put("opname", jSONObject2.getString("opname"));
                    hashMap.put("com", jSONObject2.getString("com"));
                    hashMap.put("opname", opn);
                    hashMap.put("number", number);
                    hashMap.put("drive", "drive");
                    hashMap.put("paid", paid);
                    hashMap.put("role", rol);
                    hashMap.put("reg", jSONObject2.getString("reg"));
                    if (rol.equals("getdrive")) {
                        hashMap.put(NotificationCompat.CATEGORY_SERVICE, "64");
                    } else {
                        hashMap.put(NotificationCompat.CATEGORY_SERVICE, "16384");
                    }
                    arraylist.add(hashMap);
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
            listview = (ListView) this.view.findViewById(R.id.atachview);
            adapter = new Internet_after(getActivity().getBaseContext(), arraylist);
            listview.setAdapter(adapter);
        }
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
