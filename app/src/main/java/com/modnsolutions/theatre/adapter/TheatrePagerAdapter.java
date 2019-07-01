package com.modnsolutions.theatre.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.fragment.FavoriteMoviesFragment;
import com.modnsolutions.theatre.fragment.FavoriteTVShowsFragment;
import com.modnsolutions.theatre.fragment.WatchlistMoviesFragment;
import com.modnsolutions.theatre.fragment.WatchlistTVShowsFragment;

public class TheatrePagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private boolean isFavorite;

    public TheatrePagerAdapter(FragmentManager fm, int numOfTabs, boolean isFavorite) {
        super(fm);
        mNumOfTabs = numOfTabs;
        this.isFavorite = isFavorite;
    }

    @Override
    public Fragment getItem(int position) {
        if (isFavorite) {
            switch (position) {
                case 0: return new FavoriteMoviesFragment();
                case 1: return new FavoriteTVShowsFragment();
                default: return null;
            }
        } else {
            switch (position) {
                case 0: return new WatchlistMoviesFragment();
                case 1: return new WatchlistTVShowsFragment();
                default: return null;
            }
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
