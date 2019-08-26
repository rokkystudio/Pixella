package com.rokkystudio.pixella;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorkspaceFragment extends Fragment {

    private WorkspaceListener mListener;

    public WorkspaceFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workspace, container, false);
    }

    @Override
    public void onAttach(Context context) {
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
