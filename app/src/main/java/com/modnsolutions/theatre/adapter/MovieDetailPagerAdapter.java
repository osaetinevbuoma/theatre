package com.modnsolutions.theatre.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.fragment.MovieInfoFragment;
import com.modnsolutions.theatre.fragment.MovieReviewsFragment;
import com.modnsolutions.theatre.fragment.MovieSimilarFragment;

public class MovieDetailPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private int mNumOfTabs;

    public MovieDetailPagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
        super(fm);
        mContext = context;
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new MovieInfoFragment();
            case 1: return new MovieReviewsFragment();
            case 2: return new MovieSimilarFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return mContext.getResources().getString(R.string.tab_info);
            case 1: return mContext.getResources().getString(R.string.tab_reviews);
            case 2: return mContext.getResources().getString(R.string.tab_similar_movies);
            default: return null;
        }
    }
}
