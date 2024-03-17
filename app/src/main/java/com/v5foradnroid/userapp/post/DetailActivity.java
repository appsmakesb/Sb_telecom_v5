package com.v5foradnroid.userapp.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.v5foradnroid.userapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    String var_threes;
    private TextView titleTextView, detailsTextView;
    private ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        titleTextView = findViewById(R.id.titleTextView);
        detailsTextView = findViewById(R.id.detailsTextView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        var_threes = getPrefthrees("threes", getApplicationContext());

        // Retrieve the data passed from the previous activity
        Intent intent = getIntent();
        String ids = intent.getStringExtra("post_id");
        String title = intent.getStringExtra("title");
        String details = intent.getStringExtra("details");
        String image1 = intent.getStringExtra("image1");
        String image2 = intent.getStringExtra("image2");
        String image3 = intent.getStringExtra("image3");

        // Set the data in the corresponding views
        titleTextView.setText(title);
        detailsTextView.setText(details);

        // Load and display images using a library like Picasso or Glide
        if (image1.equals(var_threes + "/uploads/" + "null")) {
            imageView1.setVisibility(View.GONE);
        } else {
            imageView1.setVisibility(View.VISIBLE);
            Picasso.get().load(image1).placeholder((int) R.drawable.progressbar_ic).error((int) R.drawable.loading_error).into(imageView1);
        }
        if (image2.equals(var_threes + "/uploads/" + "null")) {
            imageView2.setVisibility(View.GONE);
        } else {
            imageView2.setVisibility(View.VISIBLE);
            Picasso.get().load(image2).placeholder((int) R.drawable.progressbar_ic).error((int) R.drawable.loading_error).into(imageView2);
        }
        if (image3.equals(var_threes + "/uploads/" + "null")) {
            imageView3.setVisibility(View.GONE);
        } else {
            imageView3.setVisibility(View.VISIBLE);
            Picasso.get().load(image3).placeholder((int) R.drawable.progressbar_ic).error((int) R.drawable.loading_error).into(imageView3);

        }
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}
