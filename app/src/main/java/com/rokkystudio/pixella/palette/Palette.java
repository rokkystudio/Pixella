package com.rokkystudio.pixella.palette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Palette
{
    private String mName;
    private List<Color> mColors = new ArrayList<>();

    public Palette(String name) {
        mName = name;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void addColor(Color color) {
        mColors.add(color);
    }

    public Color getColor(int index) {
        if (index < 0 || index >= mColors.size()) return new Color();
        return mColors.get(index);
    }

    public void setColor(int index, Color color) {
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

    private void sortByHue() {
        Collections.sort(mColors, new HueCmp());
    }

    private static class HueCmp implements Comparator<Color> {
        @Override
        public int compare(Color a, Color b) {
            return a.getHSBColor().hue - b.getHSBColor().hue;
        }
    }
}
