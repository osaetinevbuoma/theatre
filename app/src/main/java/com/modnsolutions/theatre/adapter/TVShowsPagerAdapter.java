package com.modnsolutions.theatre.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.fragment.TVShowsAiringTodayFragment;
import com.modnsolutions.theatre.fragment.TVShowsOnTheAirFragment;
import com.modnsolutions.theatre.fragment.TVShowsPopularFragment;
import com.modnsolutions.theatre.fragment.TVShowsTopRatedFragment;

public class TVShowsPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public TVShowsPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TVShowsPopularFragment();
            case 1: return new TVShowsTopRatedFragment();
            case 2: return new TVShowsAiringTodayFragment();
            case 3: return new TVShowsOnTheAirFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
