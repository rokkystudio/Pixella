package com.rokkystudio.pixella.palette;

import static java.lang.Integer.*;

public class ARGB
{
    public int r = 0; // 0-255
    public int g = 0; // 0-255
    public int b = 0; // 0-255
    public int a = 0; // 0-255

    public ARGB() {}

    public ARGB(int r, int g, int b) {
        a = 255; this.r = r; this.g = g; this.b = b;
    }

    public ARGB(int a, int r, int g, int b) {
        this.a = a; this.r = r; this.g = g; this.b = b;
    }

    public static ARGB fromHex(String hex) {
        hex = hex.replace("#", "");
        ARGB argb = new ARGB();

        if (hex.length() == 6) {
            argb.r = parseInt(hex.substring(0, 2), 16) / 255;
            argb.g = parseInt(hex.substring(2, 4), 16) / 255;
            argb.b = parseInt(hex.substring(4, 6), 16) / 255;
        }

        if (hex.length() == 8) {
            argb.a = parseInt(hex.substring(0, 2), 16) / 255;
            argb.r = parseInt(hex.substring(2, 4), 16) / 255;
            argb.g = parseInt(hex.substring(4, 6), 16) / 255;
            argb.b = parseInt(hex.substring(6, 8), 16) / 255;
        }

        return argb;
    }
}