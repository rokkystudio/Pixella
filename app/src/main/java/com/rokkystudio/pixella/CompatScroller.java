package com.rokkystudio.pixella;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.OverScroller;
import android.widget.Scroller;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CompatScroller
{
    Scroller scroller;
    OverScroller overScroller;
    boolean isPreGingerbread;

    @SuppressLint("ObsoleteSdkInt")
    CompatScroller(Context context)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            isPreGingerbread = true;
            scroller = new Scroller(context);

        } else {
            isPreGingerbread = false;
            overScroller = new OverScroller(context);
        }
    }

    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        if (isPreGingerbread) {
            scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        } else {
            overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        }
    }

    void forceFinished(boolean finished) {
        if (isPreGingerbread) {
            scroller.forceFinished(finished);
        } else {
            overScroller.forceFinished(finished);
        }
    }

    boolean isFinished() {
        if (isPreGingerbread) {
            return scroller.isFinished();
        } else {
            return overScroller.isFinished();
        }
    }

    boolean computeScrollOffset() {
        if (isPreGingerbread) {
            return scroller.computeScrollOffset();
        } else {
            overScroller.computeScrollOffset();
            return overScroller.computeScrollOffset();
        }
    }

    int getCurrX() {
        if (isPreGingerbread) {
            return scroller.getCurrX();
        } else {
            return overScroller.getCurrX();
        }
    }

    int getCurrY() {
        if (isPreGingerbread) {
            return scroller.getCurrY();
        } else {
            return overScroller.getCurrY();
        }
    }
}