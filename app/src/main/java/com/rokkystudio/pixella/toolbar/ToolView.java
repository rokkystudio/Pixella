package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ToolView extends androidx.appcompat.widget.AppCompatImageView
{
    private Tool mTool = null;

    public ToolView(Context context) {
        super(context);
    }

    public ToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTool(Tool tool) {
        mTool = tool;
    }

    public Tool getTool() {
        return mTool;
    }
}
