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

/**
 * The last fragment used in the setup, stating that setup is complete
 */
public class SetupConfirmFragment extends Fragment {

    SetupConfirmFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.setup_confirmation_fragment, container, false);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Button confirm = view.findViewById(R.id.Confirm);

        // When the user confirms that they are finished setup, we mark setup as complete in SharedPreferences
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPreferences.Editor editor = prefs.edit();

               // We use the class name of the activity to know exactly how much of the app is completed
                String activityClassName = getActivity().getLocalClassName();

                // I know hardcoding the names isn't best practice, but this was the best
                // solution I could find
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

                // Saves that general setup is completed
                prefs.edit().putBoolean(Constants.SharedPrefKeys.FIRST_RUN, false).apply();

                // Starts MainActivity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
