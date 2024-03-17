package com.v5foradnroid.userapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.v5foradnroid.userapp.sliderAdapter.PayAct;

public class Payment_type extends AppCompatActivity {

    RelativeLayout relativeLayoutTb;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_balance_type);

        relativeLayoutTb = findViewById(R.id.relt_layTool);

        Spinner sourceSpinner = findViewById(R.id.source_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.source_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);

        Button nextButton = findViewById(R.id.sub);
        nextButton.setOnClickListener(view -> {
            String selectedSource = (String) sourceSpinner.getSelectedItem();
            Intent intent = new Intent(Payment_type.this, PayAct.class);
            if (selectedSource.equals("Main")) {
                intent.putExtra("source", "main");
            } else if (selectedSource.equals("Drive")) {
                intent.putExtra("source", "drive");
            } else {
                intent.putExtra("source", "bank");
            }
            startActivity(intent);
        });

        if (loadColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
//                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                relativeLayoutTb.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }


    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(0, 0);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}
