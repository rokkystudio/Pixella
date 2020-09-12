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
}
