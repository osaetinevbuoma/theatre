package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {
    private String mQueryString;
    private int mCurrentPage;
    private int mOperation;

    public SearchAsyncTaskLoader(@NonNull Context context, String queryString,
                                 int currentPage, int operation) {
        super(context);
        mQueryString = queryString;
        mCurrentPage = currentPage;
        mOperation = operation;
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
        JSONObject results = new JSONObject();

        try {
            switch (mOperation) {
                case 1:
                    results = NetworkUtils.searchMovies(mQueryString,   mCurrentPage);
                    break;

                case 2:
                    results = NetworkUtils.searchTVShows(mQueryString,   mCurrentPage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
