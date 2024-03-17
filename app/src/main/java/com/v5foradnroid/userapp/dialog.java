package com.v5foradnroid.userapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.ecomflexi.softwarelabbd.dialog */
public class dialog {
    private Activity mActivity;

    dialog(Activity activity) {
        this.mActivity = activity;
    }

    public void build(String str, String str2) {
        View inflate = LayoutInflater.from(this.mActivity).inflate(R.layout.dialog, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.mActivity, R.style.AlertDialogCustom));
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setView(inflate);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
