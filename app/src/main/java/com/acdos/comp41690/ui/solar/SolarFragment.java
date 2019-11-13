package com.acdos.comp41690.ui.solar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;

public class SolarFragment extends Fragment {

    private SolarViewModel solarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        solarViewModel =
                ViewModelProviders.of(this).get(SolarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_solar, container, false);
        final TextView textView = root.findViewById(R.id.text_solar);
        solarViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}