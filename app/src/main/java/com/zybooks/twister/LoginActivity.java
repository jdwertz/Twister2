package com.zybooks.twister;

import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mInputUsernameText;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputUsernameText = findViewById(R.id.inputUsernameText);
    }

    protected void onInputUsernameClicked(View view)
    {
        mUsername = mInputUsernameText.getText().toString();
        Log.d("username", mUsername);
    }
}
