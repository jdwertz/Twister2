package com.zybooks.thebanddatabase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_TWIST_ID = "twistId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Terminate if two panes are displaying since ListActivity should be displaying both panes
        boolean isTwoPanes = getResources().getBoolean(R.bool.twoPanes);
        if (isTwoPanes) {
            finish();
            return;
        }

        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            // Use band ID from ListFragment to instantiate DetailsFragment
            int twistId = getIntent().getIntExtra(EXTRA_TWIST_ID, 1);
            fragment = DetailsFragment.newInstance(twistId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}
