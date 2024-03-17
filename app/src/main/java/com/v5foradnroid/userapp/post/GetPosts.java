package com.v5foradnroid.userapp.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPosts extends AppCompatActivity {

    String var_threes;
    private List<Post> postList;
    private List<Post> filteredList;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private ProgressBar progressBar;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_posts);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        searchEditText = findViewById(R.id.searchEditText);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String allorSell = intent.getStringExtra("allorSell");

        var_threes = getPrefthrees("threes", getApplicationContext());

        postList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new PostAdapter(GetPosts.this, postList, filteredList, allorSell);
        recyclerView.setAdapter(adapter);

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

        String profileUsername = SessionHandler.getPref("phone", getApplicationContext());
        fetchPosts(var_threes, allorSell, profileUsername);
    }

    private void fetchPosts(String API_BASE_URL, String allorSell, String username) {
        progressBar.setVisibility(View.VISIBLE);
        String url = API_BASE_URL + allorSell + "?username=" + username;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray postsArray = response.getJSONArray("posts");
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postObject = postsArray.getJSONObject(i);
                    Post post = new Post();
                    post.setId(postObject.getInt("id"));
                    post.setTitle(postObject.getString("title"));
                    post.setDetails(postObject.getString("details"));
                    post.setCode(postObject.getString("code"));
                    post.setPrice(postObject.getString("price"));
                    post.setStockOrSell(postObject.getString("stock_or_sell"));
                    post.setImage1(postObject.getString("image1"));
                    post.setImage2(postObject.getString("image2"));
                    post.setImage3(postObject.getString("image3"));
                    post.setTotalcount(postObject.getString("code_count"));
                    post.setCategory(postObject.getString("category"));
                    postList.add(post);
                    filteredList.add(post); // Add the post to filteredList initially
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(GetPosts.this, "Error retrieving data", Toast.LENGTH_SHORT).show());

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void filterPosts(String searchText) {
        filteredList.clear();

        for (Post post : postList) {
            if (post.getTitle().toLowerCase().contains(searchText)) {
                filteredList.add(post);
            }
        }

        adapter.notifyDataSetChanged();
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}