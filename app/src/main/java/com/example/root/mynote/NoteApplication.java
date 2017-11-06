package com.example.root.mynote;

import android.app.Application;

/**
 * Created by root on 11/5/17.
 */

public class NoteApplication extends Application {

    static NoteApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static NoteApplication getInstance() {
        return instance;
    }
}
