package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.v5foradnroid.userapp.view.MainActivityc;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

public class service_adafter extends BaseAdapter {
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

    public service_adafter(Context context2, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context2;
        this.data = arrayList;
        this.imageLoader = new ImageLoader(context2);
    }

    public int getCount() {
        return data.size();
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        @SuppressLint("WrongConstant") LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        inflater = layoutInflater;
        View inflate = layoutInflater.inflate(R.layout.list_service, viewGroup, false);
        resultp = data.get(i);

        TextView textView = (TextView) inflate.findViewById(R.id.title);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.flag);
        int colorFrom = loadColor();
        imageView.setColorFilter(colorFrom);
        textView.setText(resultp.get(Welcome.Service_n));

        if (resultp.get(Welcome.act).indexOf("recharge") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_recharge));
        } else if (resultp.get(Welcome.act).indexOf("drive") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drive));
        } else if (resultp.get(Welcome.act).indexOf("internet") >= 0) {
            textView.setText("Regular pack");
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pack));
        } else if (resultp.get(Welcome.Service_id).indexOf("131072") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nogad));
        } else if (resultp.get(Welcome.Service_id).indexOf("256") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rocket));
        } else if (resultp.get(Welcome.Service_id).indexOf("128") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bkash));
        } else if (resultp.get(Welcome.Service_id).indexOf("1048576") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.upay));
        } else if (resultp.get(Welcome.Service_id).indexOf("1024") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mcash));
        } else if (resultp.get(Welcome.Service_id).indexOf("2048") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ucash));
        } else if (resultp.get(Welcome.Service_id).indexOf("4096") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mycash));
        } else if (resultp.get(Welcome.Service_id).indexOf("32768") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.surecash));
        } else if (resultp.get(Welcome.act).equals("banktransfer")) {
            textView.setText("Banking");
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ibanking));
        } else if (resultp.get(Welcome.act).indexOf("bank") >= 0) {
            textView.setText("Pay Bill");
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.billpay));
        } else if (resultp.get(Welcome.act).indexOf("history") >= 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_history_24));
        } else {
            Picasso.get().load(resultp.get(Welcome.img)).resize(200, 200).centerCrop().into(imageView);
        }
        inflate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean z;
                Intent intent;
                service_adafter service_adafter = service_adafter.this;
                service_adafter.resultp = service_adafter.data.get(i);
                boolean z2 = true;
                if (service_adafter.resultp.get(Welcome.act).indexOf("addres") >= 0) {
                    intent = new Intent(service_adafter.context, Addres.class);
                    z = true;
                } else {
                    z = false;
                    intent = null;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("history") >= 0) {
                    intent = new Intent(service_adafter.context, history_op.class);
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("myres") >= 0) {
                    intent = new Intent(service_adafter.context, Myreseller.class);
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("internet") >= 0) {
                    intent = new Intent(service_adafter.context, Operator.class);
                    intent.putExtra("type", "internet");
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("drive") >= 0) {
                    intent = new Intent(service_adafter.context, Operator.class);
                    intent.putExtra("type", "internet");
                    intent.putExtra("drive", "drive");
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("news") >= 0) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/unitelpro"));
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("sms") >= 0) {
                    intent = new Intent(service_adafter.context, Smssend.class);
                    intent.putExtra("type", "sms");
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("make") >= 0) {
                    intent = new Intent(service_adafter.context, makepay.class);
                    intent.putExtra("type", service_adafter.resultp.get(Welcome.Service_id));
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("bank") >= 0) {
                    intent = new Intent(service_adafter.context, Billpay.class);
                    intent.putExtra("type", "internet");
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("mobile") >= 0) {
                    intent = new Intent(service_adafter.context, MainActivityc.class);
                    intent.putExtra("type", service_adafter.resultp.get(Welcome.Service_id));
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("transfer") >= 0) {
                    intent = new Intent(service_adafter.context, Transfer.class);
                    intent.putExtra("type", service_adafter.resultp.get(Welcome.Service_id));
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("banktransfer") >= 0) {
                    intent = new Intent(service_adafter.context, Banktransfer.class);
                    intent.putExtra("type", service_adafter.resultp.get(Welcome.Service_id));
                    z = true;
                }
                if (service_adafter.resultp.get(Welcome.act).indexOf("recharge") >= 0) {
                    intent = new Intent(service_adafter.context, MainActivityc.class);
                    intent.putExtra("opt", "");
                    intent.putExtra("opt2", "");
                    intent.putExtra("type", "rc");
                } else {
                    z2 = z;
                }
                intent.putExtra("type3", service_adafter.resultp.get(Welcome.Service_id));
                intent.putExtra("type2", service_adafter.resultp.get(Welcome.Service_n));
                if (z2) {
                    service_adafter.context.startActivity(intent);
                }
            }
        });
        return inflate;
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", context.getColor(R.color.primary));
        return selectedColor;
    }

}
