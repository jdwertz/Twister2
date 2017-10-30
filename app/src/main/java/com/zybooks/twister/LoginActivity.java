package com.zybooks.twister;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    protected void onInputUsernameClicked(View view) {
        boolean userNameFound = false;
        mTwists = mDb.getTwists();
        if (mTwists.size() == 0){
            Toast.makeText(this, "No response from API yet", Toast.LENGTH_LONG);
        }
        else {
            mUsername = mInputUsernameText.getText().toString();
            Log.d("username", mUsername);
            for (int i = 0; i < mTwists.size() && !userNameFound; i++) {
                if (mUsername.equals(mTwists.get(i).getName())) {
                    userNameFound = true;
                    Intent intent = new Intent(this, ListActivity.class);
                    startActivity(intent);
                }
            }

            Log.d("usernameFound", String.valueOf(userNameFound));
            if(!userNameFound)
            {
                Toast.makeText(this, "Account credentials invalid!", Toast.LENGTH_LONG);
            }

        }
    }
}