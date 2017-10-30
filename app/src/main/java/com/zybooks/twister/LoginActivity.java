package com.zybooks.twister;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText mInputUsernameText;
    private String mUsername;
    private TwistDatabase mDb;
    private ArrayList<Twist> mTwists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputUsernameText = findViewById(R.id.inputUsernameText);
        mDb = TwistDatabase.get(this);
        mTwists = mDb.getTwists();
    }

    protected void onInputUsernameClicked(View view)
    {
        mUsername = mInputUsernameText.getText().toString();
        Log.d("username", mUsername);
        for (int i = 0; i < mTwists.size(); i ++){
            if(mUsername.equals(mTwists.get(i).getName())){
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
            }
        }
    }
}