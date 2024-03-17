package com.v5foradnroid.userapp.sliderAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.v5foradnroid.userapp.Internet;
import com.v5foradnroid.userapp.R;
import com.google.android.gms.common.internal.ImagesContract;

import java.util.ArrayList;

public class SliderAdapterAll extends RecyclerView.Adapter<SliderAdapterAll.viewHolder> implements Filterable {

    public static String jsonUrl;
    Context context;
    ArrayList<SliderData> list;
    ArrayList<SliderData> listfull;

    public SliderAdapterAll(Context context, ArrayList<SliderData> list) {
        this.context = context;
        this.listfull = list;
        this.list = new ArrayList<>(this.listfull);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_recyclarview, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        jsonUrl = getPref23(ImagesContract.URL, context);
        SliderData model = list.get(position);
        holder.textView.setText(model.getSliderText());
        Glide.with(context).load(model.getSliderimg()).centerCrop().into(holder.SliderTwoImage);

        /*if (position == 4) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Image Two is Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        holder.SliderTwoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Internet.class);
                if (model.getSliderUrl().equals("NotChangeable")) {
                    Intent intent1 = new Intent(context, AllSliderActivity.class);
                    context.startActivity(intent1);
                }
                if (list.get(position).getSliderUrl().equals("BL")) {
                    intent.putExtra("opt", "BL");
                    intent.putExtra("opt2", "Banglalink");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/bl.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }
                if (list.get(position).getSliderUrl().equals("GP")) {
                    intent.putExtra("opt", "GP");
                    intent.putExtra("opt2", "GP");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/gp.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }
                if (list.get(position).getSliderUrl().equals("RB")) {
                    intent.putExtra("opt", "RB");
                    intent.putExtra("opt2", "Robi");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/rb.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }
                if (list.get(position).getSliderUrl().equals("AT")) {
                    intent.putExtra("opt", "AT");
                    intent.putExtra("opt2", "Airtel");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/at.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }

                if (list.get(position).getSliderUrl().equals("TT")) {
                    intent.putExtra("opt", "TT");
                    intent.putExtra("opt2", "TeleTalk");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/tt.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }

                if (list.get(position).getSliderUrl().equals("SK")) {
                    intent.putExtra("opt", "SK");
                    intent.putExtra("opt2", "Skito");
                    intent.putExtra("type3", "1");
                    intent.putExtra("type2", "recharge");
                    intent.putExtra("type", "rc");
                    intent.putExtra("img", jsonUrl+"/assets/img/sk.png");
                    intent.putExtra("drive", "drive");
                    context.startActivity(intent);
                }
                else {
                    if (model.getSliderUrl().equals("")) {
                        Toast.makeText(context, "এখানে কোন ঠিকানা দেওয়া হয়নি", Toast.LENGTH_SHORT).show();
                    }
                    if (model.getSliderUrl().contains("http")) {
                        Intent intent3 = new Intent(context, WebActivity.class);
                        intent3.putExtra("uelData", list.get(position).getSliderUrl());
                        context.startActivity(intent3);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return recipeFilter;
    }

    private final Filter recipeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SliderData> recipeFilterlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                recipeFilterlist.addAll(listfull);
            } else {
                String filterPattern = constraint.toString().trim();
                for (SliderData recipeModel : listfull) {
                    if (recipeModel.getSliderText().toUpperCase().contains(filterPattern))
                        recipeFilterlist.add(recipeModel);
                }
            }
            FilterResults results = new FilterResults();
            results.values = recipeFilterlist;
            results.count = recipeFilterlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView SliderTwoImage;
        TextView textView;

        public viewHolder(@NonNull View itemView2) {
            super(itemView2);
            SliderTwoImage = itemView.findViewById(R.id.SliderTwoImage);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void deleteItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public static String getPref23(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }
}
