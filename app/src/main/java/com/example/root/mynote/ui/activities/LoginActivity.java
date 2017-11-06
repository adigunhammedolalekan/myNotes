package com.example.root.mynote.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.root.mynote.R;
import com.example.root.mynote.core.Api;
import com.example.root.mynote.core.Memory;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.util.L;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by root on 11/6/17.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_username_login)
    EditText usernameEditText;
    @BindView(R.id.edt_password_login)
    EditText passwordEditText;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
    }
    @OnClick(R.id.btn_login) public void onLoginClick() {

        if(textOf(usernameEditText).isEmpty()) {
            toast("Username is empty");
            return;
        }
        if(textOf(passwordEditText).isEmpty()) {
            toast("Enter your password");
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("username", textOf(usernameEditText));
        requestParams.put("password", textOf(passwordEditText));
        Api.post("/login", requestParams, textHttpResponseHandler);
    }
    @OnClick(R.id.btn_sign_up_login) public void onSignUpClick() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    String textOf(EditText editText) {
        return editText.getText().toString().trim();
    }
    private TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {
        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            L.error(responseString, throwable);
            toast("There was an error. Please retry");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {

            try {
                JSONObject jsonObject = new JSONObject(responseString);
                if(!jsonObject.getBoolean("status")) {
                    toast(jsonObject.getString("message"));
                    return;
                }
                User user = new User(jsonObject.getJSONObject("data"));
                Memory.memory().putUser(user);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
