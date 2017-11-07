package com.example.root.mynote.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.example.root.mynote.R;
import com.example.root.mynote.ui.fragments.NoteListsFragment;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main, new NoteListsFragment(), NoteListsFragment.class.getSimpleName())
                .commit();
    }
    @OnClick(R.id.fab_create_note) public void createNoteClick() {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
    }
}
