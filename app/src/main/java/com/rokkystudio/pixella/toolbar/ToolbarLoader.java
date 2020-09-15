package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.rokkystudio.pixella.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ToolbarLoader
{
    private static final String XML_TOOLBAR = "Toolbar";
    private static final String XML_TOOL_GROUP = "ToolGroup";
    private static final String XML_TOOL = "Tool";
    private static final String XML_TOOL_NAME = "name";
    private static final String XML_TOOL_EVENT = "event";
    private static final String XML_TOOL_ICON = "icon";

    public static Toolbar getDefaultToolbar(Context context)
    {
        Toolbar toolbar = new Toolbar();

        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.toolbar);
            int eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG) {
                    if (XML_TOOLBAR.equals(parser.getName())) {
                        toolbar = parseToolbar(parser);
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return toolbar;
    }

    private static Toolbar parseToolbar(XmlPullParser parser)
        throws IOException, XmlPullParserException
    {
        Toolbar toolbar = new Toolbar();
        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG) {
                if (XML_TOOL_GROUP.equals(parser.getName())) {
                    toolbar.addGroup(parseGroup(parser));
                }
            }

            if (eventType == XmlPullParser.END_TAG) {
                if (XML_TOOLBAR.equals(parser.getName())) {
                    break;
                }
            }

            eventType = parser.next();
        }

        return toolbar;
    }

    private static ToolGroup parseGroup(XmlPullParser parser)
        throws IOException, XmlPullParserException
    {
        ToolGroup group = new ToolGroup();
        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_TAG) {
                if (XML_TOOL.equals(parser.getName())) {
                    group.addTool(parseTool(parser));
                }
            }

            if (eventType == XmlPullParser.END_TAG) {
                if (XML_TOOL_GROUP.equals(parser.getName())) {
                    break;
                }
            }

            eventType = parser.next();
        }

        return group;
    }

    private static Tool parseTool(XmlPullParser parser)
        throws IOException, XmlPullParserException
    {
        String name = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, XML_TOOL_NAME);
        String event = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, XML_TOOL_EVENT);
        String icon = parser.getAttributeValue(XmlPullParser.NO_NAMESPACE, XML_TOOL_ICON);

        Tool tool = new Tool(name, event);
        tool.setIcon(icon);
        return tool;
    }
}
