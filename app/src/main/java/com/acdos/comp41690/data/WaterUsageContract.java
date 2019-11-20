package com.acdos.comp41690.data;

import android.provider.BaseColumns;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 */
public final class WaterUsageContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WaterUsageContract() {}

    /* Inner class that defines the table contents */
    public static class WaterUsageEntry implements BaseColumns {
        public static final String TABLE_NAME = "water_usage";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}

