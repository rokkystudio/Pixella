package com.rokkystudio.pixella.palette;

public class HSB {
    public int hue; // 0-360
    public int saturation; // 0-100
    public int brightness; // 0-100

    public HSB() {
        this.hue = 0; this.saturation = 0; this.brightness = 0;
    }

    public HSB(int hue, int saturation, int brightness) {
        this.hue = hue; this.saturation = saturation; this.brightness = brightness;
    }

    public static HSB fromHex(String hex) {
        ARGB argb = ARGB.fromHex(hex);

        int cMax = Math.max(argb.r, Math.max(argb.g, argb.b));
        int cMin = Math.min(argb.r, Math.min(argb.g, argb.b));
        int delta = cMax - cMin;
        int saturation = (cMax != 0) ? (delta / cMax) : 0;

        HSB hsb = new HSB(0, 0, cMax);

        if (cMax == cMin) {
            hsb.brightness = cMax;
        } else if (cMax == argb.r) {
            hsb.hue = 60 * ((argb.g - argb.b) / delta) % 6;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        } else if (cMax == argb.g) {
            hsb.hue = 60 * ((argb.b - argb.r) / delta) + 2;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        } else if (cMax == argb.b) {
            hsb.hue = 60 * ((argb.r - argb.g) / delta) + 4;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        }

        return hsb;
    }
}
