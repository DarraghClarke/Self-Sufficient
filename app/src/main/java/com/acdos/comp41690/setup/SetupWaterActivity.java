package com.acdos.comp41690.setup;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.acdos.comp41690.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Objects;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager.html
 */
public class SetupWaterActivity extends FragmentActivity {
    final double drainageCoeffient = 0.8;
    final double filterEfficiency = 0.95;
    RadioButton fivePercentButton;
    RadioButton fiveWeeksButton;
    RadioGroup formulaSelector;
    float roofArea;
    float usage;
    int tankSizeInteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_water);
        fivePercentButton = findViewById(R.id.fivePercent);
        fiveWeeksButton = findViewById(R.id.fiveWeeks);
        formulaSelector = findViewById(R.id.toggle);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        final Float roof_area = prefs.getFloat("Roof_Area", 0);

        final EditText waterUsage = findViewById(R.id.water_usage);
        final TextView tankSize = findViewById(R.id.tank_Size);

        formulaSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                       public void onCheckedChanged(RadioGroup group, int checkedId) {

                                                           tankSize.setText(tankSizeCalculator() + " Litres");//5 weeks of water usage
                                                       }
                                                   }
        );
        final EditText harvestableRoofArea = findViewById(R.id.harvestable_roof_area);
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
                                                           if (waterUsage.getText().length() != 0 && harvestableRoofArea.getText().length() != 0) {
                                                               roofArea = Float.valueOf(harvestableRoofArea.getText().toString());

                                                               tankSize.setText(tankSizeCalculator() + " Litres");//5 weeks of yearly rainfall
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
                if (waterUsage.getText().length() != 0 && harvestableRoofArea.getText().length() != 0) {
                    usage = Float.valueOf(waterUsage.getText().toString());

                    tankSize.setText(tankSizeCalculator() + " Litres");//5 weeks of water usage
                }
            }
        });

        //this will submit the users input
        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int result = tankSizeInteger;
//            //To save
                final SharedPreferences prefs = Objects.requireNonNull(getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE));

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Water_Tank_Size", result);
                editor.apply();

                Toast.makeText(getBaseContext(), "Area is: " + result, Toast.LENGTH_SHORT).show();

                setResult(2);
                finish();
            }
        });


    }

    public int tankSizeCalculator() {
        int averageRainFall = 800;
        int weeklyBased = (int) Math.round((roofArea * drainageCoeffient * filterEfficiency * averageRainFall));
        int usageBased = (int) Math.round((usage * 5));


        if (fiveWeeksButton.isChecked()) {
            tankSizeInteger = weeklyBased;
            return weeklyBased;
        } else {
            tankSizeInteger = usageBased;
            return usageBased;
        }
    }
}
