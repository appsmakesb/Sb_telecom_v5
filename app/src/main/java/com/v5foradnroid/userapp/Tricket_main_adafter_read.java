package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Tricket_main_adafter_read extends BaseAdapter {
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

    public Tricket_main_adafter_read(Context context2, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context2;
        this.data = arrayList;
        this.imageLoader = new ImageLoader(context2);
    }

    public int getCount() {
        return this.data.size();
    }

    @SuppressLint("WrongConstant")
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        this.resultp = this.data.get(i);
        this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (!this.resultp.get(Tricket_main_read.aid).equals("no")) {
            view2 = this.inflater.inflate(R.layout.tricket_main_list_read, viewGroup, false);
        } else {
            view2 = this.inflater.inflate(R.layout.my_message, viewGroup, false);
        }
        ((TextView) view2.findViewById(R.id.message_body)).setText(this.resultp.get(Tricket_main_read.TITLE));
        ((TextView) view2.findViewById(R.id.date)).setText(this.resultp.get(Tricket_main_read.date));
        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        return view2;
    }
}
