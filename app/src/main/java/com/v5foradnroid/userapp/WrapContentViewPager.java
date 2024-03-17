package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.viewpager.widget.ViewPager;

public class WrapContentViewPager extends ViewPager {
    private int mCurrentPagePosition = 0;

    public WrapContentViewPager(Context context) {
        super(context);
    }

    public WrapContentViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* Foysal Tech && ict Foysal */
    @SuppressLint("WrongConstant")
    public void onMeasure(int i, int i2) {
        View childAt;
        try {
            if ((MeasureSpec.getMode(i2) == Integer.MIN_VALUE) && (childAt = getChildAt(this.mCurrentPagePosition)) != null) {
                childAt.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
                i2 = MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), BasicMeasure.EXACTLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(i, i2);
    }

    public void reMeasureCurrentPage(int i) {
        this.mCurrentPagePosition = i;
        requestLayout();
    }
}
