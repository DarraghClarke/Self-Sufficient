package com.acdos.comp41690.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-08.
 */
public class WaterTrackingContract {

    public static final String CONTENT_AUTHORITY = "com.acdos.comp41690";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WATER= "water";

    public static abstract class WaterTrackingEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WATER);

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "water";
        public static final String COLUMN_VOLUME = "volume";
        public static final String COLUMN_TIMESTAMP = "timestamp";


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WATER;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WATER;
    }
}
