package com.acdos.comp41690.setup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

;

/**
 * Fragment used to setup the solar panels with information
 */
public class SetupSolarFragment extends Fragment {

    SetupSolarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_setup_solar, container, false);

        Button confirm = view.findViewById(R.id.submitButton);

        final EditText solar_panel_output = view.findViewById(R.id.solar_panel_output);
        final EditText kwh_rate = view.findViewById(R.id.kwh_rate);

        // If the EditText components have valid inputs, it saves them to SharedPreferences and advances
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();

                if (solar_panel_output.getText().length() != 0 && kwh_rate.getText().length() != 0) {
                    editor.putString(Constants.SharedPrefKeys.SOLAR_PANEL_OUTPUT, solar_panel_output.getText().toString());
                    editor.putString(Constants.SharedPrefKeys.KWH_RATE, kwh_rate.getText().toString());
                    editor.apply();

                    // Move to the next stage of setup
                    ((SetupPagerActivity) getActivity()).moveToNextPage();
                }
            }
        });

        return view;
    }
}
