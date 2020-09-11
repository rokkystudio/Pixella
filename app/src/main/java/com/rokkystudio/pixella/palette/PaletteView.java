package com.rokkystudio.pixella.palette;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class PaletteView extends LinearLayout
{

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
        PaletteManager paletteManager = PaletteManager.getInstance();
        addView();
    }
}
