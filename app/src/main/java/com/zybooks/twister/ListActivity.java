package com.zybooks.twister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListFragment.OnTwistSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onTwistSelected(Twist twist) {

        if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_TWIST_USERNAME, twist.getName());
            startActivity(intent);
        } else {
            // To do for extra credit...
            // Replace previous fragment (if one exists) with a new fragment
            // Fragment twistFragment = DetailsFragment.newInstance(twistId);
            // getSupportFragmentManager().beginTransaction()
            //         .replace(R.id.details_fragment_container, twistFragment)
            //         .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTwist:
                Intent intent = new Intent(this, AddTwistActivity.class);
                startActivity(intent);
                return true;

            case R.id.Logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;

            case R.id.searchTwists:
                intent = new Intent (this, SearchActivity.class);
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
    }
}
