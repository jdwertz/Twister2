package com.zybooks.twister;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private ArrayList<Twist> mTwists;
    private ArrayList<User> mUsers;
    private TwistDatabaseHelper mDbHelper;
    private Context mContext;

    public interface VolleyCallback {
        void onSuccess();
    }

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

    public void setTwists(ArrayList<Twist> twists) {
        mTwists = twists;
    }

    public ArrayList<Twist> getTwists() {
        return mDbHelper.getTwists();
    }

    public void clearAllTwists()
    {
        mDbHelper.clearAllTwists();
    }

    public Twist getTwist(int twistId) {
        for (Twist twist : mTwists) {
            if (twist.getId() == twistId) {
                return twist;
            }
        }
        return null;
    }

    public void addTwist(Twist twist) {
       // Log.d("Josh6", Integer.toString(mTwists.get(0).getId()));

      //  Log.d("Josh6", Integer.toString(mTwists.size() + 1));
       /* int size = mTwists.size();
        size++;
        Log.d("Josh6", twist.getName());
        twist.setId(size);
        ArrayList<Twist> temp = new ArrayList<>();
        temp.add(twist);
        temp.addAll(mTwists);
        mTwists.clear();
        mTwists.addAll(temp); */

        mDbHelper.addTwist(twist);
    }

    public ArrayList<Twist> search (String searchString){
        ArrayList<Twist> matchedTwists = new ArrayList<>();
        return matchedTwists;
    }

}