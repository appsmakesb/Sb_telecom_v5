package com.v5foradnroid.userapp.post;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayPostsActivity extends AppCompatActivity {

    String var_threes;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<Post> filteredList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_posts);

        String category = getIntent().getStringExtra("category");
        String allorSell = getIntent().getStringExtra("allorSell");
        String sellPost = getIntent().getStringExtra("sellPost");

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        searchEditText = findViewById(R.id.searchEditText);

        var_threes = getPrefthrees("threes", getApplicationContext());

        postList = new ArrayList<>();
        filteredList = new ArrayList<>();
        postAdapter = new PostAdapter(DisplayPostsActivity.this, postList, filteredList, sellPost);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                filterPosts(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        String username = SessionHandler.getPref("phone", getApplicationContext());

        getPostsForCategory(var_threes, allorSell, category, username);
    }

    private void getPostsForCategory(String API_BASE_URL, String allorSell, String category, String username) {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = API_BASE_URL + allorSell + "?category=" + category + "&username=" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonPosts = response.getJSONArray("posts");
                    for (int i = 0; i < jsonPosts.length(); i++) {
                        JSONObject jsonPost = jsonPosts.getJSONObject(i);
                        int id = jsonPost.getInt("id");
                        String title = jsonPost.getString("title");
                        String details = jsonPost.getString("details");
                        String code = jsonPost.getString("code");
                        String price = jsonPost.getString("price");
                        String StockOrSell = jsonPost.getString("stock_or_sell");
                        String image1 = jsonPost.getString("image1");
                        String image2 = jsonPost.getString("image2");
                        String image3 = jsonPost.getString("image3");
                        String codeCount = jsonPost.getString("code_count");
                        String category = jsonPost.getString("category");
                        Post post = new Post(id, title, details, code, price, StockOrSell, image1, image2, image3, codeCount, category);
                        postList.add(post);
                        filteredList.add(post); // Add the post to filteredList initially
                    }
                    postAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                progressBar.setVisibility(View.GONE);
            }
        });
        queue.add(request);
    }


    private void filterPosts(String searchText) {
        filteredList.clear();

        for (Post post : postList) {
            if (post.getTitle().toLowerCase().contains(searchText)) {
                filteredList.add(post);
            }
        }

        postAdapter.notifyDataSetChanged();
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}