package com.example.root.mynote.entities;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 11/7/17.
 */

public class Note extends AbstractEntity {

    private String noteID = "";
    private String title = "";
    private String content = "";
    private User user;
    private String timeAdded = "";

    private String noteJSON = "";
    public Note() {}

    public Note(JSONObject jsonObject) throws JSONException {

        noteID = jsonObject.getString("note_id");
        title = jsonObject.getString("title");
        content = jsonObject.getString("note_content");
        user = new User(jsonObject.getJSONObject("user"));
        timeAdded = jsonObject.getString("time_created");

        noteJSON = jsonObject.toString();
    }
    @Override
    ContentValues getContentValues() {
        return null;
    }

    public String getContent() {
        return content;
    }

    public String getNoteID() {
        return noteID;
    }

    public String getNoteJSON() {
        return noteJSON;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public String getTimeAdded() {
        return timeAdded;
    }
}
