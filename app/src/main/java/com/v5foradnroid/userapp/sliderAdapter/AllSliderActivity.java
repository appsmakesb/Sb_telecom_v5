package com.v5foradnroid.userapp.sliderAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.v5foradnroid.userapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AllSliderActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    private static String Json_Url;
    RecyclerView recyclerView;
    ArrayList<SliderData> list2;
    SliderAdapterAll myadapter;
    EditText searchEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_slider);

        Json_Url = getPref23("fourth", getApplicationContext());
        recyclerView = findViewById(R.id.SliderRecyclar);
        searchEt = findViewById(R.id.search_Et);
        pDialog = new ProgressDialog(AllSliderActivity.this);
        pDialog.setMessage("please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        GetData getData = new GetData();
        getData.execute();
        list2 = new ArrayList<SliderData>();


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    myadapter.getFilter().filter(charSequence);
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static String getPref23(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

    public class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(Json_Url);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            list2.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("SecondSlider");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SliderData sliderData = new SliderData();
                    sliderData.setOnly(jsonObject1.getString("Only"));
                    sliderData.setSliderText(jsonObject1.getString("sliderText"));
                    sliderData.setSliderUrl(jsonObject1.getString("sliderUrl"));
                    sliderData.setSliderimg(jsonObject1.getString("sliderimg"));
                    list2.add(sliderData);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            myadapter = new SliderAdapterAll(AllSliderActivity.this, list2);
            StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggered);
            recyclerView.setAdapter(myadapter);
            myadapter.notifyDataSetChanged();
            myadapter.deleteItem(4);
            pDialog.dismiss();
        }
    }


}