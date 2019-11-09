package com.acdos.comp41690;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.acdos.comp41690.data.WaterTrackingContract.WaterTrackingEntry;
import com.acdos.comp41690.data.WaterTrackingDbHelper;

import java.time.Instant;

public class MainActivity extends Activity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testDb();

        prefs = getSharedPreferences(
                getString(R.string.shared_preferences), Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Launch set-up view
            Intent intent = new Intent(this, SetupActivity.class);

            prefs.edit().putBoolean("firstrun", false).apply();

            startActivity(intent);
        }
        // Launch dashboard view
    }

    private void testDb() {
        WaterTrackingDbHelper dbHelper = new WaterTrackingDbHelper(this);

        ContentValues values = new ContentValues();
        values.put(WaterTrackingEntry.COLUMN_NAME_VOLUME, 53302.33);
        values.put(WaterTrackingEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(WaterTrackingEntry.TABLE_NAME, null, values);

        String[] projection = {
                WaterTrackingEntry._ID,
                WaterTrackingEntry.COLUMN_NAME_TIMESTAMP,
                WaterTrackingEntry.COLUMN_NAME_VOLUME };

        Cursor c = db.query(WaterTrackingEntry.TABLE_NAME, projection, null, null, null, null, null);

        while(c.moveToNext()) {
            Log.d("MainActivity", c.getLong(0) + ", " + c.getLong(1) + ", " + c.getDouble(2));
        }
        c.close();
    }
}
