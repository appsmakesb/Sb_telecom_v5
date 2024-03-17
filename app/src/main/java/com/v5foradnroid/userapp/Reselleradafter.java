package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.ArrayList;
import java.util.HashMap;

public class Reselleradafter extends BaseAdapter {
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

    public Reselleradafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        View inflate = layoutInflater.inflate(R.layout.listview_item_r, viewGroup, false);
        this.resultp = this.data.get(i);
        ((TextView) inflate.findViewById(R.id.mobile)).setText(this.resultp.get(Myreseller.Teei));
        ((TextView) inflate.findViewById(R.id.main)).setText("Main: " + this.resultp.get(Myreseller.Phone));
        ((TextView) inflate.findViewById(R.id.bank)).setText("Bank: " + this.resultp.get(Myreseller.bbalance));
        ((TextView) inflate.findViewById(R.id.drive)).setText("Drive: " + this.resultp.get(Myreseller.Dbal));
        ((TextView) inflate.findViewById(R.id.type)).setText(this.resultp.get(Myreseller.TYPe));
        ((TextView) inflate.findViewById(R.id.name)).setText(this.resultp.get(Myreseller.name));
        ((TextView) inflate.findViewById(R.id.email)).setText(this.resultp.get(Myreseller.Email));
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Reselleradafter reselleradafter = Reselleradafter.this;
                reselleradafter.resultp = reselleradafter.data.get(i);
                PopupMenu popupMenu = new PopupMenu(Reselleradafter.this.context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.edit) {
                            Intent intent = new Intent(Reselleradafter.this.context, Editres.class);
                            intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, Reselleradafter.this.resultp.get(Myreseller.LINK));
                            intent.putExtra("time", Reselleradafter.this.resultp.get(Myreseller.TIME));
                            intent.putExtra("email", Reselleradafter.this.resultp.get(Myreseller.Email));
                            intent.putExtra("id", Reselleradafter.this.resultp.get(Myreseller.Model));
                            intent.putExtra("nick", Reselleradafter.this.resultp.get(Myreseller.name));
                            intent.putExtra("model", Reselleradafter.this.resultp.get(Myreseller.mmm));
                            intent.putExtra("tel", Reselleradafter.this.resultp.get(Myreseller.Teei));
                            intent.putExtra("nid", Reselleradafter.this.resultp.get(Myreseller.NID));
                            intent.putExtra("birth", Reselleradafter.this.resultp.get(Myreseller.Birth));
                            Reselleradafter.this.context.startActivity(intent);
                            return true;
                        } else if (itemId != R.id.pay) {
                            return true;
                        } else {
                            Intent intent2 = new Intent(Reselleradafter.this.context, Payment.class);
                            intent2.putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, Reselleradafter.this.resultp.get(Myreseller.LINK));
                            intent2.putExtra("amount", Reselleradafter.this.resultp.get(Myreseller.Phone));
                            intent2.putExtra("bamount", Reselleradafter.this.resultp.get(Myreseller.bbalance));
                            intent2.putExtra("damount", Reselleradafter.this.resultp.get(Myreseller.Dbal));
                            intent2.putExtra("email", Reselleradafter.this.resultp.get(Myreseller.Email));
                            intent2.putExtra("id", Reselleradafter.this.resultp.get(Myreseller.Model));
                            intent2.putExtra("nick", Reselleradafter.this.resultp.get(Myreseller.name));
                            intent2.putExtra("model", Reselleradafter.this.resultp.get(Myreseller.mmm));
                            intent2.putExtra("tel", Reselleradafter.this.resultp.get(Myreseller.Teei));
                            Reselleradafter.this.context.startActivity(intent2);
                            return true;
                        }
                    }
                });
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
        return inflate;
    }
}
