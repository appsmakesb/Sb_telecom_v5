package com.v5foradnroid.userapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;


public class SeperatedProgressbar extends Drawable {
    private static final int NUM_SEGMENTS = 6;
    Context context;
    private final Paint mPaint = new Paint();
    private final RectF mSegment = new RectF();

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public SeperatedProgressbar(Context context2) {
        this.context = context2;
    }

    /* Foysal Tech && ict Foysal */
    public boolean onLevelChange(int i) {
        invalidateSelf();
        return true;
    }

    public void draw(Canvas canvas) {
        float level = ((float) getLevel()) / 10000.0f;
        Rect bounds = getBounds();
        float height = ((float) bounds.height()) / 2.0f;
        float width = (((float) bounds.width()) - (5.0f * height)) / 6.0f;
        this.mSegment.set(0.0f, 0.0f, width, (float) bounds.height());
        this.mPaint.setColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
        int i = 0;
        while (i < 6) {
            float f = ((float) i) / 6.0f;
            i++;
            float f2 = ((float) i) / 6.0f;
            if (f > level || level > f2) {
                canvas.drawRect(this.mSegment, this.mPaint);
            } else {
                float f3 = this.mSegment.left + (width * 6.0f * (level - f));
                canvas.drawRect(this.mSegment.left, this.mSegment.top, f3, this.mSegment.bottom, this.mPaint);
                this.mPaint.setColor(ContextCompat.getColor(this.context, R.color.Lavender));
                canvas.drawRect(f3, this.mSegment.top, this.mSegment.right, this.mSegment.bottom, this.mPaint);
                Canvas canvas2 = canvas;
            }
            RectF rectF = this.mSegment;
            rectF.offset(rectF.width() + height, 0.0f);
        }
    }
}
