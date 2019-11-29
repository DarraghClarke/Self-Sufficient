package com.acdos.comp41690.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.acdos.comp41690.R;

/**
 * Fragment used in setup to let the user select to activate the solar section, water section or both
 */
public class OnboardingTypeSelectionFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup_onboarding_type, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Switch waterSwitch = view.findViewById(R.id.water_switch);
        final Switch solarSwitch = view.findViewById(R.id.solar_switch);
        Button submit = view.findViewById(R.id.button);

        // Depending on what switches are checked, a different pager activity is loaded
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterSwitch.isChecked() && solarSwitch.isChecked()){
                    Intent intent = new Intent(getActivity(), FullPagerActivity.class);
                    startActivity(intent);
                } else if(waterSwitch.isChecked()){
                    Intent intent = new Intent(getActivity(), WaterOnlyPagerActivity.class);
                    startActivity(intent);
                }else if(solarSwitch.isChecked()){
                    Intent intent = new Intent(getActivity(), SolarOnlyPagerActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"Please select at least one option", Toast.LENGTH_SHORT).show();
                  }
                }
        });
    }
}
