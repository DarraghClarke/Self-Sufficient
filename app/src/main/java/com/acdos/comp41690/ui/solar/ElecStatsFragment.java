package com.acdos.comp41690.ui.solar;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.data.SolarGenerationContract;
import com.acdos.comp41690.data.SolarUsageContract;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

        //Graph
        try {
            createGraph(rootView);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //usage over time

        return rootView;
    }

    public void createGraph(View rootView) throws ParseException {
        GraphView lineGraph = rootView.findViewById(R.id.lineGraph);
        GridLabelRenderer glr = lineGraph.getGridLabelRenderer();
        glr.setPadding(50);
        DataPoint[] data = usageTimeData();


            if (!data.equals(null)) {
                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(data);


                lineGraph.setTitle("Electricty Usage Over Time");
                lineGraphSeries.setColor(Color.RED);
                lineGraphSeries.setDrawDataPoints(true);
                lineGraphSeries.setDataPointsRadius(10);
                lineGraphSeries.setThickness(8);

              GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();
            gridLabel.setHorizontalAxisTitle("Time (in days)");
            gridLabel.setVerticalAxisTitle("Electricity Usage");
                lineGraphSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), " Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
                    }
                });
                lineGraph.addSeries(lineGraphSeries);


        }
        GraphView lineGraphGenerated = rootView.findViewById(R.id.lineGraphGenerated);
        GridLabelRenderer renderer = lineGraphGenerated.getGridLabelRenderer();
        renderer.setPadding(50);
        DataPoint[] dataGenerated = generatedTimeData();


        if (!data.equals(null)) {
            LineGraphSeries<DataPoint> lineGraphSeriesGenerated = new LineGraphSeries<DataPoint>(dataGenerated);


            lineGraphGenerated.setTitle("Generated Electricity Usage Over Time");
            lineGraphSeriesGenerated.setColor(Color.RED);
            lineGraphSeriesGenerated.setDrawDataPoints(true);
            lineGraphSeriesGenerated.setDataPointsRadius(10);
            lineGraphSeriesGenerated.setThickness(8);
            GridLabelRenderer gridLabel = lineGraphGenerated.getGridLabelRenderer();
            gridLabel.setVerticalAxisTitle("Electricity Generated");
//          lineGraph.getGridLabelRenderer().setNumHorizontalLabels(4);
            gridLabel.setHorizontalAxisTitle("Time (in days)");


            lineGraphSeriesGenerated.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(getActivity(), " Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
                }
            });
            lineGraphGenerated.addSeries(lineGraphSeriesGenerated);
        }
//


    }



    public DataPoint[] usageTimeData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());


        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();


        String[] projectionUsage = {
                SolarUsageContract.SolarUsageEntry._ID,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE};


        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarUsageContract.SolarUsageEntry.TABLE_NAME);
        DataPoint[] values = new DataPoint[count];

        Cursor cUsage = userDb.query(SolarUsageContract.SolarUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);

        int i=0;
        while (cUsage.moveToNext()) {
            Log.d("ElecStatsFragment", cUsage.getLong(0) + ", " + cUsage.getLong(1) + ", " + cUsage.getDouble(2));
            DataPoint v = new DataPoint(cUsage.getLong(1), cUsage.getLong(2));
            values[i] = v;
            i++;
        }



        cUsage.close();
        return values;
    }


    public DataPoint[] generatedTimeData() throws ParseException {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());


        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();
        final SimpleDateFormat sdf = new SimpleDateFormat("ddMM");

        String[] projectionGen = {
                SolarGenerationContract.SolarGenerationEntry._ID,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY
        };

        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarGenerationContract.SolarGenerationEntry.TABLE_NAME);

        DataPoint[] values = new DataPoint[count];

        Cursor cGen = userDb.query(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, projectionGen, null, null, null, null, null);

        int i=0;

            while (cGen.moveToNext()) {
                Log.d("ElecStatsFragment", cGen.getLong(0) + ", " + cGen.getLong(1) + ", " + cGen.getDouble(2));
                DataPoint v = new DataPoint((cGen.getLong(1)), cGen.getLong(2));
                values[i] = v;
                i++;

        }



        cGen.close();
        return values;
    }

    public DataPoint[] genUseTimeData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();



        String[] projectionGen = {
                SolarGenerationContract.SolarGenerationEntry._ID,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,
                SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY
        };




        Cursor cGen = userDb.query(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, projectionGen, null, null, null, null, null);

        String[] projectionUsage = {
                SolarUsageContract.SolarUsageEntry._ID,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,
                SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE};

        
        int count = (int) DatabaseUtils.queryNumEntries(userDb, SolarUsageContract.SolarUsageEntry.TABLE_NAME);

        DataPoint[] values = new DataPoint[count];
        Long[] xAxis = new Long[count];
        Cursor cUsage = userDb.query(SolarUsageContract.SolarUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);
        int i=0;
        int j=0;
        while (cUsage.moveToNext()){
            Long value = cUsage.getLong(2);
          xAxis[j] = value;
            j++;
        }
        Arrays.sort(xAxis);
        while (cGen.moveToNext()) {
            DataPoint v = new DataPoint(xAxis[i], cGen.getLong(2));

            values[i] = v;
            i++;
        }


        cUsage.close();
        cGen.close();
        return values;
    }


}