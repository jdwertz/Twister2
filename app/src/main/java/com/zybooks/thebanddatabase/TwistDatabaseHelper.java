package com.zybooks.thebanddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class TwistDatabaseHelper extends SQLiteOpenHelper{

    private Context mContext;
    private ArrayList<Twist> mTwists;
    private SQLiteDatabase mDb;


    private static final class TwistTable {
        private static final String TABLE = "twists";
        private static final String COL_ID = "id";
        private static final String COL_NAME = "name";
        private static final String COL_DESC = "desc";
        private static final String COL_TIME = "time";
    }

    public TwistDatabaseHelper (Context context, ArrayList<Twist> twists) {
        super(context, "twists.db", null, 2);
        mContext = context;
        mTwists = twists;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Josh2", "Is on create being called?");
        mDb = sqLiteDatabase;
        mDb.execSQL("create table " + TwistTable.TABLE +
                " (id integer, " +
                "name, desc, time real)");


        //Resources res = mContext.getResources();
        /*String[] twists = res.getStringArray(R.array.bands);
        String[] descriptions = res.getStringArray(R.array.descriptions);*/
        Log.d("Josh2", "Creating dataFetcher");
        DataFetcher fetcher = new DataFetcher(mContext);
        fetcher.getData("/twist/", new DataFetcher.OnTwistsReceivedListener() {
            @Override
            public void onTwistsReceived(ArrayList<Twist> twists) {
                mTwists = twists;
                for (int i = 0; i < mTwists.size(); i++) {
                    Twist twist = new Twist();
                    twist.setName(mTwists.get(i).getName());
                    twist.setDescription(mTwists.get(i).getDescription());
                    twist.setId(mTwists.get(i).getId());
                    addTwist(mDb, twist);
                    Log.d("Josh3", twist.getName());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table twists");
        onCreate(sqLiteDatabase);
    }

    public void addTwist(SQLiteDatabase db, Twist twist) {
        // Insert the twist into bands table
        //SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TwistTable.COL_ID, twist.getId());
        values.put(TwistTable.COL_NAME, twist.getName());
        values.put(TwistTable.COL_DESC, twist.getDescription());
        values.put(TwistTable.COL_TIME, twist.getmTimeAgo());
        db.insert(TwistTable.TABLE, null, values);
    }

    public ArrayList<Twist> getTwists() {

        ArrayList<Twist> twists = new ArrayList<>();

        String query = "select * from " + TwistTable.TABLE;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // Loop through all twists
            do {
                Twist twist = new Twist();
                twist.setId(cursor.getInt(0));
                twist.setName(cursor.getString(1));
                twist.setDescription(cursor.getString(2));
                twist.setmTimeAgo(cursor.getString(3));
                //twist.setRating(cursor.getFloat(3));
                twists.add(twist);
            } while (cursor.moveToNext());
        }

        return twists;
    }

    public void updateTwist(Twist twist) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = buildValues(twist);
        db.update(TwistTable.TABLE, values, TwistTable.COL_ID +
                        " = ?",
                new String[] { String.valueOf(twist.getId()) });
    }

    private ContentValues buildValues(Twist twist) {
        ContentValues values = new ContentValues();
        values.put(TwistTable.COL_ID, twist.getId());
        values.put(TwistTable.COL_NAME, twist.getName());
        values.put(TwistTable.COL_DESC, twist.getDescription());
        values.put(TwistTable.COL_TIME, twist.getmTimeAgo());
        return values;
    }
}
