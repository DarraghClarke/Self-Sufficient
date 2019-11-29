package com.acdos.comp41690.setup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Custom ViewPager class that disables swiping between pages. Used in the setup PagerActivity.
 */
public class CustomPager extends ViewPager {

    public CustomPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // We override the methods, preventing any page changes from occurring
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    // We override the methods, preventing any page changes from occurring
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
