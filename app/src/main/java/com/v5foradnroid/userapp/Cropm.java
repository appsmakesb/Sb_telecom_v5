package com.v5foradnroid.userapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import java.io.File;
import org.apache.http.cookie.ClientCookie;

public class Cropm extends AppCompatActivity {
    Intent intent;
    CropImageView mCropView;

    private final LoadCallback mLoadCallback = new LoadCallback() {
        public void onError(Throwable th) {
        }

        public void onSuccess() {
        }
    };
    /* Foysal Tech && Ict Foysal */
    public final SaveCallback mSaveCallback = new SaveCallback() {
        public void onError(Throwable th) {
        }

        public void onSuccess(Uri uri) {
            Cropm.this.pDialog.dismiss();
            Intent intent = new Intent(Cropm.this.getApplicationContext(), Addpic.class);
            intent.putExtra(ClientCookie.PATH_ATTR, "ok");
            Cropm.this.startActivity(intent);
            Cropm.this.finish();
        }
    };
    private Uri mSourceUri = null;
    /* Foysal Tech && Ict Foysal */
    public ProgressDialog pDialog;
    Uri sourceUri;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.cropm);
        CropImageView cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.mCropView = cropImageView;
        cropImageView.setCropMode(CropImageView.CropMode.FREE);
        this.mCropView.setBackgroundColor(-5);
        this.mCropView.setOverlayColor(-1440998372);
        Intent intent2 = getIntent();
        this.intent = intent2;
        if (intent2.hasExtra(ClientCookie.PATH_ATTR)) {
            this.sourceUri = Uri.fromFile(new File(this.intent.getExtras().getString(ClientCookie.PATH_ATTR)));
        }
        this.mCropView.load(this.sourceUri).execute(this.mLoadCallback);
        ((Button) findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProgressDialog unused = Cropm.this.pDialog = new ProgressDialog(Cropm.this);
                Cropm.this.pDialog.setMessage("please wait...");
                Cropm.this.pDialog.setIndeterminate(false);
                Cropm.this.pDialog.setCancelable(true);
                Cropm.this.pDialog.show();
                Cropm.this.mCropView.crop(Cropm.this.sourceUri).execute(new CropCallback() {
                    public void onError(Throwable th) {
                    }

                    public void onSuccess(Bitmap bitmap) {
                        Cropm.this.mCropView.save(bitmap).execute(Cropm.this.sourceUri, Cropm.this.mSaveCallback);
                    }
                });
            }
        });
    }

    public static Uri getUriFromDrawableResId(Context context, int i) {
        return Uri.parse("android.resource" + "://" + context.getResources().getResourcePackageName(i) + "/" + context.getResources().getResourceTypeName(i) + "/" + context.getResources().getResourceEntryName(i));
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
