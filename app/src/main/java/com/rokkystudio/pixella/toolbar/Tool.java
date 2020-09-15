package com.rokkystudio.pixella.toolbar;

public class Tool
{
    private String mName;
    private String mEvent;
    private String mIcon;

    public Tool(String name, String event) {
        mName = name; mEvent = event;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setEvent(String event) {
        mEvent = event;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public String getEvent() {
        return mEvent;
    }

    public String getIcon() {
        return mIcon;
    }
}
