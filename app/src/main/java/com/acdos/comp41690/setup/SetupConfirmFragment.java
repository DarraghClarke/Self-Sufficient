package com.acdos.comp41690.setup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.MainActivity;
import com.acdos.comp41690.R;

public class SetupConfirmFragment extends Fragment {

    SetupConfirmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.setup_confirmation_fragment, container, false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Button confirm = view.findViewById(R.id.Confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launch app proper
                prefs.edit().putBoolean(Constants.SharedPrefKeys.FIRST_RUN, false).apply();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
