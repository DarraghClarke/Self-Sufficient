package com.acdos.comp41690.ui.rain;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.acdos.comp41690.data.WaterUsageContract;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

/**
 * Fragment for displaying stats for the rain section of the app
 */
public class RainStatsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RainPageViewModel pageViewModel;

    public static RainStatsFragment newInstance(int index) {
        RainStatsFragment fragment = new RainStatsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragement_rain_stats, container, false);

        createGraph(root);

        return root;
    }

    // Creates and populates the graph
    private void createGraph(View root) {
        GraphView lineGraph = root.findViewById(R.id.lineGraph);
        GridLabelRenderer glr = lineGraph.getGridLabelRenderer();
        // Ensures all of the graph is displayed
        glr.setPadding(50);

        DataPoint[] data = waterUsage();

        if(data != null) {
            LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(data);

            lineGraph.setTitle("Water Usage Over Time");
            lineGraphSeries.setColor(Color.RED);
            lineGraphSeries.setDrawDataPoints(true);
            lineGraphSeries.setDataPointsRadius(10);
            lineGraphSeries.setThickness(8);
            GridLabelRenderer gridLabel = lineGraph.getGridLabelRenderer();

            gridLabel.setHorizontalAxisTitle("Time (in days)");
            gridLabel.setVerticalAxisTitle("Water Input (liters)");

            lineGraphSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(getActivity(), dataPoint.getX() + " litres", Toast.LENGTH_SHORT).show();
                }
            });
            lineGraph.addSeries(lineGraphSeries);
            lineGraph.getViewport().setScalable(true);
        }
    }

    private DataPoint[] waterUsage() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());

        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();

        String[] projectionUsage = {
                WaterUsageContract.WaterUsageEntry._ID,
                WaterUsageContract.WaterUsageEntry.COLUMN_NAME_TIMESTAMP,
                WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME};


        int count = (int) DatabaseUtils.queryNumEntries(userDb, WaterUsageContract.WaterUsageEntry.TABLE_NAME);
        DataPoint[] values = new DataPoint[count];

        Cursor cUsage = userDb.query(WaterUsageContract.WaterUsageEntry.TABLE_NAME, projectionUsage, null,
                null, null, null, null);

        int i=0;
        while (cUsage.moveToNext()) {
            Log.d("RainStatsFragment", cUsage.getLong(0) + ", " + cUsage.getLong(1) + ", " + cUsage.getDouble(2));
            DataPoint v = new DataPoint(((cUsage.getLong(1))), cUsage.getLong(2));
            values[i] = v;
            i++;
        }


        cUsage.close();
        return values;
    }

}
