package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ToolView extends androidx.appcompat.widget.AppCompatImageView
{
    private String mToolName = null;

    public ToolView(Context context) {
        super(context);
        init();
    }

    public ToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding
    }

    public void setTool(String name) {
        mToolName = name;
        Resources resources = getContext().getResources();
        String packageName = getContext().getPackageName();
        setImageResource(resources.getIdentifier(name.toLowerCase(), "drawable", packageName));
    }

    public String getTool() {
        return mToolName;
    }
}
