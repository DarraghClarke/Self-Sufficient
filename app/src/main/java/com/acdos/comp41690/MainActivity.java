package com.acdos.comp41690;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.acdos.comp41690.data.WaterTrackingContract.WaterTrackingEntry;
import com.acdos.comp41690.data.WaterTrackingDbHelper;

import java.time.Instant;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
