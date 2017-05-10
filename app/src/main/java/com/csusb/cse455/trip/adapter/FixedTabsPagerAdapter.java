package com.csusb.cse455.trip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

// Adapter for fixed tabs in ViewPager.
public class FixedTabsPagerAdapter extends FragmentPagerAdapter {
    // Fragments representing each view.
    private ArrayList<FragmentTab> mFragments;

    // Constructor accepting FragmentManager and a list of FragmentTab objects.
    public FixedTabsPagerAdapter(FragmentManager fm, ArrayList<FragmentTab> fragments)
    {
        super(fm);
        mFragments = fragments;
    }

    // Returns number of views.
    @Override
    public int getCount()
    {
        return mFragments == null ? 0 : mFragments.size();
    }

    // Gets a specific view fragment.
    @Override
    public Fragment getItem(int position) {
        if (position < 0 || mFragments == null || position > mFragments.size() - 1)
        {
            return null;
        }

        Fragment fragment = null;

        try {
            fragment = (Fragment) mFragments.get(position).getFragmentClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fragment;
    }

    // Sets titles for each view.
    @Override
    public CharSequence getPageTitle(int position) {
        if (position < 0 || mFragments == null || position > mFragments.size() - 1)
        {
            return null;
        }

        return mFragments.get(position).getTitle();
    }
}
