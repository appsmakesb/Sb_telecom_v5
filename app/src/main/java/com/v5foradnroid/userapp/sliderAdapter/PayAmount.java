package com.v5foradnroid.userapp.sliderAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.makepay;
import com.google.firebase.analytics.FirebaseAnalytics;

import de.hdodenhof.circleimageview.CircleImageView;

public class PayAmount extends AppCompatActivity {

    private String source;
    private Intent intent_var;
    private EditText var_im_amou;
    private String var_mbtype;
    int receivedDrawableResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_uidi_two);

        var_im_amou = findViewById(R.id.im_amou);
        CircleImageView circleImageView = findViewById(R.id.hm_new);
        TextView var_textView = findViewById(R.id.textView10);

        Intent intent = getIntent();
        receivedDrawableResId = intent.getIntExtra("drawableResId", 0);
        var_mbtype = intent.getStringExtra("mbtype");
        this.source = getIntent().getStringExtra(FirebaseAnalytics.Param.SOURCE);

        if (var_mbtype != null) {
            var_textView.setText(var_mbtype);
        }

        ViewGroup parent = (ViewGroup) circleImageView.getParent();
        ViewTreeObserver viewTreeObserver = parent.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                parent.removeView(circleImageView);
                parent.addView(circleImageView);
                circleImageView.setElevation(50);
                circleImageView.setTranslationZ(50);
            }
        });
        circleImageView.setImageResource(receivedDrawableResId);

        findViewById(R.id.next_withamo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amvar = var_im_amou.getText().toString().trim();
                if (amvar.length() < 1) {
                    Toast.makeText(PayAmount.this, "Please Enter correct Amount", Toast.LENGTH_LONG).show();
                } else {
                    intent_var = new Intent(PayAmount.this, makepay.class);
                    intent_var.putExtra("amvar", amvar);
                    intent_var.putExtra("mbtype", var_mbtype);
                    intent_var.putExtra(FirebaseAnalytics.Param.SOURCE, source);
                    intent_var.putExtra("drawableResId", receivedDrawableResId);
                    startActivity(intent_var);
                }
            }
        });


    }
}