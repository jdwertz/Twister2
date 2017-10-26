package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.zip.Inflater;

public class ListActivity extends AppCompatActivity implements ListFragment.OnBandSelectedListener {

    private static String KEY_BAND_ID = "bandId";
    private int mBandId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mBandId = -1;

        DataFetcher dataFetcher = new DataFetcher();
        dataFetcher.getData(this, "/twist/");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_BAND_ID) != 0
                && getResources().getBoolean(R.bool.twoPanes)) {
            mBandId = savedInstanceState.getInt(KEY_BAND_ID);
            Fragment bandFragment = DetailsFragment.newInstance(mBandId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bandFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mBandId != -1) {
            savedInstanceState.putInt(KEY_BAND_ID, mBandId);
        }
    }

    @Override
    public void onBandSelected(int bandId) {

        mBandId = bandId;

        if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_BAND_ID, bandId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment bandFragment = DetailsFragment.newInstance(bandId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bandFragment)
                    .commit();
        }
    }
}
