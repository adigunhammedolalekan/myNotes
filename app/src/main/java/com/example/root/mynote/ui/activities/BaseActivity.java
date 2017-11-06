package com.example.root.mynote.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by root on 11/5/17.
 */

public class BaseActivity extends AppCompatActivity {

    private volatile boolean isOn = false;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        ButterKnife.bind(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);
    }

    public void toast(String message) {

        if(!isOn)
            return;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOn = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOn = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOn = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isOn = true;
    }
}
