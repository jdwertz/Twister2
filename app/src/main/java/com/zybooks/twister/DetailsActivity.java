package com.zybooks.twister;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnTwistSelectedListener {

    public static String EXTRA_TWIST_USERNAME = "twistId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Terminate if two panes are displaying since ListActivity should be displaying both panes
        //boolean isTwoPanes = getResources().getBoolean(R.bool.twoPanes);
        //if (isTwoPanes) {
        //    finish();
        //    return;
        //}

        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            // Use band ID from ListFragment to instantiate DetailsFragment
            String username = getIntent().getStringExtra(EXTRA_TWIST_USERNAME);
            fragment = DetailsFragment.newInstance(username);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onTwistSelected(int twistId) {
        Log.d("onTwistSelected", "Ignoring click");
    }
}
