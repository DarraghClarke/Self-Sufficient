package com.acdos.comp41690.ui.solar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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

    private PageViewModel pageViewModel;

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
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
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
        GraphView lineGraph = (GraphView) rootView.findViewById(R.id.lineGraph);
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(data());


        //usage over time
        // generated over time
        //generated over usage

        lineGraph.setTitle("Random Line Graph");
        lineGraphSeries.setColor(Color.YELLOW);
        lineGraphSeries.setDrawDataPoints(true);
        lineGraphSeries.setDataPointsRadius(10);
        lineGraphSeries.setThickness(8);

//
//        GraphView barGraph = (GraphView) rootView.findViewById(R.id.barGraph);
//        BarGraphSeries<DataPoint> barGraphSeries = new BarGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//
//        barGraphSeries.setColor(Color.YELLOW);
//        barGraphSeries.setSpacing(50);
//        barGraph.setTitle("Random Bar Graph");
//
//
        lineGraph.addSeries(lineGraphSeries);
//      barGraph.addSeries(barGraphSeries);
        return rootView;
    }

    public DataPoint[] data() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());

        ContentValues value = new ContentValues();
        ContentValues value_gen = new ContentValues();
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 10);
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond());
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 20);
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond()+10);
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, 30);
        value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond()+20);


        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 15);
        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond());
        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 25);
        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond()+10);
        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, 35);
        value_gen.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond()+20);


        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();


        userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);
        userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value_gen);

        String[] projectionUsage = {
                SolarUsageContract.SolarUsageEntry._ID,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE};

        String[] projectionGen = {
                SolarGenerationContract.SolarGenerationEntry._ID,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY
        };


        DataPoint[] values = new DataPoint[10];

        Cursor cGen = userDb.query(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, projectionGen, null, null, null, null, null);
        Cursor cUsage = userDb.query(SolarUsageContract.SolarUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);

        int i=0;
        while (cUsage.moveToNext() && cGen.moveToNext()) {
            Log.d("ElecStatsFragment", cUsage.getLong(0) + ", " + cUsage.getLong(1) + ", " + cUsage.getDouble(2));
            Log.d("ElecStatsFragment", cGen.getLong(0) + ", " + cGen.getLong(1) + ", " + cGen.getDouble(2));

            DataPoint v = new DataPoint(cUsage.getLong(1), cGen.getLong(1));
            values[i] = v;
            i++;
        }

        cUsage.close();
        cGen.close();
        return values;
    }


}