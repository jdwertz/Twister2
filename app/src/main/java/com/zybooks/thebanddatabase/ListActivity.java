package com.zybooks.thebanddatabase;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListFragment.OnTwistSelectedListener {

    private static String KEY_TWIST_ID = "twistId";
    private int mTwistId;
    private ArrayList<Twist> mTwists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mTwistId = -1;




        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_TWIST_ID) != 0
                && getResources().getBoolean(R.bool.twoPanes)) {
            mTwistId = savedInstanceState.getInt(KEY_TWIST_ID);
            Fragment twistFragment = DetailsFragment.newInstance(mTwistId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, twistFragment)
                    .commit();
        }
    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mTwistId != -1) {
            savedInstanceState.putInt(KEY_TWIST_ID, mTwistId);
        }
    }

    @Override
    public void onTwistSelected(int twistId) {
        Log.d("Josh", "onTwistSelected (ListActivity)");
        mTwistId = twistId;

        if (findViewById(R.id.details_fragment_container) == null) {
            Log.d("Josh", "?");
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_TWIST_ID, twistId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment twistFragment = DetailsFragment.newInstance(twistId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, twistFragment)
                    .commit();
        }
    }
}
