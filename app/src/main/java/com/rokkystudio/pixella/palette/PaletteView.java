package com.rokkystudio.pixella.palette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.rokkystudio.pixella.R;

import java.util.ArrayList;
import java.util.List;

public class PaletteView extends View
{
    private Palette mPalette;
    private List<Rect> mRects;

    // Default palette rows
    private int mRows = 3;

    private int mCellWidth;
    private int mCellHeight;

    private Paint mPaint;

    public PaletteView(Context context) {
        super(context);
        init();
    }

    public PaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaletteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        mPalette = new Palette("Empty");
        mRects = new ArrayList<>();

        mCellWidth = (int) getResources().getDimension(R.dimen.palette_cell_width);
        mCellHeight = (int) getResources().getDimension(R.dimen.palette_cell_height);
        mPaint = new Paint();
    }

    private void calculateRects() {
        mRects.clear();
        for (int i = 0; i < mPalette.getCount(); i++) {
            int x = (i / mRows) * mCellWidth;
            int y = (i % mRows) * mCellHeight;
            mRects.add(new Rect(x, y, x + mCellWidth, y + mCellHeight));
        }
    }

    public void setPalette(Palette palette) {
        mPalette = palette;
        calculateRects();
        requestLayout();
        invalidate();
    }

    public void setRows(int rows) {
        if (rows < 1) return;
        mRows = rows;
        calculateRects();
        requestLayout();
        invalidate();
    }

    public void setCellWidth(int width) {
        mCellWidth = width;
        calculateRects();
        requestLayout();
        invalidate();
    }

    public void setCellHeight(int height) {
        mCellHeight = height;
        calculateRects();
        requestLayout();
        invalidate();
    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        if (mPalette.getCount() == 0) return 0;
        return (mPalette.getCount() - 1) / mRows + 1;
    }


    public void setOnClickListener() {

    }

    public void setOnLongClickListener() {

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getColumns() * mCellWidth , getRows() * mCellHeight);
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        for (int i = 0; i < mPalette.getCount(); i++)
        {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPalette.getColor(i).getIntColor());
            canvas.drawRect(mRects.get(i), mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.parseColor("#999999"));
            canvas.drawRect(mRects.get(i), mPaint);
        }
    }
}
