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
        final SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        Button confirm = view.findViewById(R.id.Confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();

                String activityClassName = getActivity().getLocalClassName();

                switch (activityClassName) {
                    case "setup.SolarOnlyPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR, true);
                        break;
                    case "setup.WaterOnlyPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_WATER, true);
                        break;
                    case "setup.FullPagerActivity":
                        editor.putBoolean(Constants.SharedPrefKeys.USING_WATER, true);
                        editor.putBoolean(Constants.SharedPrefKeys.USING_SOLAR, true);
                        break;
                }

                editor.apply();

                //launch app proper
                prefs.edit().putBoolean("firstrun", false).apply();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
