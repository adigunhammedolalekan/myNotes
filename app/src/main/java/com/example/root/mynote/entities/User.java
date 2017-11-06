package com.example.root.mynote.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 11/5/17.
 */

public class User extends AbstractEntity {

    private String userID = "";
    private String username = "";
    private String email = "";
    private String json = "";

    public static final String USER_TABLE_NAME = "users";
    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";

    public static final String CREATE_TABLE_STATEMENT =
            "create table " + USER_TABLE_NAME + " ( " + COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_USERNAME + " text, " +
                    COLUMN_EMAIL + " text);";

    public User() {

    }

    public User(String name, String email) {
        this("0", name, email);
    }
    public User(String id, String name, String mail) {
        userID = id;
        username = name;
        email = mail;
    }
    public User(JSONObject jsonObject) throws JSONException {

        userID = jsonObject.getString("user_id");
        username = jsonObject.getString("username");
        email = jsonObject.getString("email");

        json = jsonObject.toString();
    }
    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);

        return contentValues;
    }
    public static User fromCursor(Cursor cursor) {
        return new User(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public String getJson() {
        return json;
    }
}
