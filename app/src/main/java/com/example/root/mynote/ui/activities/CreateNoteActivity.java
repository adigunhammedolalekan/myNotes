package com.example.root.mynote.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.root.mynote.R;
import com.example.root.mynote.core.Api;
import com.example.root.mynote.core.Memory;
import com.example.root.mynote.entities.Note;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.util.L;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by root on 11/7/17.
 */

public class CreateNoteActivity extends BaseActivity {

    @BindView(R.id.edt_note_title)
    EditText titleEditText;
    @BindView(R.id.edt_note_content)
    EditText contentEditText;

    private ProgressDialog progressDialog;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_note);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Create Note");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("creating note...");

        currentUser = Memory.memory().getUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_note:
                createNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {

        if (textOf(titleEditText).isEmpty()) {
            toast("Enter title");
            return;
        }
        if(textOf(contentEditText).isEmpty()) {
            toast("Enter content");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("title", textOf(titleEditText));
        requestParams.put("content", textOf(contentEditText));
        requestParams.put("user_id", currentUser.getUserID());
        Api.post("/note", requestParams, createHandler());
    }
    private TextHttpResponseHandler createHandler() {
        return new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                L.error(responseString, throwable);

                toast("Error occurred.");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONObject note = jsonObject.getJSONObject("note");
                    Note newNote = new Note(note);

                    Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                }catch (JSONException e) {
                    L.error("", e);
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.cancel();
            }
        };
    }
    String textOf(EditText editText) {
        return editText.getText().toString().trim();
    }
}
