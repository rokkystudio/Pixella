package com.rokkystudio.pixella.palette;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.rokkystudio.pixella.R;

public class PaletteView extends View
{
    private Palette mPalette = new Palette("Empty");
    private int mRows = 3; // Default palette rows

    private int mCellWidth;
    private int mCellHeight;

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
        mCellWidth = (int) getResources().getDimension(R.dimen.palette_cell_width);
        mCellHeight = (int) getResources().getDimension(R.dimen.palette_cell_height);
    }

    public void setPalette(Palette palette) {
        mPalette = palette;
    }

    public void setRows(int rows) {
        if (rows < 1) return;
        mRows = rows;
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

    public void setCellWidth(int width) {
        mCellWidth = width;
    }

    public void setCellHeight(int height) {
        mCellHeight = height;
    }

    public void setOnClickListener() {

    }

    public void setOnLongClickListener() {

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getColumns() * mCellWidth , getRows() * mCellHeight);
    }

    protected void onDraw(Canvas canvas) {

    }
}
