package com.example.root.mynote.core;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.root.mynote.NoteApplication;
import com.example.root.mynote.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 11/6/17.
 */

public class Memory {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static Memory memory;
    private Memory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NoteApplication.getInstance().getApplicationContext());
        editor = sharedPreferences.edit();
    }
    public static synchronized Memory memory() {
        if(memory == null)
            memory = new Memory();
        return memory;
    }
    public void putUser(User user) {
        editor.putString("user", user.getJson()).apply();
    }
    public User getUser() {

        try {
            return new User(new JSONObject(sharedPreferences.getString("user", "")));
        }catch (JSONException e) {
        }
        return null;
    }
    public boolean isLoggedIn() {
        return getUser() != null;
    }
}
