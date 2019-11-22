package com.acdos.comp41690.data;

import android.provider.BaseColumns;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 */
public final class SolarGenerationContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SolarGenerationContract() {}

    /* Inner class that defines the table contents */
    public static class SolarGenerationEntry implements BaseColumns {
        public static final String TABLE_NAME = "solar_generation";
        public static final String COLUMN_NAME_GENERATED_ENERGY = "generated_energy";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}
