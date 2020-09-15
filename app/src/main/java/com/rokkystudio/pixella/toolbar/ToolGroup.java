package com.rokkystudio.pixella.toolbar;

import java.util.ArrayList;
import java.util.List;

public class ToolGroup
{
    private List<Tool> mTools = new ArrayList<>();
    private Tool mDefaultTool = null;

    public void addTool(Tool tool) {
        if (mTools.isEmpty()) {
            setDefaultTool(tool);
        }
        mTools.add(tool);
    }

    public void removeTool(Tool tool)
    {
        mTools.remove(tool);

        if (tool.equals(mDefaultTool)) {
            mDefaultTool = null;
        }

        if (!mTools.isEmpty()) {
            mDefaultTool = mTools.get(0);
        }
    }

    public void setDefaultTool(Tool tool) {
        mDefaultTool = tool;
    }

    public Tool getDefaultTool() {
        return mDefaultTool;
    }

    public int getCount() {
        return mTools.size();
    }

    public Tool getTool(int index) {
        return mTools.get(index);
    }
}
