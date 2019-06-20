package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TVShowDetailsAsyncTaskLoader extends AsyncTaskLoader<List<JSONObject>> {
    private int mTVShowId;

    public TVShowDetailsAsyncTaskLoader(@NonNull Context context, int tvShowId) {
        super(context);
        mTVShowId = tvShowId;
    }

    @Nullable
    @Override
    public List<JSONObject> loadInBackground() {
        List<JSONObject> tvShowInfo = new LinkedList<>();
        JSONObject tvShowDetails;
        JSONObject tvShowCredits;
        JSONObject tvShowVideos;
        JSONObject tvShowRecommendations;
        JSONObject tvShowSimilar;

        try {
            tvShowDetails = NetworkUtils.tvShowsDetails(mTVShowId);
            tvShowCredits = NetworkUtils.tvShowsCredits(mTVShowId);
            tvShowVideos = NetworkUtils.tvShowsVideos(mTVShowId);
            tvShowRecommendations = NetworkUtils.tvShowsRecommendations(mTVShowId);
            tvShowSimilar = NetworkUtils.tvShowsSimilar(mTVShowId);

            tvShowInfo.add(tvShowDetails);
            tvShowInfo.add(tvShowCredits);
            tvShowInfo.add(tvShowVideos);
            tvShowInfo.add(tvShowRecommendations);
            tvShowInfo.add(tvShowSimilar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tvShowInfo;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
