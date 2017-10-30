package com.zybooks.twister;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_TWIST_ID = "twistId";
    public static ArrayList<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDataFetcher userFetcher = new UserDataFetcher(this);
        userFetcher.getData("/user/bsmith", new UserDataFetcher.OnUserReceivedListener() {
            @Override
            public void onUserReceived(User user) {
                mUsers.add(user);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }

        });

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
