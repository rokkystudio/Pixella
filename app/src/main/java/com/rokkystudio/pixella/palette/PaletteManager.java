package com.rokkystudio.pixella.palette;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PaletteManager
{
    private List<Palette> mPalettes = new ArrayList<>();

    public PaletteManager(Context context) {
        mPalettes.add(mUserPalette);
        mPalettes.addAll(PaletteLoader.getStoragePalettes(context));
        mPalettes.addAll(PaletteLoader.getDefaultPalettes(context));
    }

    public Palette getUserPalette() {
        return mUserPalette;
    }


}
