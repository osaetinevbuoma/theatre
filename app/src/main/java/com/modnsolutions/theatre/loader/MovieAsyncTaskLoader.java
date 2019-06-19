package com.modnsolutions.theatre.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.modnsolutions.theatre.enums.MovieType;
import com.modnsolutions.theatre.utils.NetworkUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<List<JSONObject>> {
    private MovieType mMovieType;
    private int mCurrentPage;

    public MovieAsyncTaskLoader(@NonNull Context context, MovieType movieType, int currentPage) {
        super(context);
        mMovieType = movieType;
        mCurrentPage = currentPage;
    }

    @Nullable
    @Override
    public List<JSONObject> loadInBackground() {
        JSONArray results = new JSONArray();

        switch (mMovieType) {
            case NOW_PLAYING:
                try {
                    results = NetworkUtils.moviesNowPlaying(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case POPULAR:
                try {
                    results = NetworkUtils.moviesPopular(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case TOP_RATED:
                try {
                    results = NetworkUtils.moviesTopRated(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UPCOMING:
                try {
                    results = NetworkUtils.moviesUpcoming(mCurrentPage);
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
