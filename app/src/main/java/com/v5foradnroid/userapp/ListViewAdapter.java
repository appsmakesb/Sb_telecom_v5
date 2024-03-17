package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    String operator;
    HashMap<String, String> resultp = new HashMap<>();

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public ListViewAdapter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        View inflate = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);
        this.resultp = this.data.get(i);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.fla);
        TextView textView = (TextView) inflate.findViewById(R.id.type);
        TextView textView2 = (TextView) inflate.findViewById(R.id.amountt);
        TextView textView3 = (TextView) inflate.findViewById(R.id.cost);
        TextView textView4 = (TextView) inflate.findViewById(R.id.pre);
        TextView textView5 = (TextView) inflate.findViewById(R.id.trns);
        TextView textView6 = (TextView) inflate.findViewById(R.id.status);
        ((TextView) inflate.findViewById(R.id.to)).setText(this.resultp.get(Mainlist.TITLE));
        textView2.setText("Amount: " + this.resultp.get(Mainlist.Phone));
        ((TextView) inflate.findViewById(R.id.balance)).setText(this.resultp.get(Mainlist.BALANCE));
        ((TextView) inflate.findViewById(R.id.uptime)).setText(this.resultp.get(Mainlist.Uptime));
        if (Integer.parseInt(this.resultp.get(Mainlist.service_id)) == 11) {
            textView3.setText("Debit: " + this.resultp.get(Mainlist.debit));
            textView6.setText("Credit: " + this.resultp.get(Mainlist.credit));
            textView2.setVisibility(View.GONE);
        } else {
            textView3.setText("Cost: " + this.resultp.get(Mainlist.CONTENT));
            textView6.setText(this.resultp.get(Mainlist.FLAG));
        }
        if (this.resultp.get(Mainlist.FLAG).indexOf("Cancel") >= 0) {
            textView6.setTextColor(this.context.getResources().getColor(R.color.red));
        }
        if (this.resultp.get(Mainlist.FLAG).indexOf("Failed") >= 0) {
            textView6.setTextColor(this.context.getResources().getColor(R.color.red));
        }
        if (this.resultp.get(Mainlist.FLAG).indexOf("Processing") >= 0) {
            textView6.setTextColor(this.context.getResources().getColor(R.color.blue_light));
        }
        if (this.resultp.get(Mainlist.FLAG).indexOf("Waiting") >= 0) {
            textView6.setTextColor(this.context.getResources().getColor(R.color.light_yellow));
        }
        if (this.resultp.get(Mainlist.FLAG).indexOf("Pending") >= 0) {
            textView6.setTextColor(this.context.getResources().getColor(R.color.orange_light));
        }
        if (this.resultp.get(Mainlist.Pcode).equals("GP")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.grameenphone));
        } else if (this.resultp.get(Mainlist.Pcode).equals("RB")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.robi));
        } else if (this.resultp.get(Mainlist.Pcode).equals("BL")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.banglalink));
        } else if (this.resultp.get(Mainlist.Pcode).equals("AT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.airtel));
        } else if (this.resultp.get(Mainlist.Pcode).equals("SK")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.skitto));
        } else if (this.resultp.get(Mainlist.Pcode).equals("TT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.teletalk));
        } else {
            Picasso.get().load(this.resultp.get(Mainlist.img)).into(imageView);
        }
        textView5.setText(this.resultp.get(Mainlist.LINK));
        textView.setText(this.resultp.get(Mainlist.TYPe));
        textView4.setText(this.resultp.get(Mainlist.Model));
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ListViewAdapter listViewAdapter = ListViewAdapter.this;
                listViewAdapter.resultp = listViewAdapter.data.get(i);
                Intent intent = new Intent(ListViewAdapter.this.context, web.class);
                intent.putExtra("link", ListViewAdapter.this.resultp.get(Mainlist.LINK));
                intent.putExtra("time", ListViewAdapter.this.resultp.get(Mainlist.TIME));
                intent.putExtra(FirebaseAnalytics.Param.CONTENT, ListViewAdapter.this.resultp.get(Mainlist.CONTENT));
                intent.putExtra("title", ListViewAdapter.this.resultp.get(Mainlist.TITLE));
                intent.putExtra("flag", ListViewAdapter.this.resultp.get(Mainlist.FLAG));
                if (Integer.parseInt(ListViewAdapter.this.resultp.get(Mainlist.service_id)) == 11) {
                    Toast.makeText(ListViewAdapter.this.context, "No data for view", Toast.LENGTH_LONG).show();
                } else {
                    new ViewDialog().showDialog(ListViewAdapter.this.context, ListViewAdapter.this.resultp.get(Mainlist.TITLE), ListViewAdapter.this.resultp.get(Mainlist.Phone), ListViewAdapter.this.resultp.get(Mainlist.TYPe), ListViewAdapter.this.resultp.get(Mainlist.CONTENT), ListViewAdapter.this.resultp.get(Mainlist.LINK), ListViewAdapter.this.resultp.get(Mainlist.Uptime), ListViewAdapter.this.resultp.get(Mainlist.Model), ListViewAdapter.this.resultp.get(Mainlist.BALANCE), ListViewAdapter.this.resultp.get(Mainlist.SENder), ListViewAdapter.this.resultp.get(Mainlist.f199ut));
                }
            }
        });
        return inflate;
    }

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Context context, String str, String str2, String str3, String str4, final String str5, String str6, String str7, String str8, String str9, String str10) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.order);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ((TextView) dialog.findViewById(R.id.cost)).setText(str4);
            ((TextView) dialog.findViewById(R.id.trnx)).setText(str5);
            ((TextView) dialog.findViewById(R.id.time)).setText(str6);
            ((TextView) dialog.findViewById(R.id.msg)).setText(str7);
            ((TextView) dialog.findViewById(R.id.newb)).setText(str8);
            ((TextView) dialog.findViewById(R.id.sender)).setText(str9);
            ((TextView) dialog.findViewById(R.id.utime)).setText(str10);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View view) {
                    String str = str5;
                    ListViewAdapter.this.context = view.getContext();
                    if (Build.VERSION.SDK_INT >= 11) {
                        ((ClipboardManager) ListViewAdapter.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Copy", str));
                        Toast.makeText(ListViewAdapter.this.context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                    } else {
                        ((android.text.ClipboardManager) ListViewAdapter.this.context.getSystemService("clipboard")).setText(str);
                        Toast.makeText(ListViewAdapter.this.context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
