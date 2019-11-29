package com.acdos.comp41690.setup;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

/**
 * Parent Pager activity used for setup to display multiple steps of setup, letting the user navigate
 * linearly through set-up steps without using multiple activities
 *
 * Code based off https://developer.android.com/training/animation/screen-slide
 */
public class SetupPagerActivity extends FragmentActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private CustomPager mPager;

    protected void setViewPager(CustomPager viewPager) {
        this.mPager = viewPager;
    }

    // We override the back button, so it brings you to the previous page if possible instead of
    // to the previous Activities
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    // Method linked to the "Continue" button in the layout
    public void onContinueButtonClick(View v) {
        moveToNextPage();
    }

    // Moves the pager to the next page
    public void moveToNextPage() {
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }
}
