package com.zybooks.twister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListFragment.OnTwistSelectedListener {

    private static String KEY_TWIST_ID = "twistId";
    private int mTwistId;
    private ArrayList<Twist> mTwists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TwistDatabase.get(this).getUserDetails();

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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //getMenuInflater().inflate(R.menu.popup, menu);

        // Locate MenuItem with ShareActionProvider
        //MenuItem item = menu.findItem(R.id.addTwist);
        //ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // User chose action_settings
            case R.id.addTwist:
                Context context = getApplicationContext();
                //CharSequence text = "Add twist pressed";
               // int duration = Toast.LENGTH_SHORT;

               // Toast toast = Toast.makeText(context, text, duration);
               // toast.show();

                Intent intent = new Intent(this, AddTwistActivity.class);
                startActivity(intent);

                return true;

            case R.id.About:
                AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("Program by Trevor Hale and Josh Wertz");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

        //public boolean onShowPopup(View v) {
        //PopupMenu popupMenu = new PopupMenu(this, v);
        //MenuInflater inflater = popupMenu.getMenuInflater();
        // inflater.inflate(R.menu.popup, popupMenu.getMenu());
        // popupMenu.show();


        //    return true;
        //}

        //@Override
        //public boolean onOptionsItemSelected(MenuItem item) {
        //   switch(item.getItemId()) {
        //     case R.id.addTwist:
        //       Intent intent = new Intent(this, AddTwistActivity.class);
        //          this.startActivity(intent);
        //         break;
        //case R.id.menu_item2:
        // another startActivity, this is for item with id "menu_item2"
        //    break;
        //     default:
        //         return super.onOptionsItemSelected(item);
        //}

        // return true;
    }
}
