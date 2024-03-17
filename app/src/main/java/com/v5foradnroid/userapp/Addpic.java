package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Addpic extends AppCompatActivity {
    public String[] PERMISSIONS;
    int f151bf = 0;
    Dialog dialog;
    int flag = 0;
    ImageView f152im;
    Uri image;
    ImageView imb;
    ImageView imp;
    ImageView impb;
    JSONParser jsonParser = new JSONParser();
    String mCameraFileName;
    String outPath;
    String scean;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.addpic);

        this.PERMISSIONS = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
        this.f152im = (ImageView) findViewById(R.id.front);
        this.imp = (ImageView) findViewById(R.id.pick);
        this.impb = (ImageView) findViewById(R.id.bpick);
        this.imb = (ImageView) findViewById(R.id.back);
        Button button = (Button) findViewById(R.id.reg);
        if (!hasPermissions(this, this.PERMISSIONS)) {
            showDialog_permission(this);
        }
        this.imp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Addpic addpic = Addpic.this;
                addpic.showDialog_ph(addpic);
                Addpic.this.f151bf = 1;
            }
        });
        this.impb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Addpic addpic = Addpic.this;
                addpic.showDialog_ph(addpic);
                Addpic.this.f151bf = 2;
            }
        });
        if (getIntent().hasExtra(ClientCookie.PATH_ATTR)) {
            String pref = getPref(ClientCookie.PATH_ATTR, getApplicationContext());
            if (!TextUtils.isEmpty(pref) && pref.length() > 3) {
                File file = new File(pref);
                Toast.makeText(this, pref, Toast.LENGTH_SHORT).show();
                if (file.exists()) {
                    this.f152im.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
            }
            String pref2 = getPref("pathb", getApplicationContext());
            if (!TextUtils.isEmpty(pref2) && pref2.length() > 3) {
                File file2 = new File(pref2);
                if (file2.exists()) {
                    this.imb.setImageBitmap(BitmapFactory.decodeFile(file2.getAbsolutePath()));
                }
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String pref = Addpic.getPref(ClientCookie.PATH_ATTR, Addpic.this.getApplicationContext());
                    String pref2 = Addpic.getPref(ClientCookie.PATH_ATTR, Addpic.this.getApplicationContext());
                    Addpic addpic = Addpic.this;
                    if (!addpic.isOnline(addpic)) {
                        Toast.makeText(Addpic.this, "No network connection", Toast.LENGTH_LONG).show();
                        return;
                    }
                    File file = new File(pref);
                    if (!file.exists()) {
                        Toast.makeText(Addpic.this, "Please choose Front part", Toast.LENGTH_LONG).show();
                    } else if (!new File(pref2).exists()) {
                        Toast.makeText(Addpic.this, "Please choose Back part", Toast.LENGTH_LONG).show();
                    } else {
                        Addpic.this.dialog = new Dialog(Addpic.this);
                        Addpic.this.dialog.requestWindowFeature(1);
                        Addpic.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        Addpic.this.dialog.setCancelable(false);
                        Addpic.this.dialog.setContentView(R.layout.custom_progress);
                        Addpic.this.dialog.show();
                        if (file.exists()) {
                            FirebaseVisionImage fromBitmap = FirebaseVisionImage.fromBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            FirebaseVision.getInstance().getOnDeviceTextRecognizer();
                            FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(fromBitmap).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                    Addpic.this.dialog.dismiss();
                                    Addpic.this.processTextRecognitionResult(firebaseVisionText.getText());
                                    for (FirebaseVisionText.TextBlock next : firebaseVisionText.getTextBlocks()) {
                                        next.getText();
                                        next.getConfidence();
                                        next.getRecognizedLanguages();
                                        next.getCornerPoints();
                                        next.getBoundingBox();
                                        for (FirebaseVisionText.Line next2 : next.getLines()) {
                                            next2.getText();
                                            next2.getConfidence();
                                            next2.getRecognizedLanguages();
                                            next2.getCornerPoints();
                                            next2.getBoundingBox();
                                            for (FirebaseVisionText.Element next3 : next2.getElements()) {
                                                next3.getText();
                                                next3.getConfidence();
                                                next3.getRecognizedLanguages();
                                                next3.getCornerPoints();
                                                next3.getBoundingBox();
                                            }
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                public void onFailure(Exception exc) {
                                    Addpic.this.dialog.dismiss();
                                    exc.printStackTrace();
                                }
                            });
                        }
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(Addpic.this, "Please choose Front part\nPlease choose Back part", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public void showDialog_permission(Activity activity) {
        final Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.permission_close);
        ((LinearLayout) dialog2.findViewById(R.id.cem)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((LinearLayout) dialog2.findViewById(R.id.gal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Addpic addpic = Addpic.this;
                ActivityCompat.requestPermissions(addpic, addpic.PERMISSIONS, 1);
            }
        });
        dialog2.show();
    }

    @SuppressLint("ResourceType")
    public void showDialog_ph(Activity activity) {
        final Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.cemera_d);
        dialog2.getWindow().setGravity(Gravity.CENTER);
        ((LinearLayout) dialog2.findViewById(R.id.cem)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Addpic.this.cameraIntent();
            }
        });
        ((LinearLayout) dialog2.findViewById(R.id.gal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Addpic.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
            }
        });
        dialog2.show();
    }

    /* Foysal Tech && Ict Foysal */
    public void processTextRecognitionResult(String str) {
        this.scean = str;
        new loginAccess().execute(new String[0]);
    }

    private boolean hasPermissions(Context context, String... strArr) {
        if (context == null || strArr == null) {
            return true;
        }
        for (String checkSelfPermission : strArr) {
            if (ActivityCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            if (iArr[0] == 0) {
                Toast.makeText(this, "Write Storge Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Write Storge Permission is denied", Toast.LENGTH_SHORT).show();
            }
            if (iArr[1] == 0) {
                Toast.makeText(this, "Read Storge Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Storge Permission is denied", Toast.LENGTH_SHORT).show();
            }
            if (iArr[2] == 0) {
                Toast.makeText(this, "Camera Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission is denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            if (i == 2 && i2 == -1) {
                String[] strArr = {"_data"};
                Cursor query = getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
                query.moveToFirst();
                @SuppressLint("Range")
                String string = query.getString(query.getColumnIndex(strArr[0]));
                query.close();
                BitmapFactory.decodeFile(string);
                File file = new File(string);
                if (file.exists()) {
                    Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (this.f151bf == 1) {
                        this.f152im.setImageBitmap(decodeFile);
                        SavePreferences(ClientCookie.PATH_ATTR, string);
                        return;
                    }
                    this.imb.setImageBitmap(decodeFile);
                    SavePreferences("pathb", string);
                }
            }
        } else if (i2 == -1 && i == 1) {
            if (intent != null) {
                this.image = intent.getData();
            }
            if (this.image == null && this.mCameraFileName != null) {
                this.image = Uri.fromFile(new File(this.mCameraFileName));
            }
            File file2 = new File(this.mCameraFileName);
            if (!file2.exists()) {
                file2.mkdir();
            }
            Intent intent2 = new Intent(getApplicationContext(), Cropm.class);
            if (this.f151bf == 1) {
                SavePreferences(ClientCookie.PATH_ATTR, this.outPath);
            } else {
                SavePreferences("pathb", this.outPath);
            }
            intent2.putExtra(ClientCookie.PATH_ATTR, this.outPath);
            startActivity(intent2);
            finish();
        }
    }

    /* Foysal Tech && Ict Foysal */
    public void cameraIntent() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        Date date = new Date();
        this.outPath = "/storage/emulated/0/.flexisoftwarebd/" + (new SimpleDateFormat("mm_ss").format(date) + ".jpg");
        File file = new File("/storage/emulated/0/.flexisoftwarebd");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(this.outPath);
        this.mCameraFileName = file2.toString();
        intent.putExtra("output", Uri.fromFile(file2));
        startActivityForResult(intent, 1);
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* Foysal Tech && ict Foysal */
        public void onPreExecute() {
            super.onPreExecute();
            Addpic.this.dialog = new Dialog(Addpic.this);
            Addpic.this.dialog.requestWindowFeature(1);
            Addpic.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Addpic.this.dialog.setCancelable(false);
            Addpic.this.dialog.setContentView(R.layout.custom_progress);
            Addpic.this.dialog.show();
        }

        /* Foysal Tech && ict Foysal */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Addpic.getPref("token", Addpic.this.getApplicationContext());
            Addpic.getPref("device", Addpic.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("text", Addpic.this.scean));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Addpic.this.jsonParser.makeHttpRequest((Addpic.getPref(ImagesContract.URL, Addpic.this.getApplicationContext()) + "/apiapp/") + "picture", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Addpic.this.flag = 0;
                    Addpic.this.runOnUiThread(new Runnable() {
                        public void run() {
                            loginAccess loginaccess = loginAccess.this;
                            loginaccess.showError(Addpic.this, string);
                        }
                    });
                }
                if (i == 1) {
                    Addpic.this.flag = 0;
                } else {
                    Addpic.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Addpic.this.flag = 0;
                if (i == 0) {
                    Addpic.this.showAlert(string);
                    return null;
                }
                Intent intent = new Intent(Addpic.this.getApplicationContext(), RegisterActivity.class);
                intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, makeHttpRequest.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                intent.putExtra("birth", makeHttpRequest.getString("birth"));
                intent.putExtra("nid", makeHttpRequest.getString("nid"));
                Addpic.this.startActivity(intent);
                Addpic.this.finish();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* Foysal Tech && ict Foysal */
        public void onPostExecute(String str) {
            Addpic.this.dialog.dismiss();
            if (Addpic.this.flag == 1) {
                Toast.makeText(Addpic.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }

        public void showError(Activity activity, String str) {
            @SuppressLint("ResourceType")
            Dialog dialog = new Dialog(activity, 2131886564);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_dialog_view);
            ((TextView) dialog.findViewById(R.id.dialogOpen)).setText(str);
            dialog.show();
        }

        public void SavePreferences(String str, String str2) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(Addpic.this.getApplicationContext()).edit();
            edit.putString(str, str2);
            edit.commit();
        }
    }

    /* Foysal Tech && Ict Foysal */
    public void showAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str).setTitle("Message").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    /* Foysal Tech && Ict Foysal */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void onBackPressed() {
        startActivity(new Intent(Addpic.this, Login_Activity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
