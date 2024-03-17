package com.v5foradnroid.userapp.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.v5foradnroid.userapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserCategory extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    String  var_threes , category, allorSell, sellPost;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_category);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        allorSell = intent.getStringExtra("allorSell");
        sellPost = intent.getStringExtra("sellPost");

        var_threes = getPrefthrees("threes", getApplicationContext());
        String profileUsername = SessionHandler.getPref("phone", getApplicationContext());
        String url = var_threes + category + "?username=" + profileUsername;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getCategoryList(url);
    }

    private void getCategoryList(String url) {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                List<Category> categoryList = parseCategoryList(response);
                categoryAdapter = new CategoryAdapter(categoryList, UserCategory.this);
                recyclerView.setAdapter(categoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserCategory.this, "Failed to fetch categories: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private List<Category> parseCategoryList(JSONObject jsonObject) {
        List<Category> categoryList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("categories");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCategory = jsonArray.getJSONObject(i);
                String name = jsonCategory.getString("name");
                int iconResourceId = getCategoryIconResource(name); // Get the category icon resource
                Category category = new Category(i, name, iconResourceId);
                categoryList.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private int getCategoryIconResource(String categoryName) {
        HashMap<String, Integer> categoryIconMap = new HashMap<>();
        categoryIconMap.put("Education", R.drawable.education);
        categoryIconMap.put("Health Care", R.drawable.healthcare);
        categoryIconMap.put("Entertainment", R.drawable.entertainment);
        categoryIconMap.put("Technology", R.drawable.technology);
        categoryIconMap.put("Sports", R.drawable.sports);
        categoryIconMap.put("Business", R.drawable.business);
        categoryIconMap.put("Travel", R.drawable.travel);
        categoryIconMap.put("Fashion", R.drawable.fashion);
        categoryIconMap.put("Food and Dining", R.drawable.foodanddining);
        categoryIconMap.put("Science", R.drawable.science);
        categoryIconMap.put("Art and Culture", R.drawable.artandculture);
        categoryIconMap.put("Lifestyle", R.drawable.lifestyle);
        categoryIconMap.put("Fitness and Wellness", R.drawable.fitnessandwellness);
        categoryIconMap.put("News and Current Affairs", R.drawable.newsandcurrentaffairs);
        // Add more mappings for other category names and icon resources

        Integer iconResource = categoryIconMap.get(categoryName);
        if (iconResource != null) {
            return iconResource;
        } else {
            // Return a default icon resource if no matching category name is found
            return R.drawable.placeholder_image;
        }
    }

    @Override
    public void onItemClick(Category category) {
        Intent intent = new Intent(UserCategory.this, DisplayPostsActivity.class);
        intent.putExtra("category", category.getName());
        intent.putExtra("allorSell", allorSell);
        intent.putExtra("sellPost", sellPost);
        startActivity(intent);
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}
