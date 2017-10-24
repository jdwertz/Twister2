/*
package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class TwistDatabase {

    private static TwistDatabase sTwistDatabase;
    private List<Twist> mTwists;

    public static TwistDatabase get(Context context) {
        if (sTwistDatabase == null) {
            sTwistDatabase = new TwistDatabase(context);
        }
        return sTwistDatabase;
    }

    private TwistDatabase(Context context) {
        mTwists = new ArrayList<>();
        Resources res = context.getResources();
        String[] bands = res.getStringArray(R.array.twists);
        String[] descriptions = res.getStringArray(R.array.descriptions);
        String[] genres = res.getStringArray(R.array.genre);
        for (int i = 0; i < bands.length; i++) {
            mTwists.add(new Twist(i + 1, bands[i], descriptions[i], genres[i]));
        }
    }

    public List<Twist> getBands() {
        return mTwists;
    }

    public Twist getBand(int bandId) {
        for (Twist twist : mTwists) {
            if (twist.getId() == bandId) {
                return twist;
            }
        }
        return null;
    }
}
*/
