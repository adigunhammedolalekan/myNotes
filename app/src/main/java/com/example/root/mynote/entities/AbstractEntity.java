package com.example.root.mynote.entities;

import android.content.ContentValues;

/**
 * Created by root on 11/5/17.
 */

public abstract class AbstractEntity {

    abstract ContentValues getContentValues();

    @Override
    public String toString() {
        return "This is an abs class";
    }
}
