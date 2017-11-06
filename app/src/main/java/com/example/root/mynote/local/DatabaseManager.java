package com.example.root.mynote.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.mynote.NoteApplication;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.util.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 11/5/17.
 */

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper databaseHelper;

    public static synchronized DatabaseManager getInstance() {

        if(instance == null) {
            instance = new DatabaseManager(NoteApplication.getInstance().getApplicationContext());
        }
        return instance;
    }
    private DatabaseManager(Context context) {
        databaseHelper =
                new DatabaseHelper(context);
    }
    private SQLiteDatabase getDatabase() {
        return databaseHelper.getWritableDatabase();
    }
    public long addUser(User user) {
        return getDatabase().insert(User.USER_TABLE_NAME, null, user.getContentValues());
    }
    public User getUser(String id) {

        String where = User.COLUMN_ID + " =? ";
        String args[] = {id};
        String columns[] = {User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL};

        Cursor cursor =
                getDatabase().query(User.USER_TABLE_NAME, columns, where, args,
                        null, null, null);
        if(cursor != null && cursor.moveToFirst()) {

            L.fine("Cursor Obtained " + cursor.getCount());

            User user = User.fromCursor(cursor);
            cursor.close();

            return user;
        }
        return null;
    }
    public List<User> users() {

        Cursor cursor = getDatabase().query(User.USER_TABLE_NAME,
                null, null, null, null, null, null);

        if(cursor == null)
            return new ArrayList<>();

        List<User> users = new ArrayList<>();
        cursor.moveToFirst();
        do {
            User user = User.fromCursor(cursor);
            users.add(user);
        }while (cursor.moveToNext());

        return users;
    }
}
