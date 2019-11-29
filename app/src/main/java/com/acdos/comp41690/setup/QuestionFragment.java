package com.acdos.comp41690.setup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager.html
 */
public class QuestionFragment extends Fragment {
    private final String questionType;
    private final int ACTIVITY_RETURN_CODE = 1;

    QuestionFragment(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (questionType.equals(Constants.QuestionType.REQUEST_LOCATION_QUESTION)) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_setup_location, container, false);
            return rootView;
        } else {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_setup_question, container, false);
            return rootView;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageView imageView = view.findViewById(R.id.question_image);
        TextView textView = view.findViewById(R.id.question_text);

        switch (questionType) {
            case Constants.QuestionType.WATER_TANK_QUESTION:
                imageView.setImageResource(R.drawable.water_tank);
                textView.setText(R.string.own_water_tank_question);
                break;
            case Constants.QuestionType.ROOF_AREA_QUESTION:
                imageView.setImageResource(R.drawable.roof);
                textView.setText(R.string.roof_area_question);
                break;
        }

        if (questionType.equals(Constants.QuestionType.REQUEST_LOCATION_QUESTION)) {
            Button setLocationButton = view.findViewById(R.id.set_location_button);
            setLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SetLocationMapActivity.class);
                    startActivityForResult(intent, 3);
                }
            });
            return;
        }

        Button noButton = view.findViewById(R.id.no_button);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (questionType) {
                    case Constants.QuestionType.ROOF_AREA_QUESTION:
                        Intent intent = new Intent(getContext(), RoofAreaMapActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    case Constants.QuestionType.WATER_TANK_QUESTION:
                        Intent water = new Intent(getContext(), SetupWaterActivity.class);
                        startActivityForResult(water, 2);
                }
            }
        });

        Button yesButton = view.findViewById(R.id.yes_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (questionType) {
                    case Constants.QuestionType.ROOF_AREA_QUESTION:
                        DialogFragment roofFragment = new RoofAreaDialogFragment();
                        roofFragment.show(getFragmentManager(), "roof_area");
                        break;
                    case Constants.QuestionType.WATER_TANK_QUESTION:
                        DialogFragment newFragment = new WaterTankDialogFragment();
                        newFragment.show(getFragmentManager(), "water_tank");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {//1 for roof_area
            double result = data.getDoubleExtra("area", -1.0f);

//            //To save
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constants.SharedPrefKeys.ROOF_AREA, String.valueOf(result));
            editor.apply();

            ((SetupPagerActivity) getActivity()).moveToNextPage();
        } else if(requestCode == 2 && resultCode == 2){// 2 for next page
            ((SetupPagerActivity) getActivity()).moveToNextPage();
        } else if(requestCode == 3 && resultCode == 3){// 3 for next page
            double latitude = data.getDoubleExtra("latitude", -1);
            double longitude = data.getDoubleExtra("longitude", -1);
            Toast.makeText(getActivity(), "Longitude: " + longitude + ", latitude: " + latitude, Toast.LENGTH_SHORT).show();

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();

            editor.putFloat("longitude", (float) longitude);
            editor.putFloat("latitude", (float) latitude);
            editor.apply();

            ((SetupPagerActivity) getActivity()).moveToNextPage();
        }
    }


}
