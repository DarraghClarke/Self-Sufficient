package com.acdos.comp41690.setup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

public class OnboardingTypeSelectionFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_setup_onboarding_type, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageView imageView = view.findViewById(R.id.question_image);
        TextView textView = view.findViewById(R.id.question_text);



               //imageView.setImageResource(R.drawable.roof);
               //textView.setText(R.string.onboarding_question);

        final Switch waterSwitch = view.findViewById(R.id.water_switch);
        final Switch solarSwitch = view.findViewById(R.id.solar_switch);
        Button submit = view.findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();

                if (waterSwitch.isChecked() && solarSwitch.isChecked()){
                    editor.putBoolean(Constants.SharedPrefKeys.USING_WATER,true);
                    editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR,true);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), FullPagerActivity.class);
                    startActivity(intent);
                } else if(waterSwitch.isChecked()){
                    editor.putBoolean(Constants.SharedPrefKeys.USING_WATER,true);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), WaterOnlyPagerActivity.class);
                    startActivity(intent);
                }else if(solarSwitch.isChecked()){
                    editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR,true);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), SolarOnlyPagerActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"Please select at least one option", Toast.LENGTH_SHORT).show();
                  }
                }

        });

    }

}
