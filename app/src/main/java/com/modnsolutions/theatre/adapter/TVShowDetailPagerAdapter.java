package com.modnsolutions.theatre.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;
import com.modnsolutions.theatre.fragment.TVShowReviewsFragment;
import com.modnsolutions.theatre.fragment.TVShowSeasonsFragment;

public class TVShowDetailPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private int mNumOfTabs;

    public TVShowDetailPagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
        super(fm);
        mContext = context;
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TVShowInfoFragment();
            case 1: return new TVShowReviewsFragment();
            case 2: return new TVShowSeasonsFragment();
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
            case 2: return mContext.getResources().getString(R.string.tab_seasons);
            default: return null;
        }
    }
}
