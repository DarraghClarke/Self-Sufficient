package com.acdos.comp41690.setup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.acdos.comp41690.R;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager.html
 */
public class SetupWaterFragment extends Fragment {
    final double drainageCoeffient=0.8;
    final double filterEfficiency=0.95;
    RadioButton fivePercentButton;
    RadioButton fiveWeeksButton;
    RadioGroup formulaSelector;
    float roofArea;
    float usage;
    SetupWaterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_setup_water, container, false);
        fivePercentButton = rootView.findViewById(R.id.fivePercent);
        fiveWeeksButton = rootView.findViewById(R.id.fiveWeeks);

        formulaSelector = rootView.findViewById(R.id.toggle);



        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        final Float roof_area = prefs.getFloat("Roof_Area", 0);

        final EditText waterUsage= rootView.findViewById(R.id.water_usage);
        final TextView tankSize= rootView.findViewById(R.id.tank_Size);

        formulaSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                tankSize.setText(tankSizeCalculator() + " Litres");//5 weeks of water usage
            }
        }
        );
        final EditText harvestableRoofArea= rootView.findViewById(R.id.harvestable_roof_area);
        harvestableRoofArea.setText(roof_area.toString());
        harvestableRoofArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if ( waterUsage.getText().length() != 0 && harvestableRoofArea.getText().length() != 0 ){
                    roofArea = Float.valueOf(harvestableRoofArea.getText().toString());

                  tankSize.setText( tankSizeCalculator() + " Litres");//5 weeks of yearly rainfall
                }
            }
        }
        );

        waterUsage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if ( waterUsage.getText().length() != 0 && harvestableRoofArea.getText().length() != 0 ){
                    usage = Float.valueOf(waterUsage.getText().toString());

                    tankSize.setText( tankSizeCalculator() + " Litres");//5 weeks of water usage
                }
            }
        });

        //this will submit the users input
        final Button submitButton = rootView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        return rootView;
    }
    public int tankSizeCalculator() {
        int averageRainFall=800;
        int weeklyBased = (int) Math.round((roofArea*drainageCoeffient*filterEfficiency*averageRainFall));
        int usageBased = (int) Math.round((usage * 5));


        if ( fiveWeeksButton.isChecked() ) {
            System.out.println("used");
            return weeklyBased;
        } else {
            System.out.println("tried" + usageBased);
            return usageBased;
        }
    }
}
