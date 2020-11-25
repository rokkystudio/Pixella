package com.rokkystudio.pixella.toolbar;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.rokkystudio.pixella.R;

public class ToolView extends androidx.appcompat.widget.AppCompatImageView
{
    private String mToolName = null;

    public ToolView(Context context) {
        super(context);
        init();
    }

    public ToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final int padding = (int) getResources().getDimension(R.dimen.toolbar_button_padding);
        setPadding(padding, padding, padding, padding);
        final int size = (int) getResources().getDimension(R.dimen.toolbar_button_size);
        setLayoutParams(new ViewGroup.LayoutParams(size, size));
    }

    public void setTool(String name) {
        mToolName = name;
        Resources resources = getContext().getResources();
        String packageName = getContext().getPackageName();
        setImageResource(resources.getIdentifier(name.toLowerCase(), "drawable", packageName));
    }

    public String getTool() {
        return mToolName;
    }
}
