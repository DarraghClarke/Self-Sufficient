package com.acdos.comp41690.ui.rain;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.acdos.comp41690.ui.solar.ElecStatsFragment;
import com.acdos.comp41690.ui.solar.ElecViewFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class RainSectionsPagerAdapter extends FragmentPagerAdapter {

    public RainSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if(position == 0) {
            return RainViewFragment.newInstance(position + 1);
        }
        else {
            return RainStatsFragment.newInstance(position + 1);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
