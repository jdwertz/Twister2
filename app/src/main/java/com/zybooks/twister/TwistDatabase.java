package com.zybooks.twister;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private ArrayList<Twist> mTwists;
    private ArrayList<User> mUsers;
    private TwistDatabaseHelper mDbHelper;
    private Context mContext;

    public interface VolleyCallback{
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



        DataFetcher fetcher = new DataFetcher(context);
        fetcher.getData("/twist/", new DataFetcher.OnTwistsReceivedListener() {
            @Override
            public void onTwistsReceived(ArrayList<Twist> twists) {
                Log.d("Josh5", "Something");
                mTwists = twists;
                Log.d("Josh5", Integer.toString(mTwists.size()));
                for (int i = 0; i < mTwists.size(); i++) {
                    Log.d("Josh5", Integer.toString(i));
                    mDbHelper.addTwist(mTwists.get(i));
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
            }
        });
    }

    public void getUserDetails(){
        for (int i = 0; i < mTwists.size(); i++) {
            UserDataFetcher userFetcher = new UserDataFetcher(mContext);
            userFetcher.getData("/user/" + mTwists.get(i).getName(), new UserDataFetcher.OnUserReceivedListener() {
                @Override
                public void onUserReceived(User user) {
                    Log.d("UserFetcher reply", "Username: " + user.getUsername() + "About:" + user.getAbout());
                    mUsers.add(user);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Josh", error.toString());
                }
            });
        }
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
        Log.d("JoshDB", "User list:" + mUsers.size());
        for (int i = 0; i < mUsers.size(); i++){
            if(mUsers.get(i).getUsername().equals(username)){
                Log.d("Josh4", "Username: " + mUsers.get(i).getUsername());
                return mUsers.get(i);
            }
        }
        return null;
    }
}
