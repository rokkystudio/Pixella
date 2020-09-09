package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ToolGroup extends View
{
    private List<Tool> mTools = new ArrayList<>();
    private Tool mDefaultTool = null;

    public ToolGroup(Context context) {
        super(context);
    }

    public ToolGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTool(Tool tool) {
        if (mTools.isEmpty()) {
            setDefaultTool(tool);
        }
        mTools.add(tool);
    }

    public void setDefaultTool(Tool tool) {
        mDefaultTool = tool;
    }

    public Tool getDefaultTool() {
        return mDefaultTool;
    }

    public List<Tool> getTools() {
        return mTools;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mDefaultTool != null) {

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
