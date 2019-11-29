package com.acdos.comp41690.ui.rain;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;
import com.acdos.comp41690.RainActivity;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.acdos.comp41690.data.WaterUsageContract;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static com.acdos.comp41690.Constants.SharedPrefKeys.WATER_TANK_SIZE;
import static java.lang.Thread.sleep;

public class RainViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView imageView;
    private RainPageViewModel pageViewModel;
    private int maxLitre = 1;
    private int defaultLitre = 0;
    private int currLitre= defaultLitre;
    SharedPreferences prefs = null;

    public static RainViewFragment newInstance(int index) {
        RainViewFragment fragment = new RainViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(RainPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rain_view, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView = Objects.requireNonNull(getView()).findViewById(R.id.imageView8);
        GradientDrawable shapeDrawable = (GradientDrawable) imageView.getDrawable();
        shapeDrawable.setSize(90, 80);
        imageView.setPadding(0, 0 , 0, 0);
        maxLitre = getMaxLitre();
        currLitre = getCurrLitre();
        runUIThread(currLitre);
    }

    //Ensure there isn't any delay when updating the UI
    private void runUIThread(int value) {
        currLitre = value;
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable();
                TextView percent = Objects.requireNonNull(getView()).findViewById(R.id.percent_val_rain);
                TextView versus =  Objects.requireNonNull(getView()).findViewById(R.id.curr_vs_max_rain);

                double ratio = 80.0/maxLitre;
                int newHeight = (int) (ratio*currLitre);
                gradientDrawable.setSize(90, newHeight);
                imageView.setPadding(0, (80-newHeight)*10, 0, 0);

                int percentage = (int) ((newHeight/80.0)*100);
                String percentString = percentage + "%";
                percent.setText(percentString);
                String versusString = currLitre + "L / " + maxLitre + "L";
                versus.setText(versusString);
            }
        });
    }

    //Get current volume from the database
    private int getCurrLitre() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());
        SQLiteDatabase userDb = userDataDbHelper.getReadableDatabase();

        String[] projectionUsage = { WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME };
        Cursor cursor = userDb.query(WaterUsageContract.WaterUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);
        int val;
        if(cursor.moveToLast()) {
            val = cursor.getInt(0);
        }
        else {
            Toast.makeText(getContext(), "DB_ERROR: Using default value.", Toast.LENGTH_SHORT).show();
            val = defaultLitre;
        }

        if(val > maxLitre) {
            addToDatabase(currLitre);
            Toast.makeText(getContext(), "DB_ERROR: Current volume must be smaller than max volume.", Toast.LENGTH_SHORT).show();
            val = currLitre;
        }

        if(val < 0) {
            addToDatabase(defaultLitre);
            Toast.makeText(getContext(), "DB_ERROR: Current volume must be bigger than zero.", Toast.LENGTH_SHORT).show();
            val = defaultLitre;
        }

        cursor.close();
        return val;
    }

    //Get tank size from the SharedPreferences
    private int getMaxLitre() {
        prefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        String tankSizeStr = prefs.getString(Constants.SharedPrefKeys.WATER_TANK_SIZE, "0");
        int tankSize = (int)Float.parseFloat(tankSizeStr);

        if(tankSize == 0) {
            currLitre = defaultLitre;
        }
        if(tankSize < currLitre) {
            Toast.makeText(getContext(), "DB_ERROR: Max volume must be smaller than current volume.", Toast.LENGTH_SHORT).show();
            tankSize = defaultLitre;
            currLitre = defaultLitre;
        }
        return tankSize;
    }

    //Add current volume to the database
    private void addToDatabase(int input) {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getContext());
        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();
        Date timestamp = RainActivity.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");

        value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME, input);
        value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_TIMESTAMP, sdf.format(timestamp) );
        userDb.insert(WaterUsageContract.WaterUsageEntry.TABLE_NAME, null, value);
    }
}
