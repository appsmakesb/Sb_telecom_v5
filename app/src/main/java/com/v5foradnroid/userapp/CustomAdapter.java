package com.v5foradnroid.userapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CustomAdapter extends PagerAdapter {
    private Activity activity;
    private Integer[] imagesArray;
    private String[] namesArray;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public CustomAdapter(Activity activity2, Integer[] numArr, String[] strArr) {
        this.activity = activity2;
        this.imagesArray = numArr;
        this.namesArray = strArr;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.activity.getLayoutInflater().inflate(R.layout.content_custom, viewGroup, false);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView);
        String[] strArr = this.namesArray;
        if (strArr[i] != null && !strArr[i].isEmpty()) {
            Picasso.get().load(this.namesArray[i]).into((Target) new Target() {
                public void onBitmapFailed(Exception exc, Drawable drawable) {
                }

                public void onPrepareLoad(Drawable drawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    imageView.setBackground(new BitmapDrawable(bitmap));
                }
            });
        }
        viewGroup.addView(inflate);
        return inflate;
    }

    public int getCount() {
        return this.imagesArray.length;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
