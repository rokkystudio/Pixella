package com.rokkystudio.pixella;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferences
{
    public static final String PIXELLA_PREFERENCES = "Pixella Preferences";

    public static void switchColor(Context context) {

    }

    public static void setKey(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PIXELLA_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getKey(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(PIXELLA_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(name, "");
    }
}
