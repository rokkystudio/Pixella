package com.rokkystudio.pixella.palette;

import static java.lang.Integer.*;

public class RGB
{
    private int mRed   = 0;
    private int mGreen = 0;
    private int mBlue  = 0;
    private int mAlpha = 0;

    public RGB() {}

    public RGB(int r, int g, int b) {
        mRed = r; mGreen = g; mBlue = b; mAlpha = 255;
    }

    public RGB(int a, int r, int g, int b) {
        mRed = r; mGreen = g; mBlue = b; mAlpha = a;
    }

    public int getRedInt() {
        return mRed;
    }

    public int getGreenInt() {
        return mGreen;
    }

    public int getBlueInt() {
        return mBlue;
    }

    public float getRedFloat() {
        return mRed / 255f;
    }

    public float getGreenFloat() {
        return mGreen / 255f;
    }

    public float getBlueFloat() {
        return mBlue / 255f;
    }

    public static RGB fromHex(String hex)
    {
        hex = hex.replace("#", "");
        RGB rgb = new RGB();

        if (hex.length() == 6) {
            rgb = new RGB(
                parseInt(hex.substring(0, 2), 16),
                parseInt(hex.substring(2, 4), 16),
                parseInt(hex.substring(4, 6), 16)
            );
        }

        if (hex.length() == 8) {
            rgb = new RGB(
                parseInt(hex.substring(0, 2), 16),
                parseInt(hex.substring(2, 4), 16),
                parseInt(hex.substring(4, 6), 16),
                parseInt(hex.substring(6, 8), 16)
            );
        }

        return rgb;
    }
}