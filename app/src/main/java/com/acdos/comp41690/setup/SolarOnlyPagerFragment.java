package com.acdos.comp41690.setup;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

public class SolarOnlyPagerFragment extends FragmentActivity {
    /**
     * The number of pages (wizard steps) in the solar setup.
     */
    private int NUM_PAGES=1;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);
        pagerAdapter = new SolarOnlyPagerFragment.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
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
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SetupSolarFragment();
                default:
                    return new SetupPageFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
}
