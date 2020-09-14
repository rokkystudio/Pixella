package com.rokkystudio.pixella.palette;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.rokkystudio.pixella.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaletteLoader
{
    private static final String FILE_PALETTES = "palettes.xml";

    private static final String XML_PALETTE = "palette";
    private static final String XML_PALETTE_NAME = "name";

    private static final String XML_COLOR = "color";
    private static final String XML_COLOR_VALUE = "value";

    public static List<Palette> getDefaultPalettes(Context context)
    {
        Resources resources = context.getResources();
        List<Palette> palettes = new ArrayList<>();
        XmlResourceParser parser = resources.getXml(R.xml.palettes);

        try {
            palettes = parseDocument(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palettes;
    }

    public static List<Palette> getStoragePalettes(Context context)
    {
        File xmlFile = new File(context.getFilesDir(), FILE_PALETTES);
        List<Palette> palettes = new ArrayList<>();
        if (!xmlFile.exists()) return palettes;

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new FileInputStream(xmlFile), null);
            palettes = parseDocument(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return palettes;
    }

    private static List<Palette> parseDocument(XmlPullParser parser)
        throws XmlPullParserException, IOException
    {
        List<Palette> palettes = new ArrayList<>();
        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG) {
                if (XML_PALETTE.equals(parser.getName())) {
                    palettes.add(parsePalette(parser));
                }
            }
            eventType = parser.next();
        }
        return palettes;
    }

    private static Palette parsePalette(XmlPullParser parser)
        throws XmlPullParserException, IOException
    {
        String name = parser.getAttributeValue(null , XML_PALETTE_NAME);
        Palette palette = new Palette(name);
        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG) {
                if (XML_COLOR.equals(parser.getName())) {
                    String value = parser.getAttributeValue(null, XML_COLOR_VALUE);
                    if (value != null) {
                        palette.addColor(new Color(value));
                    }
                }
            }
            eventType = parser.next();
        }
        return palette;
    }
}
