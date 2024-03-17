package com.v5foradnroid.userapp.post;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.v5foradnroid.userapp.R;

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

public class UploadPost extends AppCompatActivity {
    String var_threes, username;
    ProgressDialog uProgressDialog;
    private static final int REQUEST_IMAGE1 = 1;
    private static final int REQUEST_IMAGE2 = 2;
    private static final int REQUEST_IMAGE3 = 3;
    private ImageView ivImage1, ivImage2, ivImage3;
    private Button btnUpload;
    private Bitmap selectedImage1, selectedImage2, selectedImage3;
    private EditText etTitle, etDetails, etCode, etPrice;
    String StockOrSell = "Stock";
    private Spinner spinnerCategory;
    private ArrayList<String> categoriesList;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        ivImage1 = findViewById(R.id.ivImage1);
        ivImage2 = findViewById(R.id.ivImage2);
        ivImage3 = findViewById(R.id.ivImage3);
        btnUpload = findViewById(R.id.btnUpload);
        etTitle = findViewById(R.id.title);
        etCode = findViewById(R.id.product_code);
        etPrice = findViewById(R.id.product_price);
        etDetails = findViewById(R.id.details);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        var_threes = getPrefthrees("threes", getApplicationContext());
        username = SessionHandler.getPref("phone", getApplicationContext());

        uProgressDialog = new ProgressDialog(UploadPost.this);

        ivImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE1);
            }
        });

        ivImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE2);
            }
        });

        ivImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(REQUEST_IMAGE3);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerCategory.getSelectedItem() != null) {
                    uploadImages();
                } else {
                    Toast.makeText(UploadPost.this, "At First Create Category", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadPost.this, CategoryActivity.class));
                    finish();
                }
            }
        });

        // Retrieve the categories from the server
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
                            ivImage1.setImageBitmap(compressedImage);
                            selectedImage1 = compressedImage;
                            break;
                        case REQUEST_IMAGE2:
                            ivImage2.setImageBitmap(compressedImage);
                            selectedImage2 = compressedImage;
                            break;
                        case REQUEST_IMAGE3:
                            ivImage3.setImageBitmap(compressedImage);
                            selectedImage3 = compressedImage;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImages() {
        String title = etTitle.getText().toString();
        String details = etDetails.getText().toString();
        String code = etCode.getText().toString();
        String price = etPrice.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        /*if (selectedImage1 == null && selectedImage2 == null && selectedImage3 == null) {
            Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (title.isEmpty()) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return;
        }

        if (code.isEmpty()) {
            etCode.setError("Code is required");
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
        } else {
            uProgressDialog.setMessage("Uploading has started...");
            uProgressDialog.show();
        }

        new UploadTask().execute(title, details, category, username, code, price, StockOrSell);
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

            OkHttpClient client = new OkHttpClient();

            try {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("title", title).addFormDataPart("details", details).addFormDataPart("category", category).addFormDataPart("username", username).addFormDataPart("code", code).addFormDataPart("price", price).addFormDataPart("StockOrSell", StockOrSell);

                // Add the selected images
                if (selectedImage1 != null) {
                    byte[] imageBytes1 = bitmapToByteArray(selectedImage1);
                    builder.addFormDataPart("image1", "image1.jpg", RequestBody.create(MediaType.parse("image/jpeg"), imageBytes1));
                }
                if (selectedImage2 != null) {
                    byte[] imageBytes2 = bitmapToByteArray(selectedImage2);
                    builder.addFormDataPart("image2", "image2.jpg", RequestBody.create(MediaType.parse("image/jpeg"), imageBytes2));
                }
                if (selectedImage3 != null) {
                    byte[] imageBytes3 = bitmapToByteArray(selectedImage3);
                    builder.addFormDataPart("image3", "image3.jpg", RequestBody.create(MediaType.parse("image/jpeg"), imageBytes3));
                }

                RequestBody requestBody = builder.build();
                Request request = new Request.Builder().url(var_threes + "/upload_data.php").post(requestBody).build();

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
            Toast.makeText(UploadPost.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etDetails.setText("");
            etCode.setText("");
            etPrice.setText("");
            Glide.with(UploadPost.this).load(R.drawable.placeholder_image).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(new ImageViewTarget<Drawable>(ivImage1) {
                @Override
                protected void setResource(@Nullable Drawable resource) {
                    ivImage1.setImageDrawable(resource);
                }
            });
            Glide.with(UploadPost.this).load(R.drawable.placeholder_image).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(new ImageViewTarget<Drawable>(ivImage2) {
                @Override
                protected void setResource(@Nullable Drawable resource) {
                    ivImage2.setImageDrawable(resource);
                }
            });

            Glide.with(UploadPost.this).load(R.drawable.placeholder_image).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(new ImageViewTarget<Drawable>(ivImage3) {
                @Override
                protected void setResource(@Nullable Drawable resource) {
                    ivImage3.setImageDrawable(resource);
                }
            });

        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
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
    /*private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }*/

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
