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

public class Noticeadafter extends BaseAdapter {
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

    public Noticeadafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        View inflate = layoutInflater.inflate(R.layout.noticelist, viewGroup, false);
        this.resultp = this.data.get(i);
        ((TextView) inflate.findViewById(R.id.date)).setText(this.resultp.get(News.date));
        ((TextView) inflate.findViewById(R.id.notice)).setText(this.resultp.get(News.notice));
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Noticeadafter noticeadafter = Noticeadafter.this;
                noticeadafter.resultp = noticeadafter.data.get(i);
            }
        });
        return inflate;
    }
}
