package com.modnsolutions.theatre.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.fragment.MoviesLatestFragment;
import com.modnsolutions.theatre.fragment.MoviesNowPlayingFragment;
import com.modnsolutions.theatre.fragment.MoviesPopularFragment;
import com.modnsolutions.theatre.fragment.MoviesTopRatedFragment;
import com.modnsolutions.theatre.fragment.MoviesUpcomingFragment;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MoviesPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new MoviesLatestFragment();
            case 1: return new MoviesNowPlayingFragment();
            case 2: return new MoviesPopularFragment();
            case 3: return new MoviesTopRatedFragment();
            case 4: return new MoviesUpcomingFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
