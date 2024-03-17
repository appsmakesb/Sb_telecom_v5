package com.v5foradnroid.userapp.sliderAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.web;
import com.google.firebase.analytics.FirebaseAnalytics;

import de.hdodenhof.circleimageview.CircleImageView;

public class PayAct extends AppCompatActivity implements View.OnClickListener {

    ImageView var_bk_par, var_nag_per, var_roc_par, var_upay_per;
    private String source;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_uidi_one);

        CircleImageView circleImageView = findViewById(R.id.hm_new);
        var_bk_par = findViewById(R.id.bk_par);
        var_nag_per = findViewById(R.id.nag_per);
        var_roc_par = findViewById(R.id.roc_par);
        var_upay_per = findViewById(R.id.upay_per);

        source = getIntent().getStringExtra(FirebaseAnalytics.Param.SOURCE);

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

        findViewById(R.id.live_payId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(PayAct.this, web.class);
                intent.putExtra(FirebaseAnalytics.Param.SOURCE, source);
                startActivity(intent);
            }
        });

        var_bk_par.setOnClickListener(this);
        var_nag_per.setOnClickListener(this);
        var_roc_par.setOnClickListener(this);
        var_upay_per.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bk_par) {
            int drawableResourceId = R.drawable.bkask_personal;
            intent = new Intent(PayAct.this,PayAmount.class);
            intent.putExtra(FirebaseAnalytics.Param.SOURCE, source);
            intent.putExtra("mbtype","bkash personal");
            intent.putExtra("drawableResId", drawableResourceId);
            startActivity(intent);
        } else if (view.getId() == R.id.nag_per) {
            int drawableResourceId = R.drawable.nagad_personal;
            intent = new Intent(PayAct.this,PayAmount.class);
            intent.putExtra(FirebaseAnalytics.Param.SOURCE, source);
            intent.putExtra("mbtype","Nagad personal");
            intent.putExtra("drawableResId", drawableResourceId);
            startActivity(intent);
        } else if (view.getId() == R.id.roc_par) {
            int drawableResourceId = R.drawable.rocket_per;
            intent = new Intent(PayAct.this,PayAmount.class);
            intent.putExtra(FirebaseAnalytics.Param.SOURCE, source);
            intent.putExtra("mbtype","Rocket personal");
            intent.putExtra("drawableResId", drawableResourceId);
            startActivity(intent);
        } else if (view.getId() == R.id.upay_per) {
            int drawableResourceId = R.drawable.upay_personal;
            intent = new Intent(PayAct.this,PayAmount.class);
            intent.putExtra(FirebaseAnalytics.Param.SOURCE, source);
            intent.putExtra("mbtype","Upay personal");
            intent.putExtra("drawableResId", drawableResourceId);
            startActivity(intent);
        }
    }
}