
package com.acdos.comp41690.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acdos.comp41690.data.SolarGenerationContract.SolarGenerationEntry;
import com.acdos.comp41690.data.SolarUsageContract.SolarUsageEntry;
import com.acdos.comp41690.data.WaterUsageContract.WaterUsageEntry;

/**
 * Class used to access all data stored by the user, including water usage, electricity usage
 * and solar energy generated
 *
 * This class uses SQLiteOpenHelper to simplify accessing and creating the database
 *
 * Developed using code from https://developer.android.com/training/data-storage/sqlite.html
 */
public class UserDataDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "info.db";
    // This number must be incremented when the database schema changes
    public static final int DATABASE_VERSION = 3;

    // SQL queries used to create all required tables
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

    private static final String SQL_DELETE_SOLAR_USAGE =
            "DROP TABLE IF EXISTS " + SolarUsageEntry.TABLE_NAME;

    private static final String SQL_DELETE_SOLAR_GENERATION =
            "DROP TABLE IF EXISTS " + SolarGenerationEntry.TABLE_NAME;

    private static final String SQL_DELETE_WATER_USAGE =
            "DROP TABLE IF EXISTS " + WaterUsageEntry.TABLE_NAME;

    public UserDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SOLAR_USAGE);
        db.execSQL(SQL_CREATE_SOLAR_GENERATION);
        db.execSQL(SQL_CREATE_WATER_USAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is a naive solution, but implementing a proper onUpgrade version is complicated and
        // not predicted to be needed. For now, we just drop all tables
        db.execSQL(SQL_DELETE_SOLAR_GENERATION);
        db.execSQL(SQL_DELETE_SOLAR_USAGE);
        db.execSQL(SQL_DELETE_WATER_USAGE);
        onCreate(db);
    }
}