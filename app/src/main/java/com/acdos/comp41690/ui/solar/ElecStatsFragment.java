package com.acdos.comp41690.ui.solar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.data.SolarGenerationContract;
import com.acdos.comp41690.data.SolarUsageContract;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.Instant;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class ElecStatsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SolarPageViewModel pageViewModel;

    public static ElecStatsFragment newInstance(int index) {
        ElecStatsFragment fragment = new ElecStatsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(SolarPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_electricity_stats, container, false);
        //Button

        Button button = rootView.findViewById(R.id.button);
        final Random r = new Random();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder addData = new AlertDialog.Builder(getActivity());
                addData.setTitle("Add Data");
                final EditText input = new EditText(getActivity());
                input.setHint("Enter a new value.");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                addData.setView(input);

                addData.setPositiveButton("Enter",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newValue = input.getText().toString();
                                Integer.parseInt(newValue);

                            }
                        });
                addData.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                addData.show();
            }
        });

        //Graph

        //usage over time
        GraphView lineGraph = (GraphView) rootView.findViewById(R.id.lineGraph);
        DataPoint[] data = usageTimeData();


        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(data);

        lineGraph.setTitle("Electricty Usage Over Time");
        lineGraphSeries.setColor(Color.RED);
        lineGraphSeries.setDrawDataPoints(true);
        lineGraphSeries.setDataPointsRadius(10);
        lineGraphSeries.setThickness(8);

//        // generated over time
        GraphView lineGraphGenerated = (GraphView) rootView.findViewById(R.id.lineGraphGenerated);
        DataPoint[] dataGenerated = generatedTimeData();


        LineGraphSeries<DataPoint> lineGraphSeriesGenerated = new LineGraphSeries<DataPoint>(dataGenerated);


        lineGraphGenerated.setTitle("Generated Electricty Usage Over Time");
        lineGraphSeriesGenerated.setColor(Color.RED);
        lineGraphSeriesGenerated.setDrawDataPoints(true);
        lineGraphSeriesGenerated.setDataPointsRadius(10);
        lineGraphSeriesGenerated.setThickness(8);



        //generated over usage
        GraphView lineGraphGenUse = (GraphView) rootView.findViewById(R.id.lineGraphGenerated);
        DataPoint[] dataGenUse = genUseTimeData();


        LineGraphSeries<DataPoint> lineGraphSeriesGenUse = new LineGraphSeries<DataPoint>(dataGenUse);
//
//
//        lineGraphGenUse.setTitle("Generated Electricty Usage Over Time");
//        lineGraphSeriesGenUse.setColor(Color.RED);
//        lineGraphSeriesGenUse.setDrawDataPoints(true);
//        lineGraphSeriesGenUse.setDataPointsRadius(10);
//        lineGraphSeriesGenUse.setThickness(8);


        lineGraph.addSeries(lineGraphSeries);
        lineGraphGenerated.addSeries(lineGraphSeriesGenerated);
        lineGraphGenUse.addSeries(lineGraphSeriesGenUse);
        return rootView;
    }

    public DataPoint[] usageTimeData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());

        ContentValues value = new ContentValues();
//        ContentValues value_gen = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();
        //test data
        // TODO: change to get data from the user
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 10);
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 10);
//        userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);
//
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 20);
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 20);
//        userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);
//
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 30);
//        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, 30);
//        userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);



        userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value_gen);

        String[] projectionUsage = {
                SolarUsageContract.SolarUsageEntry._ID,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE};


        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarUsageContract.SolarUsageEntry.TABLE_NAME);
//      System.out.println("Count =" + count);
        DataPoint[] values = new DataPoint[count];

        Cursor cUsage = userDb.query(SolarUsageContract.SolarUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);

        int i=0;
        while (cUsage.moveToNext()) {
            Log.d("ElecStatsFragment", cUsage.getLong(0) + ", " + cUsage.getLong(1) + ", " + cUsage.getDouble(2));
//            Log.d("ElecStatsFragment", cGen.getLong(0) + ", " + cGen.getLong(1) + ", " + cGen.getDouble(2));
            DataPoint v = new DataPoint(cUsage.getLong(1), cUsage.getLong(2));
            values[i] = v;
            i++;
        }


        cUsage.close();
        return values;
    }


    public DataPoint[] generatedTimeData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());

        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();

        //test data
        // TODO: change to get data from the user
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 15);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 10);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 25);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 20);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 35);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, 30);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//


        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value_gen);

        String[] projectionGen = {
                SolarGenerationContract.SolarGenerationEntry._ID,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY
        };

        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarUsageContract.SolarUsageEntry.TABLE_NAME);

        DataPoint[] values = new DataPoint[count];

        Cursor cGen = userDb.query(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, projectionGen, null, null, null, null, null);

        int i=0;
        while (cGen.moveToNext()) {
            Log.d("ElecStatsFragment", cGen.getLong(0) + ", " + cGen.getLong(1) + ", " + cGen.getDouble(2));
            DataPoint v = new DataPoint(cGen.getLong(1), cGen.getLong(2));
            values[i] = v;
            i++;
        }



        cGen.close();
        return values;
    }

    public DataPoint[] genUseTimeData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());
        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();

        //test data
        // TODO: change to get data from the user
//
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 15);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 25);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//        value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 35);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);



//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value_gen);

        String[] projectionGen = {
                SolarGenerationContract.SolarGenerationEntry._ID,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY
        };

        UserDataDbHelper userDataDbHelperUsage = new UserDataDbHelper(getActivity());

        ContentValues valueUsage = new ContentValues();
        SQLiteDatabase userDbUsage = userDataDbHelperUsage.getWritableDatabase();

        Cursor cGen = userDb.query(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, projectionGen, null, null, null, null, null);

//        valueUsage.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 10);
//        userDbUsage.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, valueUsage);
//
//        valueUsage.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 20);
//
//        userDbUsage.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, valueUsage);
//
//        valueUsage.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 30);
//        userDbUsage.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, valueUsage);



//        userDbUsage.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, valueUsage);
//        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value_gen);

        String[] projectionUsage = {
                SolarUsageContract.SolarUsageEntry._ID,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE};



//      System.out.println("Count =" + count);
        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarUsageContract.SolarUsageEntry.TABLE_NAME);

        DataPoint[] values = new DataPoint[count];

        Cursor cUsage = userDb.query(SolarUsageContract.SolarUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);
        int i=0;
        while (cGen.moveToNext() && cUsage.moveToNext()) {
            DataPoint v = new DataPoint(cUsage.getLong(2), cGen.getLong(2));

            values[i] = v;
            i++;
        }



        cUsage.close();
        cGen.close();
        return values;
    }


}