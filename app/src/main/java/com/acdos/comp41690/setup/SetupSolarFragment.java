package com.acdos.comp41690.setup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager.html
 */
public class SetupSolarFragment extends Fragment {

    SetupSolarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_setup_solar, container, false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());



        Button confirm = view.findViewById(R.id.submitButton);

        final EditText solar_panel_output = view.findViewById(R.id.solar_panel_output);
        final EditText kwh_rate = view.findViewById(R.id.kwh_rate);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                if (solar_panel_output.getText().length()!=0 && kwh_rate.getText().length()!=0) {
                    editor.putFloat(Constants.SharedPrefKeys.SOLAR_PANEL_OUTPUT,Float.valueOf(solar_panel_output.getText().toString()));
                    editor.putFloat(Constants.SharedPrefKeys.KWH_RATE,Float.valueOf(kwh_rate.getText().toString()));
                }
                Toast.makeText(getContext(),"Solar output "+Float.valueOf(solar_panel_output.getText().toString())
                        +" and kwh rate of "+ Float.valueOf(kwh_rate.getText().toString()),Toast.LENGTH_SHORT).show();
                ((SetupPagerActivity) getActivity()).moveToNextPage();
            }
        });

        return view;
    }
}
