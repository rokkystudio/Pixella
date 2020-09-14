package com.rokkystudio.pixella.palette;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PaletteManager
{
    private int backgroundColor;
    private int foregroundColor;

    private List<Palette> mPalettes = new ArrayList<>();
    private Palette mUserPalette = new Palette("User Palette");

    public PaletteManager(Context context) {
        mPalettes.add(mUserPalette);
        mPalettes.addAll(PaletteLoader.getStoragePalettes(context));
        mPalettes.addAll(PaletteLoader.getDefaultPalettes(context));
    }
}
