package com.v5foradnroid.userapp.post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.v5foradnroid.userapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    String var_threes;
    private String BASE_URL;
    private static final String TAG = "CategoryActivity";
    private CardView editButton, addButton, deleteButton;
    private List<String> categories;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        listView = findViewById(R.id.listView_categories);
        addButton = findViewById(R.id.button_add);
        editButton = findViewById(R.id.button_edit);
        deleteButton = findViewById(R.id.button_delete);

        var_threes = getPrefthrees("threes", getApplicationContext());
        BASE_URL = var_threes + "/add_category.php";
        username = SessionHandler.getPref("phone", getApplicationContext());

        categories = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, categories);
        listView.setAdapter(adapter);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSelectedCategory();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedCategory();
            }
        });

        // Load categories from server
        loadCategories();
    }

    private void editSelectedCategory() {
        int selectedItemPosition = listView.getCheckedItemPosition();

        if (selectedItemPosition != ListView.INVALID_POSITION) {
            String category = categories.get(selectedItemPosition);
            showEditCategoryDialog(category);
        } else {
            Toast.makeText(this, "Please select a category to edit", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditCategoryDialog(String category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Category");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, (ViewGroup) getWindow().getDecorView(), false);
        final EditText input = viewInflated.findViewById(R.id.editText_category_name);
        input.setText(category); // Set the initial text as the current category name
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCategory = input.getText().toString();
                updateCategory(category, newCategory);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateCategory(String oldCategory, String newCategory) {
        new UpdateCategoryTask().execute(oldCategory, newCategory);
    }

    private class UpdateCategoryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String oldCategory = params[0];
            String newCategory = params[1];
            String response = "";

            try {
                URL url = new URL(BASE_URL + "?action=updateCategory");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);

                String requestBody = "oldCategory=" + URLEncoder.encode(oldCategory, "UTF-8") + "&newCategory=" + URLEncoder.encode(newCategory, "UTF-8") + "&username=" + URLEncoder.encode(username, "UTF-8");

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    response = stringBuilder.toString();
                } else {
                    response = "Error: " + conn.getResponseCode() + " " + conn.getResponseMessage();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (!response.startsWith("Error")) {
                Toast.makeText(CategoryActivity.this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                loadCategories();
            } else {
                Log.e(TAG, "Error updating category: " + response);
                Toast.makeText(CategoryActivity.this, "Error updating category", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadCategories() {
        new GetCategoriesTask().execute();
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, (ViewGroup) getWindow().getDecorView(), false);
        final EditText input = viewInflated.findViewById(R.id.editText_category_name);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = input.getText().toString();
                addCategory(category);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addCategory(String category) {
        new AddCategoryTask().execute(category);
    }

    private void deleteSelectedCategory() {
        int selectedItemPosition = listView.getCheckedItemPosition();

        if (selectedItemPosition != ListView.INVALID_POSITION) {
            String category = categories.get(selectedItemPosition);
            new DeleteCategoryTask().execute(category);
        } else {
            Toast.makeText(this, "Please select a category to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCategoriesList() {
        adapter.notifyDataSetChanged();
    }

    private class GetCategoriesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String response = "";

            try {
                URL url = new URL(BASE_URL + "?action=getCategories&username=" + username);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    response = stringBuilder.toString();
                } else {
                    response = "Error: " + conn.getResponseCode() + " " + conn.getResponseMessage();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (!response.startsWith("Error")) {
                try {
                    JSONArray categoriesArray = new JSONArray(response);
                    categories.clear();

                    for (int i = 0; i < categoriesArray.length(); i++) {
                        String category = categoriesArray.getString(i);
                        categories.add(category);
                    }

                    refreshCategoriesList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Error retrieving categories: " + response);
                Toast.makeText(CategoryActivity.this, "Error retrieving categories", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddCategoryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String category = params[0];
            String response = "";

            try {
                URL url = new URL(BASE_URL + "?action=addCategory");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String requestBody = "category=" + category + "&username=" + username;

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    response = stringBuilder.toString();
                } else {
                    response = "Error: " + conn.getResponseCode() + " " + conn.getResponseMessage();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (!response.startsWith("Error")) {
                if (response.equals("Category added successfully")) {
                    Toast.makeText(CategoryActivity.this, response, Toast.LENGTH_SHORT).show();
                    loadCategories();
                } else {
                    Log.e(TAG, "Error adding category: " + response);
                    Toast.makeText(CategoryActivity.this, "Error adding category", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Error adding category: " + response);
                Toast.makeText(CategoryActivity.this, "Error adding category", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class DeleteCategoryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String category = params[0];
            String response = "";

            try {
                URL url = new URL(BASE_URL + "?action=deleteCategory");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setDoOutput(true);

                String requestBody = "category=" + category + "&username=" + username;

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    response = stringBuilder.toString();
                } else {
                    response = "Error: " + conn.getResponseCode() + " " + conn.getResponseMessage();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (!response.startsWith("Error")) {
                Toast.makeText(CategoryActivity.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                loadCategories();
            } else {
                Log.e(TAG, "Error deleting category: " + response);
                Toast.makeText(CategoryActivity.this, "Error deleting category", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String getPrefthrees(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}
