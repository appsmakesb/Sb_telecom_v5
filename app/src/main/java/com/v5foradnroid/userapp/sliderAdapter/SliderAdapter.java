package com.v5foradnroid.userapp.sliderAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.activities.ActivitySplash;
import com.v5foradnroid.userapp.activities.StoreMainAc;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private final List<SliderOne> mSliderItems;
    public Context context;

    public SliderAdapter(Context context, ArrayList<SliderOne> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.context = context;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SliderOne sliderOne = mSliderItems.get(position);


        Glide.with(viewHolder.itemView).load(sliderOne.getSliderimg()).centerCrop().into(viewHolder.imageViewBackground);

        if (sliderOne.getSliderText() != null) {
            viewHolder.OneSliderTv.setText(sliderOne.getSliderText());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSliderItems.get(position).getSliderUrl().equals("")) {
                    Toast.makeText(context, "এখানে কোন ঠিকানা দেওয়া হয়নি", Toast.LENGTH_SHORT).show();
                }
                if (mSliderItems.get(position).getSliderUrl().contains("NotChangeable")) {
                    Intent intent = new Intent(context, ActivitySplash.class);
                    context.startActivity(intent);
                }
                if (mSliderItems.get(position).getSliderUrl().contains("OpenCategory")) {
                    Intent intent = new Intent(context, StoreMainAc.class);
                    intent.putExtra("OpenCategory", "OpenCategory");
                    context.startActivity(intent);
                }
                if (mSliderItems.get(position).getSliderUrl().contains("Payment")) {
                    Intent intent = new Intent(context, StoreMainAc.class);
                    intent.putExtra("OpenCategory", "payment");
                    context.startActivity(intent);
                }
                if (mSliderItems.get(position).getSliderUrl().contains("http")) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("uelData", mSliderItems.get(position).getSliderUrl());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView OneSliderTv;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            OneSliderTv = itemView.findViewById(R.id.OneSliderText);
            this.itemView = itemView;
        }
    }
}
