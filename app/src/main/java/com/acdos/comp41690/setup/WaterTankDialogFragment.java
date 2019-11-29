package com.acdos.comp41690.setup;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

/**
 * Dialog box used by a user to enter existing water tank dimensions
 */
public class WaterTankDialogFragment extends DialogFragment {
    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog alertDialog = new Dialog(getActivity());

        alertDialog.setContentView(R.layout.dialog);

        final EditText tank_size = alertDialog.findViewById(R.id.size_dialog);
        final Button submitButton = alertDialog.findViewById(R.id.submitButtonDialog);
        final Button cancelButton = alertDialog.findViewById(R.id.cancelButtonDialog);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // If the user enters a value, save it in SharedPreferences
                if (tank_size.getText().toString().length() != 0) {
                    SharedPreferences.Editor editor = prefs.edit();

                    try {
                        editor.putString(Constants.SharedPrefKeys.WATER_TANK_SIZE, tank_size.getText().toString());
                        editor.apply();
                        ((SetupPagerActivity) getActivity()).moveToNextPage();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Please enter a valid value", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter a value in Litres", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Value successfully entered", Toast.LENGTH_SHORT).show();

                alertDialog.cancel();
            }
        });

        // If the cancel button is pressed, close the dialog box
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        return alertDialog;
    }
}
