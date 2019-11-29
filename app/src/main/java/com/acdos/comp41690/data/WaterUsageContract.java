package com.acdos.comp41690.data;


import android.provider.BaseColumns;

/**
 * Class that represents the table name and columns associated with the usage of water
 * Used to ensure consistency when accessing the water_usage table
 */
public final class WaterUsageContract {
    private WaterUsageContract() {}

    // Inner class with constants that represent the columns and table name
    public static class WaterUsageEntry implements BaseColumns {
        public static final String TABLE_NAME = "water_usage";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}

