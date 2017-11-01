package com.zybooks.twister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText mInputUsernameText;
    private String mUsername;
    private TwistDatabase mDb;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputUsernameText = (EditText) findViewById(R.id.inputUsernameText);
        mDb = TwistDatabase.get(this);
        mDb.getTwists();

    }

    protected void onInputUsernameClicked(View view) {
        final SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();



        boolean userNameFound = false;
       /* mTwists = mDb.getTwists();*/
       mUsername = mInputUsernameText.getText().toString();
       UserDataFetcher fetcher = new UserDataFetcher(mContext);
       fetcher.getData("/user/" + mUsername, new UserDataFetcher.OnUserReceivedListener() {
           @Override
           public void onUserReceived(User user) {
               prefEditor.putString("username", mUsername);
               prefEditor.apply();
               Intent intent = new Intent(mContext, ListActivity.class);
               startActivity(intent);
           }

           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(mContext, "Account credentials invalid!", Toast.LENGTH_LONG).show();
               Log.e("error", error.getMessage());
           }
       });

        /*if (mTwists.size() == 0){
            Toast.makeText(this, "No response from API yet", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("username", mUsername);
            for (int i = 0; i < mTwists.size() && !userNameFound; i++) {
                if (mUsername.equals(mTwists.get(i).getName())) {
                    userNameFound = true;
                    Intent intent = new Intent(this, ListActivity.class);
                    startActivity(intent);
                }
            }
        }*/
    }
}