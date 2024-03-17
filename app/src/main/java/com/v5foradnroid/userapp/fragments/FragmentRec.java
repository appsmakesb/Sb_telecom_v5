package com.v5foradnroid.userapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.CustomAdapter;
import com.v5foradnroid.userapp.ExpandableHeightGridView;
import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.service_adafter;
import com.google.android.gms.common.internal.ImagesContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentRec extends Fragment {

    ViewPager viewPager;
    public static String TAG = "Welcome";
    String FinalJSonObject;
    service_adafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    int currentPage = 0;
    String device;
    Dialog dialog;
    public ImageView[] dots;
    public int dotscount;
    String img1, img2, img3, img4;
    JSONArray jsonarray;
    String number;
    String pwd, shop, telegram, whatsapp, youtube;
    LinearLayout sliderDotspanel;
    Timer timer;
    String token;
    String url;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rec, container, false);

        item_list();

        return view;
    }

    private void item_list() {
        Dialog dialog2 = new Dialog(requireContext());
        dialog = dialog2;
        dialog2.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress);
        dialog.show();
        String str = getPref(ImagesContract.URL, requireContext()) + "/apiapp/";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + url;
        url = str2;
        Log.d("osman", str2);
        number = getPref("phone", requireContext());
        pwd = getPref("pass", requireContext());
        token = getPref("token", requireContext());
        device = getPref("device", requireContext());
        StringRequest r1 = new StringRequest(1, url + "/role?token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("osman", str);
                FinalJSonObject = str;
                new ItemParseJSonDataClass(getActivity()).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(getActivity().getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
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
        Volley.newRequestQueue(requireContext()).add(r1);
    }

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(FinalJSonObject);
                jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = jsonarray.getJSONObject(i);
                    if (i == 0) {
                        jSONObject2.getString("message");
                        final String string = jSONObject2.getString("level");
                        final String string2 = jSONObject2.getString("notice");
                        whatsapp = jSONObject2.getString("whatsapp");
                        telegram = jSONObject2.getString("telegram");
                        youtube = jSONObject2.getString("youtube");
                        shop = jSONObject2.getString("shop");
                        img1 = jSONObject2.getString("img1");
                        img2 = jSONObject2.getString("img2");
                        img3 = jSONObject2.getString("img3");
                        img4 = jSONObject2.getString("img4");
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                slide(img1, img2, img3, img4);
//                                setTextInTextViewl(string);
//                                SavePreferences("level", string);
                            }
                        });
                    }
                    String string3 = jSONObject2.getString("act");
                    if (!string3.toString().toLowerCase().equals("addres") && !string3.toString().toLowerCase().equals("transfer") && !string3.toString().toLowerCase().equals("make") && !string3.toString().toLowerCase().equals("myres")) {
                        hashMap.put("service", jSONObject2.getString("service"));
                        hashMap.put("name", jSONObject2.getString("name"));
                        hashMap.put("img", jSONObject2.getString("img"));
                        hashMap.put("act", jSONObject2.getString("act"));
                        arraylist.add(hashMap);
                    }
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            ExpandableHeightGridView expandableHeightGridView = (ExpandableHeightGridView) view.findViewById(R.id.atachviewx);
            expandableHeightGridView.setExpanded(true);
            adapter = new service_adafter(requireContext(), arraylist);
            expandableHeightGridView.setAdapter(adapter);
            dialog.dismiss();
        }
    }

    public void slide(String str, String str2, String str3, String str4) {
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), new Integer[]{1, 2, 3, 4}, new String[]{str, str2, str3, str4});
        viewPager.setAdapter(customAdapter);
        int count = customAdapter.getCount();
        dotscount = count;
        dots = new ImageView[count];
        for (int i = 0; i < this.dotscount; i++) {
            dots[i] = new ImageView(requireContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(this.dots[i], layoutParams);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {

            }

            public void onPageScrolled(int i, float f, int i2) {

            }

            public void onPageSelected(int i) {
                try {
                    for (int i2 = 0; i2 < dotscount; i2++) {
                        dots[i2].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                    }
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
                } catch (NullPointerException e) {

                }
            }
        });
        final Handler handler = new Handler();
        final Runnable r9 = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                int i = currentPage;
                currentPage = i + 1;
                viewPager.setCurrentItem(i, true);
            }
        };
        Timer timer2 = new Timer();
        timer = timer2;
        timer2.schedule(new TimerTask() {
            public void run() {
                handler.post(r9);
            }
        }, 1000, 3000);
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

}