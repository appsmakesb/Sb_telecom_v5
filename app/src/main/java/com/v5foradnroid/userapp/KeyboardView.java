package com.v5foradnroid.userapp;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


public class KeyboardView extends FrameLayout implements View.OnClickListener {
    private EditText mPasswordField;

    public KeyboardView(Context context) {
        super(context);
        init();
    }

    public KeyboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public KeyboardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.keyboard, this);
        initViews();
    }

    private void initViews() {
        this.mPasswordField = (EditText) mo24227$(R.id.passwordpin);
        mo24227$(R.id.t9_key_0).setOnClickListener(this);
        mo24227$(R.id.t9_key_1).setOnClickListener(this);
        mo24227$(R.id.t9_key_2).setOnClickListener(this);
        mo24227$(R.id.t9_key_3).setOnClickListener(this);
        mo24227$(R.id.t9_key_4).setOnClickListener(this);
        mo24227$(R.id.t9_key_5).setOnClickListener(this);
        mo24227$(R.id.t9_key_6).setOnClickListener(this);
        mo24227$(R.id.t9_key_7).setOnClickListener(this);
        mo24227$(R.id.t9_key_8).setOnClickListener(this);
        mo24227$(R.id.t9_key_9).setOnClickListener(this);
        mo24227$(R.id.t9_key_clear).setOnClickListener(this);
        mo24227$(R.id.t9_key_backspace).setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getTag() == null || !"number_button".equals(view.getTag())) {
            switch (view.getId()) {
                case R.id.t9_key_backspace:
                    Editable text = this.mPasswordField.getText();
                    int length = text.length();
                    if (length > 0) {
                        text.delete(length - 1, length);
                        return;
                    }
                    return;
                case R.id.t9_key_clear:
                    this.mPasswordField.setText((CharSequence) null);
                    return;
                default:
                    return;
            }
        } else {
            this.mPasswordField.append(((TextView) view).getText());
        }
    }

    public String getInputText() {
        return this.mPasswordField.getText().toString();
    }

    /* Foysal Tech && ict Foysal */
    /* renamed from: $ */
    public <T extends View> T mo24227$(int i) {
        return super.findViewById(i);
    }
}
