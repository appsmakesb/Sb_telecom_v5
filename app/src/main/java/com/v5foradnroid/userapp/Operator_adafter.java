package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class Operator_adafter extends BaseAdapter {
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

    public Operator_adafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
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
        ImageView imageView = (ImageView) inflate.findViewById(R.id.flag);
        ((TextView) inflate.findViewById(R.id.title)).setText(this.resultp.get(Operator.opn));
        if (this.resultp.get(Operator.f212ot).equals("GP")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.grameenphone));
        } else if (this.resultp.get(Operator.f212ot).equals("RB")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.robi));
        } else if (this.resultp.get(Operator.f212ot).equals("BL")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.banglalink));
        } else if (this.resultp.get(Operator.f212ot).equals("AT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.airtel));
        } else if (this.resultp.get(Operator.f212ot).equals("SK")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.skitto));
        } else if (this.resultp.get(Operator.f212ot).equals("TT")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.teletalk));
        } else {
            Picasso.get().load(this.resultp.get(Operator.img)).into(imageView);
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                Operator_adafter operator_adafter = Operator_adafter.this;
                operator_adafter.resultp = operator_adafter.data.get(i);
                if (Operator_adafter.this.resultp.get(Operator.otype).indexOf("internet") >= 0) {
                    intent = new Intent(Operator_adafter.this.context, Internet.class);
                } else {
                    intent = new Intent(Operator_adafter.this.context, DisplayActivity.class);
                    intent.putExtra("number", Operator_adafter.this.resultp.get(Operator.number));
                }
                intent.putExtra("opt", Operator_adafter.this.resultp.get(Operator.f212ot));
                intent.putExtra("opt2", Operator_adafter.this.resultp.get(Operator.opn));
                intent.putExtra("type3", Operator_adafter.this.resultp.get(Operator.serv));
                intent.putExtra("type2", "recharge");
                intent.putExtra("type", "rc");
                intent.putExtra("img", Operator_adafter.this.resultp.get(Operator.img));
                intent.putExtra("drive", Operator_adafter.this.resultp.get(Operator.drive));
                Log.d("rftr5vt543",resultp.get(Operator.f212ot)+"\n"+resultp.get(Operator.opn)+"\n"+resultp.get(Operator.serv)+"\n"+resultp.get(Operator.img)+"\n"+resultp.get(Operator.drive));
                Operator_adafter.this.context.startActivity(intent);
            }
        });
        return inflate;
    }
}
