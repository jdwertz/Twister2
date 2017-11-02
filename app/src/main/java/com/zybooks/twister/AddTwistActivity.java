package com.zybooks.twister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;

public class AddTwistActivity extends AppCompatActivity {

    private String mUsername;
    private EditText mEditTwist;
    private TextView mCharCount;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            mCharCount.setText(String.valueOf(150 - s.length()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_twist);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = prefs.getString("username", "noName");

        mEditTwist = (EditText) findViewById(R.id.inputTwistText);
        mEditTwist.addTextChangedListener(mTextEditorWatcher);
        mCharCount = (TextView) findViewById(R.id.twistCharCountView);
    }

    public void onAddTwistIconClicked(View view)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new java.util.Date());
        Twist twist = new Twist();
        twist.setName(mUsername);
        twist.setDescription(mEditTwist.getText().toString());
        twist.setmTimeAgo(time);
        TwistDatabase.get(this).addTwist(twist);
        Intent intent = new Intent(this, ListActivity.class );
        startActivity(intent);
    }
}
