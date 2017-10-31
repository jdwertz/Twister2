package com.zybooks.twister;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AddTwistActivity extends AppCompatActivity {

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twist);

        SharedPreferences sharedPref = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        mUsername = sharedPref.getString("username", null);

        Log.d("twistUsername", "username =" + mUsername);
    }

    public void onAddTwistIconClicked()
    {
        Toast.makeText(this, "something", Toast.LENGTH_LONG).show();
    }
}
