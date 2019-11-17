package com.acdos.comp41690.electricity_ui;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;

import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * A placeholder fragment containing a simple view.
 */
public class ElecViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageView imageView;
    private PageViewModel pageViewModel;

    public static ElecViewFragment newInstance(int index) {
        ElecViewFragment fragment = new ElecViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
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
        View view = inflater.inflate(R.layout.fragment_electricity_view, container, false);
        Button button = view.findViewById(R.id.button);
        final Random r = new Random();
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final AlertDialog.Builder addData = new AlertDialog.Builder(getActivity());
                addData.setTitle("Add Data");
                final EditText input = new EditText(getActivity());
                input.setHint("Enter a new value.");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                addData.setView(input);

                addData.setPositiveButton("Enter",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newValue = input.getText().toString();
                                int newNumber = Integer.parseInt(newValue);
                                runUIThread(newNumber);
                            }
                        });
                addData.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                addData.show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView = Objects.requireNonNull(getView()).findViewById(R.id.imageView);
        GradientDrawable shapeDrawable = (GradientDrawable) imageView.getDrawable();
        shapeDrawable.setSize(100, 70);
        imageView.setPadding(0, 0 , 0, 0);
    }

    public void runUIThread(final int value) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable();
                TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.textView);
                for(int i=1; i<=value; i++) {
                    gradientDrawable.setSize(100, i);
                    imageView.setPadding(0, (70-i)*10, 0, 0);
                }

                double k = (value/70.0)*100.0;
                int k2 = (int) k;
                String k3 = k2 + "%";
                textView.setText(k3);
            }
        });
    }
}