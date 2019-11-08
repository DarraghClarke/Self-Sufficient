package com.acdos.comp41690.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acdos.comp41690.data.WaterTrackingContract.WaterTrackingEntry;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 */
public class WaterTrackingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int VERSION_NUMBER = 1;

    public WaterTrackingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + WaterTrackingEntry.TABLE_NAME + " (" + WaterTrackingEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + WaterTrackingEntry.COLUMN_TIMESTAMP + " INTEGER NOT NULL, " +
                WaterTrackingEntry.COLUMN_VOLUME + " REAL);";

        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}