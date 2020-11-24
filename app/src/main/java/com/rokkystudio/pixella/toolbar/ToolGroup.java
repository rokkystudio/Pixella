package com.rokkystudio.pixella.toolbar;

import java.util.ArrayList;
import java.util.List;

public class ToolGroup
{
    private final List<String> mToolNames = new ArrayList<>();
    private String mDefaultTool = null;

    public void addTool(String name) {
        if (mToolNames.isEmpty()) {
            setDefaultTool(name);
        }
        mToolNames.add(name);
    }

    public void removeTool(String name)
    {
        mToolNames.remove(name);

        if (name.equals(mDefaultTool)) {
            mDefaultTool = null;
        }

        if (!mToolNames.isEmpty()) {
            mDefaultTool = mToolNames.get(0);
        }
    }

    public void setDefaultTool(String name) {
        mDefaultTool = name;
    }

    public String getDefaultTool() {
        return mDefaultTool;
    }

    public int getCount() {
        return mToolNames.size();
    }

    public String getTool(int index) {
        return mToolNames.get(index);
    }
}
