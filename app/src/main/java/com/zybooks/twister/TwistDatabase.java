package com.zybooks.twister;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private ArrayList<Twist> mTwists;
    private ArrayList<User> mUser;
    private TwistDatabaseHelper mDbHelper;
    private Context mContext;

    public static TwistDatabase get(Context context) {
        if (sTwistDatabase == null) {

            sTwistDatabase = new TwistDatabase(context);
        }
        return sTwistDatabase;
    }

    private TwistDatabase(Context context) {
        mDbHelper = new TwistDatabaseHelper(context, mTwists);

        DataFetcher fetcher = new DataFetcher(context);
        fetcher.getData("/twist/", new DataFetcher.OnTwistsReceivedListener() {
            @Override
            public void onTwistsReceived(ArrayList<Twist> twists) {
                mTwists = twists;
                for (int i = 0; i < mTwists.size(); i++) {
                    mDbHelper.addTwist(mTwists.get(i));
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
            }
        });

        UserDataFetcher userFetcher = new UserDataFetcher(context);
        userFetcher.getData("/user/bsmith", new UserDataFetcher.OnUserReceivedListener() {
            @Override
            public void onUserReceived(User user) {
                Log.d("UserFetcher reply", "Username: " + user.getUsername() + "About:" + user.getAbout());
                mUser.add(user);
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
            }
        });
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

    public User getUser(String username){

        for (int i = 0; i < mUser.size(); i++){
            if(mUser.get(i).getUsername().equals(username)){
                return mUser.get(i);
            }
        }
        return null;
    }
}
