package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class Tool extends AppCompatImageView
{
    private static final DrawFilter mDrawFilter = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG, 0);

    public Tool(Context context) {
        super(context);
    }

    public Tool(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Tool(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
    }
}
