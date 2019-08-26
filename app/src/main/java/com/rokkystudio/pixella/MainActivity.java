package com.rokkystudio.pixella;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        WorkspaceFragment.WorkspaceListener
{
    private Bitmap mBitmap = null;
    private History mHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment, new WorkspaceFragment())
            .commit();
    }

    @Override
    public void onSettingsClick() {

    }
}
