package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class TVShowEpisodesAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {
    private int mTVShowId;
    private int mSeasonNum;

    public TVShowEpisodesAsyncTaskLoader(@NonNull Context context, int tvShowId, int seasonNum) {
        super(context);
        mTVShowId = tvShowId;
        mSeasonNum = seasonNum;
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = NetworkUtils.tvShowsSeasonDetails(mTVShowId, mSeasonNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
