package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {
    private int mCurrentPage;
    private int mMovieId;
    private boolean isMovie;

    public ReviewsAsyncTaskLoader(@NonNull Context context, int currentPage, int movieId,
                                  boolean isMovie) {
        super(context);
        mCurrentPage = currentPage;
        mMovieId = movieId;
        this.isMovie = isMovie;
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
        JSONObject reviews = new JSONObject();

        try {
            reviews = NetworkUtils.reviews(mMovieId, mCurrentPage, isMovie);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
