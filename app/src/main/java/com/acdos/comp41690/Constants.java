package com.acdos.comp41690;

/**
 * Constants used throughout the codebase
 */
public class Constants {
    // Strings used as parameters for the QuestionFragment class
    public static class QuestionType {
        public final static String WATER_TANK_QUESTION = "WATER_TANK_QUESTION";
        public final static String ROOF_AREA_QUESTION = "ROOF_AREA_QUESTION";
        public final static String REQUEST_LOCATION_QUESTION = "REQUEST_LOCATION_QUESTION";
    }

    // Keys used in SharedPreferences
    public static class SharedPrefKeys {
        public final static String FIRST_RUN = "first_run";
        public final static String ROOF_AREA = "roof_area";
        public final static String USING_WATER = "using_water";
        public final static String USING_SOLAR = "using_solar";
        public final static String SOLAR_PANEL_OUTPUT = "solar_panel_output";
        public final static String KWH_RATE = "kwh_rate";
        public final static String WATER_TANK_SIZE = "water_tank_size";
        public final static String LONGITUDE = "longitude";
        public final static String LATITUDE = "latitude";
    }
}
