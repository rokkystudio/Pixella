package com.rokkystudio.pixella.toolbar;

import java.util.ArrayList;
import java.util.List;

public class Toolbar
{
    private List<ToolGroup> mToolGroups = new ArrayList<>();

    public Toolbar() {}

    public void addGroup(ToolGroup group) {
        mToolGroups.add(group);
    }

    public ToolGroup getGroup(int index) {
        return mToolGroups.get(index);
    }

    public int getCount() {
        return mToolGroups.size();
    }
}
