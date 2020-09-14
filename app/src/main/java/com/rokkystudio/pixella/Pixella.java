package com.rokkystudio.pixella;

import com.rokkystudio.pixella.palette.Color;

public class Pixella
{
    private Color mBackgroundColor;
    private Color mForegroundColor;

    public void setBackgroundColor(Color backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        mForegroundColor = foregroundColor;
    }

    public Color getForegroundColor() {
        return mForegroundColor;
    }
}
