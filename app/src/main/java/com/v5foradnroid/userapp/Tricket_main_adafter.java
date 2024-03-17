package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Tricket_main_adafter extends BaseAdapter {
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

    public Tricket_main_adafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        View inflate = layoutInflater.inflate(R.layout.tricket_main_list, viewGroup, false);
        this.resultp = this.data.get(i);
        ((TextView) inflate.findViewById(R.id.title)).setText(this.resultp.get(Tricket_main.TITLE));
        ((TextView) inflate.findViewById(R.id.date)).setText(this.resultp.get(Tricket_main.date));
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Tricket_main_adafter tricket_main_adafter = Tricket_main_adafter.this;
                tricket_main_adafter.resultp = tricket_main_adafter.data.get(i);
                Intent intent = new Intent(Tricket_main_adafter.this.context, Tricket_main_read.class);
                intent.putExtra("id", Tricket_main_adafter.this.resultp.get(Tricket_main.f283id));
                Tricket_main_adafter.this.context.startActivity(intent);
            }
        });
        return inflate;
    }
}
