package com.acdos.comp41690.ui.rain;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;

import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * A placeholder fragment containing a simple view.
 */
public class RainViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView imageView;
    private RainPageViewModel pageViewModel;
    private int maxLitre = 5500;
    private int currLitre= 5500;

    public static RainViewFragment newInstance(int index) {
        RainViewFragment fragment = new RainViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(RainPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rain_view, container, false);
//        Button solar_button = view.findViewById(R.id.rain_button);
//        solar_button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                final AlertDialog.Builder addData = new AlertDialog.Builder(getActivity());
//                addData.setTitle("Current: " + currLitre + "L");
//                final EditText input = new EditText(getActivity());
//                input.setHint("Enter a new value!");
//                input.setInputType(InputType.TYPE_CLASS_NUMBER);
//                addData.setView(input);
//
//                addData.setPositiveButton("Enter",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String newValue = input.getText().toString();
//                                int newNumber = Integer.parseInt(newValue);
//
//                                if(newNumber > maxLitre) {
//                                    Toast toast = Toast.makeText(getContext(), "Cannot have more than max, (" + maxLitre + "L)!", Toast.LENGTH_SHORT);
//                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
//                                    View viewToast = toast.getView();
//                                    viewToast.setBackgroundResource(R.color.colorDarkToast);
//                                    toast.show();
//                                }
//                                else {
//                                    runUIThread(newNumber);
//                                }
//                            }
//                        });
//                addData.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                addData.show();
//            }
//        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView = Objects.requireNonNull(getView()).findViewById(R.id.imageView8);
        GradientDrawable shapeDrawable = (GradientDrawable) imageView.getDrawable();
        shapeDrawable.setSize(90, 80);
        imageView.setPadding(0, 0 , 0, 0);
        runUIThread(currLitre);
    }

    private void runUIThread(int value) {
        currLitre = value;
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable();
                TextView percent = Objects.requireNonNull(getView()).findViewById(R.id.percent_val_rain);
                TextView versus =  Objects.requireNonNull(getView()).findViewById(R.id.curr_vs_max_rain);

                double ratio = 80.0/maxLitre;
                int newHeight = (int) (ratio*currLitre);
                gradientDrawable.setSize(90, newHeight);
                imageView.setPadding(0, (80-newHeight)*10, 0, 0);

                int percentage = (int) ((newHeight/80.0)*100);
                String percentString = percentage + "%";
                percent.setText(percentString);
                String versusString = currLitre + "L / " + maxLitre + "L";
                versus.setText(versusString);
            }
        });
    }
}
