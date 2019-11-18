package com.acdos.comp41690.ui.rain;

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

public class RainFragment extends Fragment {

    private RainViewModel rainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rainViewModel =
                ViewModelProviders.of(this).get(RainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rain, container, false);
        final TextView textView = root.findViewById(R.id.text_rain);
        rainViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}