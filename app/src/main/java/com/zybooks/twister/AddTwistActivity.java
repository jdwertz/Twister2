package com.zybooks.twister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTwistActivity extends AppCompatActivity {

    private String mUsername;
    private EditText mEditTwist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twist);

        SharedPreferences sharedPref = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        mUsername = sharedPref.getString("username", null);

        mEditTwist = (EditText) findViewById(R.id.inputTwistText);

        Log.d("twistUsername", "username =" + mUsername);
    }

    public void onAddTwistIconClicked(View view)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format.format(new java.util.Date());
        Log.d("Josh5", "Time: " + time);
        Twist twist = new Twist();
        twist.setName(mUsername);
        twist.setDescription(mEditTwist.toString());
        twist.setmTimeAgo(time);
        TwistDatabase.get(this).addTwist(twist);
        Intent intent = new Intent(this, ListActivity.class );
        startActivity(intent);
    }
}
