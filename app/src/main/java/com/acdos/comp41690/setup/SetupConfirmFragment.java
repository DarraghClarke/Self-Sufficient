package com.acdos.comp41690.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.acdos.comp41690.R;

public class SetupConfirmFragment extends Fragment {

    SetupConfirmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.setup_confirmation_fragment, container, false);
        final SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        Button confirm = view.findViewById(R.id.Confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launch app proper
                prefs.edit().putBoolean("firstrun", false).apply();
                Intent intent = new Intent(getContext(), InitialSetupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
