package com.rokkystudio.pixella.palette;

public class Color
{
    private String mName = "";
    private String mHex = "#00000000";

    public Color() {}

    public Color(String hex) {
        mHex = hex;
    }

    public Color(String name, String hex) {
        setName(name);
        mHex = hex;
    }

    public Color(int color) {
        setIntColor(color);
    }

    public Color(String name, int color) {
        setName(name);
        setIntColor(color);
    }

    public Color(int r, int g, int b) {
        setIntColor(0xFF000000 | (r << 16) | (g << 8) | b);
    }

    public Color(int a, int r, int g, int b) {
        setIntColor((a << 24) | (r << 16) | (g << 8) | b);
    }

    public void setName(String name) {
        mName = name;
    }

    public void setHexColor(String hex) {
        mHex = hex;
    }

    public void setIntColor(int color) {
        mHex = String.format("#%06X", (0xFFFFFF & color));
    }

    public String getName() {
        return mName;
    }

    public int getIntColor() {
        long color = Long.parseLong(mHex.substring(1), 16);
        if (mHex.length() == 7) {
            color |= 0x00000000FF000000;
        }
        return (int) color;
    }

    public String getHexColor() {
        return mHex;
    }

    public ARGB getARGBColor() {
        return ARGB.fromHex(mHex);
    }

    public HSB getHSBColor() {
        return HSB.fromHex(mHex);
    }
}
