package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PickColor extends ToolView implements
    View.OnClickListener, View.OnLongClickListener
{
    public PickColor(Context context) {
        super(context);
        init();
    }

    public PickColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PickColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Switch background and foreground color
        Settings.switchColor(getContext());
    }

    @Override
    public boolean onLongClick(View v) {
        // Show select color dialog
        return false;
    }
}
