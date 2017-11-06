package com.example.root.mynote.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.root.mynote.R;
import com.example.root.mynote.core.Memory;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.local.DatabaseManager;
import com.example.root.mynote.util.L;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_welcome)
    TextView welcomeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = getIntent();

        String userID = intent.getStringExtra("user_id");
        User user = DatabaseManager.getInstance().getUser(userID);*/

        User user = Memory.memory().getUser();
        welcomeTextView.setText("Welcome, " + "\n " + user.getUserID() + "\n" + user.getUsername() + " \n " + user.getEmail());

        L.fine("Total Users ==> " + DatabaseManager.getInstance().users().size());
    }
}
