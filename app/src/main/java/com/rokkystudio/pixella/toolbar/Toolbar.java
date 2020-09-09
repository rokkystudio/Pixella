package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class Toolbar extends LinearLayout
{
    public Toolbar(Context context) {
        super(context);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);

        // Add Pick color
        // Add Eraser
        // Add Pen
        // Add Paint fill
        // Add Undo
    }

    public void addToolView() {
        ToolView view = new ToolView(getContext());
        childBox.setAdjustViewBounds(true);
        addView(childBox);
    }
}
