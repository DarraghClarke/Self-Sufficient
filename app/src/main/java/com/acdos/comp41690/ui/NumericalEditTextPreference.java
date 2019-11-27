package com.acdos.comp41690.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-27.
 */
public class NumericalEditTextPreference extends EditTextPreference {
    public NumericalEditTextPreference(Context context) {
        super(context);
        setKey("roof_area");
    }

    public NumericalEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setKey("roof_area");
    }

    public NumericalEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setKey("roof_area");
    }

    public NumericalEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setKey("roof_area");
    }
}
