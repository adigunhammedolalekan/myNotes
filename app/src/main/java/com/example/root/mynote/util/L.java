package com.example.root.mynote.util;

import android.util.Log;

/**
 * Created by root on 11/5/17.
 */

public class L {

    public static final String TAG = "MyNote";

    public static void fine(String message) {
        Log.d(TAG, message);
    }
    public static void error(String data, Throwable throwable) {
        Log.wtf(TAG, data + "", throwable);
    }
}
