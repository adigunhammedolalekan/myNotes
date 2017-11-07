package com.example.root.mynote.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.root.mynote.R;
import com.example.root.mynote.core.Api;
import com.example.root.mynote.core.Memory;
import com.example.root.mynote.entities.Note;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.ui.adapters.NoteListAdapter;
import com.example.root.mynote.util.L;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by root on 11/7/17.
 */

public class NoteListsFragment extends Fragment implements NoteListAdapter.INoteClickListener {

    @BindView(R.id.rv_notes_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout_notes_list)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.layout_no_notes)
    LinearLayout noNoteLayout;

    private User currentUser;
    private List<Note> noteList = new ArrayList<>();
    private NoteListAdapter noteListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_notes, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = Memory.memory().getUser();
        noteListAdapter = new NoteListAdapter(getActivity(), noteList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(noteListAdapter);

        getNotes();
    }

    void getNotes() {

        Api.get("/user/" + currentUser.getUserID() + "/notes", textHttpResponseHandler);

    }
    private TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {
        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {

            try {
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject nextNoteObject = data.getJSONObject(i);
                    Note note = new Note(nextNoteObject);
                    noteList.add(note);
                }
                if(noteList.size() > 0) {
                    noteListAdapter.notifyDataSetChanged();
                }else {
                    noNoteLayout.setVisibility(View.VISIBLE);
                }
            }catch (JSONException e) {
                L.error("", e);
            }
        }

        @Override
        public void onStart() {
            super.onStart();

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            swipeRefreshLayout.setRefreshing(false);
        }
    };
    @Override
    public void onClick(Note note) {
        L.fine(note.getNoteJSON());
    }
}
