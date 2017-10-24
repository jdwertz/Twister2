package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class BandDatabase {

    private static BandDatabase sBandDatabase;
    private List<Band> mBands;

    public static BandDatabase get(Context context) {
        if (sBandDatabase == null) {
            sBandDatabase = new BandDatabase(context);
        }
        return sBandDatabase;
    }

    private BandDatabase(Context context) {
        mBands = new ArrayList<>();
        Resources res = context.getResources();
        String[] bands = res.getStringArray(R.array.bands);
        String[] descriptions = res.getStringArray(R.array.descriptions);
        String[] genres = res.getStringArray(R.array.genre);
        for (int i = 0; i < bands.length; i++) {
            mBands.add(new Band(i + 1, bands[i], descriptions[i], genres[i]));
        }
    }

    public List<Band> getBands() {
        return mBands;
    }

    public Band getBand(int bandId) {
        for (Band band : mBands) {
            if (band.getId() == bandId) {
                return band;
            }
        }
        return null;
    }
}
