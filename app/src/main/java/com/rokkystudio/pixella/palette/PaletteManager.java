package com.rokkystudio.pixella.palette;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PaletteManager
{
    private List<Palette> mPalettes = new ArrayList<>();

    public PaletteManager(Context context) {
        mPalettes.addAll(PaletteLoader.getStoragePalettes(context));
        mPalettes.addAll(PaletteLoader.getDefaultPalettes(context));
    }

    public Palette getPalette(int index) {
        if (index < 0 || index >= mPalettes.size()) return null;
        return mPalettes.get(index);
    }

    public int getPalettesCount() {
        return mPalettes.size();
    }
}
