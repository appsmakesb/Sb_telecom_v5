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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class history_adafter extends BaseAdapter {
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

    public history_adafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        View inflate = layoutInflater.inflate(R.layout.list_service, viewGroup, false);
        this.resultp = this.data.get(i);
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.flag);
        textView.setText(this.resultp.get(Welcome.Service_n));
        if (this.resultp.get(history_op.act).indexOf("recharge") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.recharge));
        } else if (this.resultp.get(history_op.act).indexOf("drive") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.drive));
        } else if (this.resultp.get(history_op.act).indexOf("payment") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.send_money));
        } else if (this.resultp.get(history_op.act).indexOf("all") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.his));
        } else if (this.resultp.get(history_op.act).indexOf("internet") >= 0) {
            textView.setText("Package");
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.pkg));
        } else if (this.resultp.get(history_op.Service_id).indexOf("131072") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.nogad));
        } else if (this.resultp.get(history_op.Service_id).indexOf("256") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.rocket));
        } else if (this.resultp.get(history_op.Service_id).indexOf("128") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.bkash));
        } else if (this.resultp.get(history_op.Service_id).indexOf("1048576") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.upay));
        } else if (this.resultp.get(history_op.Service_id).indexOf("1024") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mcash));
        } else if (this.resultp.get(history_op.Service_id).indexOf("2048") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ucash));
        } else if (this.resultp.get(history_op.Service_id).indexOf("4096") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mycash));
        } else if (this.resultp.get(history_op.Service_id).indexOf("32768") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.surecash));
        } else if (this.resultp.get(history_op.act).indexOf("banktransfer") >= 0) {
            textView.setText("Banking");
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ibanking));
        } else if (this.resultp.get(history_op.act).indexOf("bank") >= 0) {
            textView.setText("Pay Bill");
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.billpay));
        } else {
            Picasso.get().load(this.resultp.get(history_op.img)).resize(200, 200).centerCrop().into(imageView);
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                history_adafter history_adafter = history_adafter.this;
                history_adafter.resultp = history_adafter.data.get(i);
                Integer.parseInt(history_adafter.this.resultp.get(history_op.Service_id));
                Intent intent = new Intent(history_adafter.this.context, Mainlist.class);
                intent.putExtra("type", history_adafter.this.resultp.get(history_op.Service_id));
                history_adafter.this.context.startActivity(intent);
            }
        });
        return inflate;
    }
}
