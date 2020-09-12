package com.rokkystudio.pixella.palette;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PaletteManager
{
    private int backgroundColor;
    private int foregroundColor;

    private List<Palette> mPalettes = new ArrayList<>();
    private Palette mCurrentPalette = new Palette("User Palette");

    public PaletteManager(Context context) {

    }

    private HSB HexToHSB(String hex) {
        hex = hex.replaceAll("/^#/", "");

        int red = Integer.parseInt(hex.substring(0, 2), 16) / 255;
        int green = Integer.parseInt(hex.substring(2, 4), 16) / 255;
        int blue = Integer.parseInt(hex.substring(4, 6), 16) / 255;

        int cMax = Math.max(red, Math.max(green, blue));
        int cMin = Math.min(red, Math.min(green, blue));
        int delta = cMax - cMin;
        int saturation = (cMax != 0) ? (delta / cMax) : 0;

        HSB hsb = new HSB(0, 0, cMax);

        if (cMax == cMin) {
            hsb.brightness = cMax;
        } else if (cMax == red) {
            hsb.hue = 60 * ((green - blue) / delta) % 6;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        } else if (cMax == green) {
            hsb.hue = 60 * ((blue - red) / delta) + 2;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        } else if (cMax == blue) {
            hsb.hue = 60 * ((red - green) / delta) + 4;
            hsb.saturation = saturation;
            hsb.brightness = cMax;
        }
        return hsb;
    }


    {
        colors = colors.sort(function(a, b) {
        var hsva = HexToHSB(a);
        var hsvb = HexToHSB(b);
        return hsva[0] - hsvb[0];
    });
        div = document.createElement("div");
        document.body.appendChild(div);
        colors.forEach(function(element) {
        var d = document.createElement("button");
        d.style.cssText = 'padding:5px; font-size:22px; width:50px; height:50px; background-color:' + element;
        div.appendChild(d);
    });
    }
}
