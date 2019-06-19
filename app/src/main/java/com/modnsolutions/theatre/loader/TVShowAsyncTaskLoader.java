package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.enums.TVShowType;
import com.modnsolutions.theatre.utils.NetworkUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TVShowAsyncTaskLoader extends AsyncTaskLoader<List<JSONObject>> {
    private TVShowType mTVShowType;
    private int mCurrentPage;

    public TVShowAsyncTaskLoader(@NonNull Context context, TVShowType tvShowType, int currentPage) {
        super(context);
        mTVShowType = tvShowType;
        mCurrentPage = currentPage;
    }

    @Nullable
    @Override
    public List<JSONObject> loadInBackground() {
        JSONArray results = new JSONArray();

        switch (mTVShowType) {
            case AIRING_TODAY:
                try {
                    results = NetworkUtils.tvShowsAiringToday(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case ON_THE_AIR:
                try {
                    results = NetworkUtils.tvShowsOnTheAir(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case POPULAR:
                try {
                    results = NetworkUtils.tvShowsPopular(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case TOP_RATED:
                try {
                    results = NetworkUtils.tvShowsTopRated(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

        return Utilities.convertJSONArrayToList(results);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
