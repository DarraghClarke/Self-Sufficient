package com.acdos.comp41690.ui.solar;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.data.SolarGenerationContract;
import com.acdos.comp41690.data.SolarUsageContract;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class ElecViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView imageView;
    private SolarPageViewModel pageViewModel;
    private int maxKWh = 5500;
    private int currKWh = 0;

    public static ElecViewFragment newInstance(int index) {
        ElecViewFragment fragment = new ElecViewFragment();
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
            @NonNull final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_electricity_view, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView = Objects.requireNonNull(getView()).findViewById(R.id.imageView);
        GradientDrawable shapeDrawable = (GradientDrawable) imageView.getDrawable();
        shapeDrawable.setSize(100, 70);
        imageView.setPadding(0, 0, 0, 0);
        runUIThread(currKWh);
    }

    private void runUIThread(int value) {
        currKWh = value;
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable();
                TextView percent = Objects.requireNonNull(getView()).findViewById(R.id.percent_val_solar);
                TextView versus = Objects.requireNonNull(getView()).findViewById(R.id.curr_vs_max_solar);

                double ratio = 70.0 / maxKWh;
                int newHeight = (int) (ratio * currKWh);
                gradientDrawable.setSize(100, newHeight);
                imageView.setPadding(0, (70 - newHeight) * 10, 0, 0);

                int percentage = (int) ((newHeight / 70.0) * 100);
                String percentString = percentage + "%";
                percent.setText(percentString);
                String versusString = currKWh + "kWh / " + maxKWh + "kWh";
                versus.setText(versusString);
            }
        });
    }


}