package com.acdos.comp41690.ui.solar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.data.SolarTrackingContract;
import com.acdos.comp41690.data.SolarTrackingDbHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.Instant;

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
        GraphView lineGraph = (GraphView) rootView.findViewById(R.id.lineGraph);
        // styling series


        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        lineGraph.setTitle("Random Line Graph");
        lineGraphSeries.setColor(Color.YELLOW);
        lineGraphSeries.setDrawDataPoints(true);
        lineGraphSeries.setDataPointsRadius(10);
        lineGraphSeries.setThickness(8);
//        lineGraph.getViewport().setScalable(true);
        
        GraphView barGraph = (GraphView) rootView.findViewById(R.id.barGraph);
        BarGraphSeries<DataPoint> barGraphSeries = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        barGraphSeries.setColor(Color.YELLOW);
        barGraphSeries.setSpacing(50);
        barGraph.setTitle("Random Bar Graph");


        lineGraph.addSeries(lineGraphSeries);
        barGraph.addSeries(barGraphSeries);
        return rootView;
    }
}