package com.zybooks.thebanddatabase;

import android.content.Context;

import java.util.ArrayList;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private ArrayList<Twist> mTwists;
    private TwistDatabaseHelper mDbHelper;

    public static TwistDatabase get(Context context) {
        if (sTwistDatabase == null) {
            sTwistDatabase = new TwistDatabase(context);
        }
        return sTwistDatabase;
    }

    private TwistDatabase(Context context) {
        mDbHelper = new TwistDatabaseHelper(context, mTwists);

    }

    public void setTwists(ArrayList<Twist> twists){
        mTwists = twists;
    }

    public ArrayList<Twist> getTwists() {
        if (mTwists == null) {
            // Call db helper to load bands
            mTwists = mDbHelper.getTwists();
        }
        return mTwists;
    }

    public Twist getTwist(int twistId) {
        for (Twist twist : mTwists) {
            if (twist.getId() == twistId) {
                return twist;
            }
        }
        return null;
    }

    public void updateTwist(Twist band) {
        mDbHelper.updateTwist(band);
    }
}
