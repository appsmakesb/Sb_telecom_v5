package com.v5foradnroid.userapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Confirm extends Dialog implements View.OnClickListener {

    /* renamed from: c */
    public Activity f158c;

    /* renamed from: d */
    public Dialog f159d;

    /* renamed from: no */
    public Button f160no;
    public Button yes;

    public Confirm(Activity activity) {
        super(activity);
        this.f158c = activity;
    }

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.confirm);
        this.yes = (Button) findViewById(R.id.btn_yes);
        this.f160no = (Button) findViewById(R.id.btn_no);
        this.yes.setOnClickListener(this);
        this.f160no.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.btn_yes:
                this.f158c.finish();
                break;
        }
        dismiss();
    }
}
