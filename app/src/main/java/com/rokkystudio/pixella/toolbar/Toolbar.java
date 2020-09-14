package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.rokkystudio.pixella.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Toolbar extends LinearLayout
{
    public Toolbar(Context context) {
        super(context);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setDefaultToolbarLayout();
    }

    private void setDefaultToolbarLayout() {
        try {
            XmlResourceParser parser = getResources().getXml(R.xml.toolbar);
            int eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.getName() )
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void addTool(ToolView toolView) {
        ToolView view = new ToolView(getContext());
        childBox.setAdjustViewBounds(true);
        addView(childBox);
    }
}
