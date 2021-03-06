package com.rokkystudio.pixella;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rokkystudio.pixella.palette.Palette;
import com.rokkystudio.pixella.palette.PaletteManager;
import com.rokkystudio.pixella.palette.PaletteView;
import com.rokkystudio.pixella.toolbar.ToolbarLoader;
import com.rokkystudio.pixella.toolbar.ToolbarView;

public class WorkspaceFragment extends Fragment
{
    private WorkspaceListener mListener;
    private View mRootView = null;

    private PaletteManager mPaletteManager;
    private Palette mUserPalette = new Palette("User Palette");

    public WorkspaceFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPaletteManager = new PaletteManager(getContext());
        mUserPalette = mPaletteManager.getPalette(0);
        mUserPalette.sortByHue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mRootView != null) return mRootView;
        mRootView = inflater.inflate(R.layout.workspace, container, false);

        PaletteView paletteView = mRootView.findViewById(R.id.PaletteView);
        paletteView.setPalette(mUserPalette);

        ToolbarView mainToolbarView = mRootView.findViewById(R.id.MainToolbar);
        mainToolbarView.setToolbar(ToolbarLoader.getDefaultToolbar(getContext()));

        ToolbarView bottomToolbarView = mRootView.findViewById(R.id.BottomToolbar);
        bottomToolbarView.setToolbar(ToolbarLoader.getDefaultToolbar(getContext()));

        return mRootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WorkspaceListener) {
            mListener = (WorkspaceListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement WorkspaceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface WorkspaceListener {
        void onSettingsClick();
    }
}
