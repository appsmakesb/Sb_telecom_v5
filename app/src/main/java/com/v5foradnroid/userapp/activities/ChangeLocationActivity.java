package com.v5foradnroid.userapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.Welcome;
import com.v5foradnroid.userapp.models.DistrictClass;
import com.v5foradnroid.userapp.models.DivisionClass;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChangeLocationActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Spinner divisionSpinner;
    private Spinner districtSpinner;
    private LinearLayout buttonChangeLocation;
    private List<DivisionClass> divisions;
    private List<DistrictClass> districts;
    private ArrayAdapter<String> districtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_change_location);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("জেলা পরিবর্তন");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        divisionSpinner = findViewById(R.id.divisionSpinner);
        districtSpinner = findViewById(R.id.districtSpinner);
        buttonChangeLocation = findViewById(R.id.buttonChangeLocation);

        // Load division data from "division.json"
        String divisionJson = loadJSONFromAsset("division.json");
        if (divisionJson != null) {
            divisions = DivisionClass.parseDivisionJson(divisionJson);
            if (divisions.isEmpty()) {
                Toast.makeText(this, "No divisions found in the JSON data", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "Error loading division data from JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set up division spinner
        ArrayAdapter<DivisionClass> divisionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, divisions);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(divisionAdapter);

        // Handle division selection
        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDistrictsForDivision(divisions.get(position).getId());
                SharedPreferences sharedPreferences = getSharedPreferences("NamazTimerPreferences", MODE_PRIVATE);
                String selectedDistrict = sharedPreferences.getString("selectedDistrict", null);
                int selectedIndex = getIndexFromText(districtSpinner, selectedDistrict);
                if (selectedIndex != -1) {
                    districtSpinner.setSelection(selectedIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                startActivity(new Intent(ChangeLocationActivity.this, Welcome.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }

        // Load Saved Preferences
        loadPreferences();
    }

    @Override
    protected void onResume() {
        // Load Saved Preferences
        loadPreferences();
        super.onResume();
    }

    // Load districts for the selected division
    private void loadDistrictsForDivision(String divisionId) {
        String districtJson = loadJSONFromAsset("district.json");
        if (districtJson != null) {
            districts = DistrictClass.parseDistrictJson(districtJson, divisionId);
            if (districts.isEmpty()) {
                Toast.makeText(this, "No districts found for the selected division", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayAdapter<DistrictClass> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            districtSpinner.setAdapter(districtAdapter);
        } else {
            Toast.makeText(this, "Error loading district data from JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Load JSON from assets folder
    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("ChangeLocationActivity", "Error loading JSON from asset: " + fileName);
            return null;
        }
        return json;
    }

    private void savePreferences() {
        String selectedDivision = divisionSpinner.getSelectedItem().toString();
        String selectedDistrict = districtSpinner.getSelectedItem().toString();
        int districtPosition = getIndexFromText(districtSpinner, selectedDistrict);
        String selectedLatitude = districts.get(districtPosition).getLatitude();
        String selectedLongitude = districts.get(districtPosition).getLongitude();
        SharedPreferences sharedPreferences = getSharedPreferences("NamazTimerPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedDivision", selectedDivision);
        editor.putString("selectedDistrict", selectedDistrict);
        editor.putString("selectedLatitude", selectedLatitude);
        editor.putString("selectedLongitude", selectedLongitude);
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("NamazTimerPreferences", MODE_PRIVATE);
        String selectedDivision = sharedPreferences.getString("selectedDivision", null);
        String selectedDistrict = sharedPreferences.getString("selectedDistrict", null);
        // Get the positions of the saved selections in the spinners' data
        int divisionPosition = getIndexFromText(divisionSpinner, selectedDivision);
        int districtPosition = getIndexFromText(districtSpinner, selectedDistrict);
        // Set the selected items in the spinners
        if (divisionPosition != -1) {
            divisionSpinner.setSelection(divisionPosition);
        }
        if (districtPosition != -1) {
            districtSpinner.setSelection(districtPosition);
        }
    }

    private int getIndexFromText(Spinner spinner, String text) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(text)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChangeLocationActivity.this, Welcome.class));
        overridePendingTransition(0, 0);
        finish();
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}