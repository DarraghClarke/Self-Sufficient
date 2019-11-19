package com.acdos.comp41690.data;

import android.provider.BaseColumns;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 */
public final class SolarTrackingContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SolarTrackingContract() {}

    /* Inner class that defines the table contents */
    public static class SolarTrackingEntry implements BaseColumns {
        public static final String TABLE_NAME = "solar";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}

