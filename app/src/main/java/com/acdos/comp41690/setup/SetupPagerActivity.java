package com.acdos.comp41690.setup;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Oisin Quinn (@oisin1001) on 2019-11-11.
 * Based off https://developer.android.com/training/animation/screen-slide
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

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void onContinueButtonClick(View v) {
        moveToNextPage();
    }

    public void moveToNextPage() {
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }
}
