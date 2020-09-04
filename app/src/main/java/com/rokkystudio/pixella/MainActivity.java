package com.rokkystudio.pixella;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        WorkspaceFragment.WorkspaceListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment, new WorkspaceFragment())
            .commit();
    }

    @Override
    public void onSettingsClick() {

    }
}
