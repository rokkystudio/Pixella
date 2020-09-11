package com.rokkystudio.pixella.palette;

import java.util.ArrayList;
import java.util.List;

public class Palette
{
    private String mName;
    private List<Integer> mColors = new ArrayList<>();

    public Palette(String name) {
        mName = name;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void addColor(int color) {
        mColors.add(color);
    }

    public int getColor(int index) {
        if (index < 0 || index >= mColors.size()) return 0;
        return mColors.get(index);
    }

    public void setColor(int index, int color) {
        if (index < 0 || index >= mColors.size()) return;
        mColors.set(index, color);
    }

    public void removeColor(int index) {
        if (index < 0 || index >= mColors.size()) return;
        mColors.remove(index);
    }

    public int getCount() {
        return mColors.size();
    }
}
