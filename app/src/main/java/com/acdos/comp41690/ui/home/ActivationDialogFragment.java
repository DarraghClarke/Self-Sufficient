package com.acdos.comp41690.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.acdos.comp41690.setup.SolarOnlyPagerActivity;
import com.acdos.comp41690.setup.WaterOnlyPagerActivity;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-27.
 */
public class ActivationDialogFragment extends DialogFragment {
    String sectionName = "";

    ActivationDialogFragment(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("The " + sectionName + "section is disabled! Would you like to go to the setup screen to activate it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (sectionName.equals("water")) {
                            Intent intent = new Intent(getContext(), WaterOnlyPagerActivity.class);
                            startActivity(intent);
                        } else if (sectionName.equals("solar")) {
                            Intent intent = new Intent(getContext(), SolarOnlyPagerActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

