package com.acdos.comp41690.setup;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.MainActivity;
import com.acdos.comp41690.R;
import com.acdos.comp41690.data.SolarGenerationContract;
import com.acdos.comp41690.data.SolarUsageContract;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.acdos.comp41690.data.WaterUsageContract.WaterUsageEntry;

/**
 * The last fragment used in the setup, stating that setup is complete
 */
public class SetupConfirmFragment extends Fragment {

    SetupConfirmFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.setup_confirmation_fragment, container, false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Button confirm = view.findViewById(R.id.Confirm);

        // When the user confirms that they are finished setup, we mark setup as complete in SharedPreferences
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPreferences.Editor editor = prefs.edit();

               // We use the class name of the activity to know exactly how much of the app is completed
                String activityClassName = getActivity().getLocalClassName();

                // I know hardcoding the names isn't best practice, but this was the best
                // solution I could find
                switch (activityClassName) {
                    case "setup.SolarOnlyPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR, true);
                        break;
                    case "setup.WaterOnlyPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_WATER, true);
                        break;
                    case "setup.FullPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_WATER, true);
                        editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR, true);
                        break;
                }

                editor.apply();

                // Saves that general setup is completed
                prefs.edit().putBoolean(Constants.SharedPrefKeys.FIRST_RUN, false).apply();

                addSolarInfo();
                addWaterInfo();

                // Starts MainActivity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Placeholder data used to show the examiner how graphs look with data across multiple days
    private void addWaterInfo() {
        UserDataDbHelper dbHelper = new UserDataDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WaterUsageEntry.COLUMN_NAME_VOLUME, 5);
        values.put(WaterUsageEntry.COLUMN_NAME_TIMESTAMP, 26);
        db.insert(WaterUsageEntry.TABLE_NAME, null, values);
        values.put(WaterUsageEntry.COLUMN_NAME_VOLUME, 12);
        values.put(WaterUsageEntry.COLUMN_NAME_TIMESTAMP, 27);
        db.insert(WaterUsageEntry.TABLE_NAME, null, values);
        values.put(WaterUsageEntry.COLUMN_NAME_VOLUME, 15);
        values.put(WaterUsageEntry.COLUMN_NAME_TIMESTAMP, 28);
        db.insert(WaterUsageEntry.TABLE_NAME, null, values);
        values.put(WaterUsageEntry.COLUMN_NAME_VOLUME, 10);
        values.put(WaterUsageEntry.COLUMN_NAME_TIMESTAMP, 29);
        db.insert(WaterUsageEntry.TABLE_NAME, null, values);
    }


    // Placeholder data used to show the examiner how graphs look with data across multiple days
    private void addSolarInfo() {
        UserDataDbHelper dbHelper = new UserDataDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 15);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 26);
        db.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, values);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 20);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 27);
        db.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, values);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 12);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 28);
        db.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, values);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 34);
        values.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 29);
        db.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 30);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 26);
        db.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, values);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 10);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 27);
        db.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, values);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 40);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 28);
        db.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, values);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 60);
        values.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 29);
        db.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, values);
    }
}
