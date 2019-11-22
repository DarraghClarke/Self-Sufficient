package com.acdos.comp41690.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acdos.comp41690.data.SolarGenerationContract.SolarGenerationEntry;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 *
 * To access this database, follow the instructions detailed here:
 * https://developer.android.com/training/data-storage/sqlite.html
 */
public class SolarGenerationDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String DATABASE_NAME = "info.db";
    public static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SolarGenerationEntry.TABLE_NAME + " (" +
                    SolarGenerationEntry._ID + " INTEGER PRIMARY KEY," +
                    SolarGenerationEntry.COLUMN_NAME_TIMESTAMP + " INTEGER," +
                    SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SolarGenerationEntry.TABLE_NAME;


    public SolarGenerationDbHelper(Context context) {
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