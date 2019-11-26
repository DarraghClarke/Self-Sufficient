package com.acdos.comp41690.setup;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.acdos.comp41690.R;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-15.
 */
public class RoofAreaDialogFragment extends DialogFragment {
    SharedPreferences prefs = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog alertDialog=new Dialog(getActivity());

        alertDialog.setContentView(R.layout.dialog);
        final EditText roof_area = alertDialog.findViewById(R.id.size_dialog);
        roof_area.setHint(R.string.roof_area_input);
        final Button submitButton = alertDialog.findViewById(R.id.submitButtonDialog);

        final Button cancelButton = alertDialog.findViewById(R.id.cancelButtonDialog);
        submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefs = getActivity().getSharedPreferences(
                                getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                        if (roof_area.getText().toString().length() != 0 ){
                            SharedPreferences.Editor editor = prefs.edit();
                            try {
                                editor.putFloat("Roof_Area",Float.valueOf(roof_area.getText().toString()));
                                editor.apply();

                            } catch (Exception ex){
                                Toast.makeText(getContext(), "Please enter a valid value", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(getContext(), "Please enter a value in Litres", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getContext(), "Value successfully entered", Toast.LENGTH_SHORT).show();

                        alertDialog.cancel();
                    }
                });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        getDialog().cancel();
                    }
                });

        return alertDialog;
    }
}
