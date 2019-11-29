package com.acdos.comp41690.setup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;

/**
 * SetupPagerActivity used for setting up both the solar and water sections of the app
 *
 * Based off https://developer.android.com/training/animation/screen-slide
 */
public class FullPagerActivity extends SetupPagerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiates the pager, adapter and links both together
        CustomPager mPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        setViewPager(mPager);
    }

    /**
     * Internal class used to define the pages available to the user, and the associated order
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new QuestionFragment(Constants.QuestionType.REQUEST_LOCATION_QUESTION);
                case 1:
                    return new QuestionFragment(Constants.QuestionType.ROOF_AREA_QUESTION);
                case 2:
                    return new QuestionFragment(Constants.QuestionType.WATER_TANK_QUESTION);
                case 3:
                    return new SetupSolarFragment();
                case 4:
                    return new SetupConfirmFragment();
                default:
                    return new SetupPageFragment();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}