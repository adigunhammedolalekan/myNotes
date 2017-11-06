package com.example.root.mynote.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.widget.EditText;

import com.example.root.mynote.R;
import com.example.root.mynote.core.Api;
import com.example.root.mynote.core.Memory;
import com.example.root.mynote.entities.User;
import com.example.root.mynote.util.L;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by root on 11/5/17.
 */

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.edt_email_sign_up)
    EditText emailEditText;
    @BindView(R.id.edt_username_sign_up)
    EditText usernameEditText;
    @BindView(R.id.edt_password_sign_up)
    EditText passwordEditText;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_sign_up);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
    }
    @OnClick(R.id.btn_create_account) public void onCreateAccountClick() {

        if(!Patterns.EMAIL_ADDRESS.matcher(textOf(emailEditText)).matches()) {
            toast("Invalid email address.");
            return;
        }
        if(textOf(usernameEditText).isEmpty()) {
            toast("Please enter username");
            return;
        }
        if(textOf(passwordEditText).isEmpty()) {
            toast("Enter password");
            return;
        }

        /*User user = new User(textOf(usernameEditText), textOf(emailEditText));
        DatabaseManager manager = DatabaseManager.getInstance();
        long created = manager.addUser(user);
        if(created == -1) {
            toast("Failed to create account. Please retry");
            return;
        }
        toast("Account Created!");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", String.valueOf(created));
        startActivity(intent);*/

        RequestParams requestParams = new RequestParams();
        requestParams.put("username", textOf(usernameEditText));
        requestParams.put("password", textOf(passwordEditText));
        requestParams.put("email", textOf(emailEditText));
        Api.post("/user", requestParams, createHandler());
    }
    String textOf(EditText editText) {
        return editText.getText().toString().trim();
    }

    private TextHttpResponseHandler createHandler() {

        return new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                L.error(responseString, throwable);
                toast("Error response from network. Please retry");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if(!jsonObject.getBoolean("status")) {
                        toast(jsonObject.getString("message"));
                        return;
                    }
                    JSONObject data = jsonObject.getJSONObject("data");
                    User user = new User(data);
                    Memory.memory().putUser(user);

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
    @OnClick(R.id.btn_login_sign_up) public void onLoginClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
