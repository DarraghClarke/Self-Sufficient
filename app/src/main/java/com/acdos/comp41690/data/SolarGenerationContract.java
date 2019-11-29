package com.acdos.comp41690.data;

import android.provider.BaseColumns;

/**
 * Class that represents the table name and columns associated with the generation of solar energy
 * Used to ensure consistency when accessing the solar_generation table
 */
public final class SolarGenerationContract {
    private SolarGenerationContract() {}

    // Inner class with constants that represent the columns and table name
    public static class SolarGenerationEntry implements BaseColumns {
        public static final String TABLE_NAME = "solar_generation";
        public static final String COLUMN_NAME_GENERATED_ENERGY = "generated_energy";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}

