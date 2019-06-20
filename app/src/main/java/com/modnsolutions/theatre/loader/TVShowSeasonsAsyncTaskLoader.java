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

public class TVShowSeasonsAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {
    private int mTVShowId;

    public TVShowSeasonsAsyncTaskLoader(@NonNull Context context, int tvShowId) {
        super(context);
        mTVShowId = tvShowId;
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = NetworkUtils.tvShowsDetails(mTVShowId);
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
