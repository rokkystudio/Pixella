package com.rokkystudio.pixella.palette;

import static java.lang.Integer.parseInt;

public class HSB
{
    private float mHue; // 0-360
    private float mSaturation; // 0-100
    private float mBrightness; // 0-100

    public HSB() {
        mHue = 0; mSaturation = 0; mBrightness = 0;
    }

    public HSB(float hue, float saturation, float brightness) {
        mHue = hue; mSaturation = saturation; mBrightness = brightness;
    }

    public void setHue(float hue) {
        mHue = hue;
    }

    public int getHue() {
        return (int) mHue;
    }

    public void setSaturation(float saturation) {
        mSaturation = saturation;
    }

    public int getSaturation() {
        return (int) mSaturation;
    }

    public void setBrightness(float brightness) {
        mBrightness = brightness;
    }

    public int getBrightness() {
        return (int) mBrightness;
    }

    public static HSB fromHex(String hex)
    {
        hex = hex.replace("#", "");

        float red = 0;
        float green = 0;
        float blue = 0;

        if (hex.length() == 6) {
            red = parseInt(hex.substring(0, 2), 16) / 255f;
            green = parseInt(hex.substring(2, 4), 16) / 255f;
            blue = parseInt(hex.substring(4, 6), 16) / 255f;
        } else if (hex.length() == 8) {
            red = parseInt(hex.substring(2, 4), 16) / 255f;
            green = parseInt(hex.substring(4, 6), 16) / 255f;
            blue = parseInt(hex.substring(6, 8), 16) / 255f;
        }

        float cMax = Math.max(red, Math.max(green, blue));
        float cMin = Math.min(red, Math.min(green, blue));
        float delta = cMax - cMin;
        float saturation = (cMax != 0) ? (delta / cMax) : 0;

        HSB hsb = new HSB(0, 0, cMax);

        if (cMax == cMin) {
            hsb.setBrightness(cMax);
        } else if (cMax == red) {
            hsb.setHue(60 * (((green - blue) / delta) % 6));
            hsb.setSaturation(saturation);
            hsb.setBrightness(cMax);
        } else if (cMax == green) {
            hsb.setHue(60 * (((blue - red) / delta) + 2));
            hsb.setSaturation(saturation);
            hsb.setBrightness(cMax);
        } else if (cMax == blue) {
            hsb.setHue(60 * (((red - green) / delta) + 4));
            hsb.setSaturation(saturation);
            hsb.setBrightness(cMax);
        }

        return hsb;
    }
}
