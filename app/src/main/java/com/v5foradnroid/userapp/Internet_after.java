package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.v5foradnroid.userapp.view.MainActivityc;
import java.util.ArrayList;
import java.util.HashMap;

public class Internet_after extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    HashMap<String, String> resultp = new HashMap<>();

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public Internet_after(Context context2, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context2;
        this.data = arrayList;
        this.imageLoader = new ImageLoader(context2);
    }

    public int getCount() {
        return this.data.size();
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        @SuppressLint("WrongConstant") LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.inflater = layoutInflater;
        View inflate = layoutInflater.inflate(R.layout.gird_internet, viewGroup, false);
        this.resultp = this.data.get(i);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.firstt);
        ((TextView) inflate.findViewById(R.id.title)).setText(this.resultp.get(Internet.TITLE));
        ((TextView) inflate.findViewById(R.id.comm)).setText("discount: ৳" + this.resultp.get(Internet.COMM));
        ((TextView) inflate.findViewById(R.id.price)).setText("price: " + this.resultp.get(Internet.PPRICE));
        ((TextView) inflate.findViewById(R.id.regulaer)).setText("buy ৳  " + this.resultp.get(Internet.REG));
        if (this.resultp.get(Internet.OPN).equals("GP")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.grameenphone));
        } else if (this.resultp.get(Internet.OPN).equals("RB")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.robi));
        } else if (this.resultp.get(Internet.OPN).equals("BL")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.banglalink));
        } else if (this.resultp.get(Internet.OPN).equals("AT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.airtel));
        } else if (this.resultp.get(Internet.OPN).equals("SK")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.skitto));
        } else if (this.resultp.get(Internet.OPN).equals("TT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.teletalk));
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                Internet_after internet_after = Internet_after.this;
                internet_after.resultp = internet_after.data.get(i);
                if (Internet_after.this.resultp.get(Internet.FROM) == null || Internet_after.this.resultp.get(Internet.FROM).isEmpty()) {
                    intent = new Intent(Internet_after.this.context, PinActivity.class);
                } else {
                    intent = new Intent(Internet_after.this.context, MainActivityc.class);
                    intent.putExtra("drive", "drive");
                }
                intent.putExtra("id", Internet_after.this.resultp.get(Internet.f192ID));
                intent.putExtra("paid", Internet_after.this.resultp.get(Internet.Paid));
                intent.putExtra("opt", Internet_after.this.resultp.get(Internet.OPN));
                intent.putExtra("opt2", ExifInterface.GPS_DIRECTION_TRUE);
                intent.putExtra("type", "rc");
                intent.putExtra("amount", Internet_after.this.resultp.get(Internet.PPRICE));
                intent.putExtra("number", Internet_after.this.resultp.get(Internet.Number));
                intent.putExtra("pkg", Internet_after.this.resultp.get(Internet.TITLE));
                if (Internet_after.this.resultp.get(Internet.ROL).equals("getdrive")) {
                    intent.putExtra("type3", "64");
                } else {
                    intent.putExtra("type3", Internet_after.this.resultp.get(Internet.Service));
                }
                intent.putExtra("type2", Internet_after.this.resultp.get(Internet.Drive));
                intent.putExtra("rol", Internet_after.this.resultp.get(Internet.ROL));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Internet_after.this.context.startActivity(intent);
            }
        });
        return inflate;
    }
}
