package com.modnsolutions.theatre.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.fragment.SearchMoviesFragment;
import com.modnsolutions.theatre.fragment.SearchTVShowsFragment;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public SearchPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SearchMoviesFragment();
            case 1: return new SearchTVShowsFragment();
            default: return null;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
