
package com.acdos.comp41690.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acdos.comp41690.data.SolarUsageContract.SolarUsageEntry;
import com.acdos.comp41690.data.SolarGenerationContract.SolarGenerationEntry;
import com.acdos.comp41690.data.WaterUsageContract.WaterUsageEntry;
import com.acdos.comp41690.ui.solar.ElecStatsFragment;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 *
 * To access this database, follow the instructions detailed here:
 * https://developer.android.com/training/data-storage/sqlite.html
 */
public class UserDataDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String DATABASE_NAME = "info.db";
    public static final int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_SOLAR_USAGE =
            "CREATE TABLE " + SolarUsageEntry.TABLE_NAME + " (" +
                    SolarUsageEntry._ID + " INTEGER PRIMARY KEY," +
                    SolarUsageEntry.COLUMN_NAME_TIMESTAMP + " INTEGER," +
                    SolarUsageEntry.COLUMN_NAME_USAGE + " REAL)";

    private static final String SQL_CREATE_SOLAR_GENERATION =
            "CREATE TABLE " + SolarGenerationEntry.TABLE_NAME + " (" +
                    SolarGenerationEntry._ID + " INTEGER PRIMARY KEY," +
                    SolarGenerationEntry.COLUMN_NAME_TIMESTAMP + " INTEGER," +
                    SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY + " REAL)";

    private static final String SQL_CREATE_WATER_USAGE =
            "CREATE TABLE " + WaterUsageEntry.TABLE_NAME + " (" +
                    WaterUsageEntry._ID + " INTEGER PRIMARY KEY," +
                    WaterUsageEntry.COLUMN_NAME_TIMESTAMP + " INTEGER," +
                    WaterUsageEntry.COLUMN_NAME_VOLUME + " REAL)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SolarUsageEntry.TABLE_NAME;


    public UserDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SOLAR_USAGE);
        db.execSQL(SQL_CREATE_SOLAR_GENERATION);
        db.execSQL(SQL_CREATE_WATER_USAGE);
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