package com.acdos.comp41690.setup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

public class FullPagerActivity extends SetupPagerActivity {
    /**
     * The number of pages (wizard steps) in the full setup process.
     */
    private int NUM_PAGES=4;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);
        pagerAdapter = new FullPagerActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        setViewPager(mPager);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new QuestionFragment(Constants.QuestionType.ROOF_AREA_QUESTION);
                case 1:
                    return new QuestionFragment(Constants.QuestionType.WATER_TANK_QUESTION);
                case 2:
                    return new SetupSolarFragment();
                 case 3:
                    return new SetupConfirmFragment();
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
