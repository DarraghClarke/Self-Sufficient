package com.acdos.comp41690.setup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

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
    float tankSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_water);
        fivePercentButton = findViewById(R.id.fivePercent);
        fiveWeeksButton = findViewById(R.id.fiveWeeks);
        formulaSelector = findViewById(R.id.toggle);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      
        final Float roof_area = prefs.getFloat(Constants.SharedPrefKeys.ROOF_AREA, 0);

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
                float result = SetupWaterActivity.this.tankSize;
//            //To save
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SetupWaterActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.SharedPrefKeys.WATER_TANK_SIZE, String.valueOf(result));
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
            tankSize = weeklyBased;
            return weeklyBased;
        } else {
            tankSize = usageBased;
            return usageBased;
        }
    }
}
