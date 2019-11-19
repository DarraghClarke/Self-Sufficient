package com.acdos.comp41690.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acdos.comp41690.data.SolarTrackingContract.SolarTrackingEntry;


public class SolarTrackingDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String DATABASE_NAME = "usage.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SolarTrackingEntry.TABLE_NAME + " (" +
                    SolarTrackingEntry._ID + " INTEGER PRIMARY KEY," +
                    SolarTrackingEntry.COLUMN_NAME_TIMESTAMP + " INTEGER," +
                    SolarTrackingEntry.COLUMN_NAME_VOLUME + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SolarTrackingEntry.TABLE_NAME;


    public SolarTrackingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
