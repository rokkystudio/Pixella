package com.rokkystudio.pixella;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ToolbarButton extends AppCompatImageView
{
    private Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
    private Bitmap resized;

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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int size = Math.min(w, h) - (int) getResources().getDimension(R.dimen.toolbar_padding) * 2;
        ((BitmapDrawable) getDrawable()).setAntiAlias(false);
        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        getDrawable().
        resized = Bitmap.createScaledBitmap(bitmap, size, size, false);
        //resized = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        //Matrix matrix = new Matrix();
        //matrix.postScale(5, 5);
        //resized = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        paint.setAntiAlias(false);
        paint.setDither(true);
        paint.setFilterBitmap(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.scale(5, 5);
        canvas.drawBitmap(resized, 0,0, paint);
    }
}
