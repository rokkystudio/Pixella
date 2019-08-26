package com.rokkystudio.pixella;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ToolbarButton extends AppCompatImageView
{
    private static final DrawFilter mDrawFilter = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG, 0);

    public ToolbarButton(Context context) {
        super(context);
    }

    public ToolbarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
    }
}
