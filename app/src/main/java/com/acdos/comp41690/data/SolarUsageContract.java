package com.acdos.comp41690.data;

import android.provider.BaseColumns;

/**
 * Class that represents the table name and columns associated with the usage of solar energy
 * Used to ensure consistency when accessing the solar_usage table
 */
public final class SolarUsageContract {
    private SolarUsageContract() {}

    // Inner class with constants that represent the columns and table name
    public static class SolarUsageEntry implements BaseColumns {
        public static final String TABLE_NAME = "solar_usage";
        public static final String COLUMN_NAME_USAGE = "usage";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}

