package com.acdos.comp41690.setup;

import android.app.Activity;
import android.content.Context;
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

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

import java.util.Objects;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager.html
 */
public class QuestionFragment extends Fragment {
    private final String questionType;
    private final int ACTIVITY_RETURN_CODE = 1;
   // final SharedPreferences prefs =  getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

    QuestionFragment(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_setup_question, container, false);
        return rootView;
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

        Button noButton = view.findViewById(R.id.no_button);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (questionType) {
                    case Constants.QuestionType.ROOF_AREA_QUESTION:
                        Intent intent = new Intent(getContext(), MapActivity.class);
                        startActivityForResult(intent, ACTIVITY_RETURN_CODE);
                        break;
                    case Constants.QuestionType.WATER_TANK_QUESTION:
                        ((SetupPagerActivity) getActivity()).moveToNextPage();
                }
            }
        });

        Button yesButton = view.findViewById(R.id.yes_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (questionType) {
                    case Constants.QuestionType.ROOF_AREA_QUESTION:
                        ((SetupPagerActivity) getActivity()).moveToNextPage();
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
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            double result = data.getDoubleExtra("area", -1.0f);
//            //To save
            final SharedPreferences prefs =  Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("Roof_Area",(float) result);
            editor.apply();


            Toast.makeText(getActivity(), "Area is: " + result, Toast.LENGTH_SHORT).show();
            ((SetupPagerActivity) getActivity()).moveToNextPage();
        }
    }


}
