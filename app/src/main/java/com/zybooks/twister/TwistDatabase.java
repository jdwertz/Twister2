package com.zybooks.twister;

import android.content.Context;
import java.util.ArrayList;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private ArrayList<Twist> mTwists;
    private ArrayList<User> mUsers;
    private TwistDatabaseHelper mDbHelper;
    private Context mContext;


    public static TwistDatabase get(Context context) {
        if (sTwistDatabase == null) {

            sTwistDatabase = new TwistDatabase(context);
        }
        return sTwistDatabase;
    }

    private TwistDatabase(Context context) {
        mContext = context;
        mDbHelper = new TwistDatabaseHelper(context, mTwists);
        mUsers = new ArrayList<>();
    }

    public ArrayList<Twist> getTwists() {

        return mDbHelper.getTwists();
    }

    public void clearAllTwists() {
        mDbHelper.clearAllTwists();
    }

    public void addTwist(Twist twist) {
        mDbHelper.addTwist(twist);
    }

    public ArrayList<Twist> search(String searchString) {
        return mDbHelper.getWordMatches(searchString);
    }
}