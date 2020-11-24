package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ToolbarView extends LinearLayout implements
    View.OnClickListener, View.OnLongClickListener
{
    private Toolbar mToolbar;

    public ToolbarView(Context context) {
        super(context);
        init();
    }

    public ToolbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    public void setToolbar(Toolbar toolbar)
    {
        mToolbar = toolbar;
        removeAllViews();

        for (int i = 0; i < mToolbar.getCount(); i++)
        {
            String name = mToolbar.getGroup(i).getDefaultTool();
            ToolView toolView = new ToolView(getContext());
            toolView.setTool(name);
            toolView.setOnClickListener(this);
            toolView.setOnLongClickListener(this);
            addView(toolView);
        }

        requestLayout();
        invalidate();
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ToolView) {
            ToolView toolView = (ToolView) view;
            toolView.getTool();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view instanceof ToolView) {
            ToolView toolView = (ToolView) view;
            toolView.getTool();
        }
        return false;
    }
}
