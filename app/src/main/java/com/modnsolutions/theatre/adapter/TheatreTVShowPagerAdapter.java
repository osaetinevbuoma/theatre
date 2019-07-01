package com.modnsolutions.theatre.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.fragment.TheatreTVShowDetailsFragment;
import com.modnsolutions.theatre.fragment.TheatreTVShowDetailsSeasonsFragment;

public class TheatreTVShowPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Context mContext;

    public TheatreTVShowPagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TheatreTVShowDetailsFragment();
            case 1: return new TheatreTVShowDetailsSeasonsFragment();
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
            case 1: return mContext.getResources().getString(R.string.tab_seasons);
            default: return null;
        }
    }
}
