package com.v5foradnroid.userapp.sliderAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.v5foradnroid.userapp.R;

import java.util.ArrayList;

public class SliderAdapterThird extends RecyclerView.Adapter<SliderAdapterThird.viewHolder> implements Filterable {

    Context context;
    ArrayList<SliderThirdModel> list;
    ArrayList<SliderThirdModel> listfull;

    public SliderAdapterThird(Context context, ArrayList<SliderThirdModel> list) {
        this.context = context;
        this.listfull = list;
        this.list = new ArrayList<>(this.listfull);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.third_recyclarview, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        SliderThirdModel model = list.get(position);
        holder.textView.setText(model.getThirdSliderText());
        Glide.with(context).load(model.getThirdSliderimg()).centerCrop().into(holder.thirdImageView);

        /*if (position == 4) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Image Two is Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        holder.thirdImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getThirdSliderUrl().equals("NotChangeable")) {
                    Intent intent = new Intent(context, AllSliderActivity.class);
                    context.startActivity(intent);
                } else {
                    if (model.getThirdSliderUrl().equals("")) {
                        Toast.makeText(context, "এখানে কোন ঠিকানা দেওয়া হয়নি", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("uelData", list.get(position).getThirdSliderUrl());
                        context.startActivity(intent);
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
            ArrayList<SliderThirdModel> recipeFilterlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                recipeFilterlist.addAll(listfull);
            } else {
                String filterPattern = constraint.toString().trim();
                for (SliderThirdModel recipeModel : listfull) {
                    if (recipeModel.getThirdSliderText().toUpperCase().contains(filterPattern))
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

        ImageView thirdImageView;
        TextView textView;

        public viewHolder(@NonNull View itemView2) {
            super(itemView2);
            thirdImageView = itemView.findViewById(R.id.thirdImageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void deleteItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

}
