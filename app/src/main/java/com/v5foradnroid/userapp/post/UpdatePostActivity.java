package com.v5foradnroid.userapp.post;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.v5foradnroid.userapp.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdatePostActivity extends AppCompatActivity {

    String var_threes, username;
    ProgressDialog uProgressDialog;
    private static final int REQUEST_IMAGE1 = 1;
    private static final int REQUEST_IMAGE2 = 2;
    private static final int REQUEST_IMAGE3 = 3;
    private EditText editTextTitle, editTextDetails, etCode, etPrice;
    private ImageView imageView1, imageView2, imageView3;
    private Button sellBtn, buttonUpdatePost, deleteBtn;
    private Bitmap selectedImage1, selectedImage2, selectedImage3;
    TextView tvStatus, totalCount, categorytv;
    private Spinner spinnerCategory;
    private ArrayList<String> categoriesList;
    private ArrayAdapter<String> categoryAdapter;
    private int postId; // Holds the post ID
    String sell = "Sell";
    String title, details, Code, Price, StockOrSell, totalcount, category, image1, image2, image3 , postOrSell , allorSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_post);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDetails = findViewById(R.id.editTextDetails);
        etCode = findViewById(R.id.product_code);
        etPrice = findViewById(R.id.product_price);
        tvStatus = findViewById(R.id.status);
        totalCount = findViewById(R.id.totalCountStatus);
        categorytv = findViewById(R.id.categoryStatus);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        sellBtn = findViewById(R.id.sellPost);
        deleteBtn = findViewById(R.id.ChoosedeletePost);
        buttonUpdatePost = findViewById(R.id.buttonUpdatePost);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        var_threes = getPrefthrees("threes", getApplicationContext());
        username = SessionHandler.getPref("phone", getApplicationContext());

        // Get the post ID from the Intent
        Intent intent = getIntent();
        postId = intent.getIntExtra("post_id", -1);
        title = intent.getStringExtra("title");
        details = intent.getStringExtra("details");
        Code = intent.getStringExtra("Code");
        Price = intent.getStringExtra("Price");
        StockOrSell = intent.getStringExtra("StockOrSell");
        totalcount = intent.getStringExtra("totalcount");
        category = intent.getStringExtra("category");
        image1 = intent.getStringExtra("image1");
        image2 = intent.getStringExtra("image2");
        image3 = intent.getStringExtra("image3");
        postOrSell = intent.getStringExtra("postOrSell");
        allorSell = intent.getStringExtra("allorSell");

        editTextTitle.setText(title);
        editTextDetails.setText(details);
        etCode.setText(Code);
        etPrice.setText(Price);
        tvStatus.setText(StockOrSell);
        totalCount.setText(totalcount);
        categorytv.setText("Category: "+category);

        if (postOrSell.equals("/sell_delete.php")) {
            sellBtn.setVisibility(View.GONE);
            buttonUpdatePost.setVisibility(View.GONE);
        }

        uProgressDialog = new ProgressDialog(UpdatePostActivity.this);

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

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE2);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE3);
            }
        });

        buttonUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uProgressDialog.setMessage("Updateing has started...");
                uProgressDialog.show();
                updatePost();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uProgressDialog.setMessage("deleteing has started...");
                uProgressDialog.show();
                deletePost(postOrSell);
            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadTask().execute(title, details, category, username, Code, Price, sell, image1.replace(var_threes + "/uploads/",""), image2.replace(var_threes + "/uploads/",""), image3.replace(var_threes + "/uploads/",""));
            }
        });

        fetchCategories();

    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    Bitmap compressedImage = compressImage(selectedImage);

                    switch (requestCode) {
                        case REQUEST_IMAGE1:
                            imageView1.setImageBitmap(compressedImage);
                            selectedImage1 = compressedImage;
                            break;
                        case REQUEST_IMAGE2:
                            imageView2.setImageBitmap(compressedImage);
                            selectedImage2 = compressedImage;
                            break;
                        case REQUEST_IMAGE3:
                            imageView3.setImageBitmap(compressedImage);
                            selectedImage3 = compressedImage;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updatePost() {
        String title = editTextTitle.getText().toString().trim();
        String details = editTextDetails.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // Validate inputs
        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        if (code.isEmpty()) {
            etCode.setError("code is required");
            etCode.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("price is required");
            etPrice.requestFocus();
            return;
        }

        if (selectedImage1 == null) {
            Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show();
            uProgressDialog.dismiss();
            return;
        }

        // Convert images to byte arrays
        byte[] image1Bytes = null;
        byte[] image2Bytes = null;
        byte[] image3Bytes = null;

        if (selectedImage1 != null) {
            image1Bytes = convertImageToByteArray(selectedImage1);
        }

        if (selectedImage2 != null) {
            image2Bytes = convertImageToByteArray(selectedImage2);
        }

        if (selectedImage3 != null) {
            image3Bytes = convertImageToByteArray(selectedImage3);
        }

        // Execute the AsyncTask to update the post
        UpdatePostAsyncTask task = new UpdatePostAsyncTask();
        task.execute(title, details, category, code, price, StockOrSell, image1Bytes, image2Bytes, image3Bytes);
    }

    private byte[] convertImageToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private class UpdatePostAsyncTask extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {
            String title = (String) params[0];
            String details = (String) params[1];
            String category = (String) params[2];
            String Code = (String) params[3];
            String Price = (String) params[4];
            String StockOrSell = (String) params[5];
            byte[] image1Bytes = (byte[]) params[6];
            byte[] image2Bytes = (byte[]) params[7];
            byte[] image3Bytes = (byte[]) params[8];

            // Prepare the request body builder
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("post_id", String.valueOf(postId))
                    .addFormDataPart("title", title)
                    .addFormDataPart("details", details)
                    .addFormDataPart("category", category)
                    .addFormDataPart("code", Code)
                    .addFormDataPart("price", Price)
                    .addFormDataPart("StockOrSell", StockOrSell);

            // Add image parts if available
            if (image1Bytes != null) {
                builder.addFormDataPart("image1", "image1.jpg", RequestBody.create(MediaType.parse("image/*"), image1Bytes));
            }
            if (image2Bytes != null) {
                builder.addFormDataPart("image2", "image2.jpg", RequestBody.create(MediaType.parse("image/*"), image2Bytes));
            }
            if (image3Bytes != null) {
                builder.addFormDataPart("image3", "image3.jpg", RequestBody.create(MediaType.parse("image/*"), image3Bytes));
            }

            // Build the request body
            RequestBody requestBody = builder.build();

            // Create the request
            Request request = new Request.Builder().url(var_threes + "/update_post.php").post(requestBody).build();

            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                Toast.makeText(UpdatePostActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
                uProgressDialog.dismiss();
            } else {
                Toast.makeText(UpdatePostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                uProgressDialog.dismiss();
            }
        }
    }

    private void deletePost(String postOrSell) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("post_id", String.valueOf(postId)).build();

        Request request = new Request.Builder().url(var_threes + postOrSell).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("UpdatePostActivity", "Failed to delete post: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uProgressDialog.dismiss();
                        Toast.makeText(UpdatePostActivity.this, "Failed to delete post", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("UpdatePostActivity", "Response: " + responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uProgressDialog.dismiss();
                        Toast.makeText(UpdatePostActivity.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdatePostActivity.this, GetPosts.class);
                        intent.putExtra("allorSell",allorSell);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private void fetchCategories() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(var_threes + "/upload_data.php").newBuilder();
        urlBuilder.addQueryParameter("username", username);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String categoriesResponse = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processCategoriesResponse(categoriesResponse);
                        }
                    });
                } else {
                    Log.e("FetchCategories", "Error: " + response.code());
                }
            }
        });
    }

    private void processCategoriesResponse(String categoriesResponse) {
        try {
            JSONArray jsonArray = new JSONArray(categoriesResponse);
            categoriesList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String category = jsonArray.getString(i);
                categoriesList.add(category);
            }

            // Set up the category spinner
            categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryAdapter);
            uProgressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SellAndDelete() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("post_id", String.valueOf(postId)).build();
        Request request = new Request.Builder().url(var_threes + "/undelete_img.php").post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("UpdatePostActivity", "Failed to delete post: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uProgressDialog.dismiss();
                        Toast.makeText(UpdatePostActivity.this, "Failed to delete post", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("UpdatePostActivity", "Response: " + responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uProgressDialog.dismiss();
                        Toast.makeText(UpdatePostActivity.this, "Added to Sale category", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdatePostActivity.this, GetPosts.class);
                        intent.putExtra("allorSell",allorSell);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private class UploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String title = params[0];
            String details = params[1];
            String category = params[2];
            String username = params[3];
            String code = params[4];
            String price = params[5];
            String StockOrSell = params[6];
            String image1 = params[7];
            String image2 = params[8];
            String image3 = params[9];

            OkHttpClient client = new OkHttpClient();

            try {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", title)
                        .addFormDataPart("details", details)
                        .addFormDataPart("category", category)
                        .addFormDataPart("username", username)
                        .addFormDataPart("code", code)
                        .addFormDataPart("price", price)
                        .addFormDataPart("StockOrSell", StockOrSell)
                        .addFormDataPart("image1", image1)
                        .addFormDataPart("image2", image2)
                        .addFormDataPart("image3", image3);

                RequestBody requestBody = builder.build();
                Request request = new Request.Builder().url(var_threes + "/sell_upload.php").post(requestBody).build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the response from the backend
            Log.d("UploadTask", "Response: " + result);
            uProgressDialog.dismiss();
//            Toast.makeText(UploadPost.this, result, Toast.LENGTH_SHORT).show();
            SellAndDelete();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdatePostActivity.this, GetPosts.class);
        intent.putExtra("allorSell",allorSell);
        startActivity(intent);
    }

    private Bitmap compressImage(Bitmap image) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int targetWidth = originalWidth / 5;
        int targetHeight = originalHeight / 5;
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, targetWidth, targetHeight, false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resizedImage.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}