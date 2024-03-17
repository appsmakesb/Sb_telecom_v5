package com.v5foradnroid.userapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


public class FullDialog extends DialogFragment {
    public static final String TAG = "example_dialog";

    public static FullDialog display(FragmentManager fragmentManager, String str) {
        FullDialog fullDialog = new FullDialog();
        fullDialog.show(fragmentManager, str);
        return fullDialog;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate = layoutInflater.inflate(R.layout.custom_dialog_view, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.dialogOpen)).setText(TAG);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }
}
