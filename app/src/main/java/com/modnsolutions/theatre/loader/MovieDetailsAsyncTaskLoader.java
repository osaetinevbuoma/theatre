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

public class MovieDetailsAsyncTaskLoader extends AsyncTaskLoader<List<JSONObject>> {
    private int mMovieId;

    public MovieDetailsAsyncTaskLoader(@NonNull Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Nullable
    @Override
    public List<JSONObject> loadInBackground() {
        List<JSONObject> movieInfo = new LinkedList<>();
        JSONObject movieDetails;
        JSONObject movieCredits;
        JSONObject movieVideos;
        JSONObject movieRecommended;
        JSONObject movieSimilar;

        try {
            movieDetails = NetworkUtils.movieDetails(mMovieId);
            movieCredits = NetworkUtils.movieCredits(mMovieId);
            movieVideos = NetworkUtils.movieVideos(mMovieId);
            movieRecommended = NetworkUtils.moviesRecommended(mMovieId);
            movieSimilar = NetworkUtils.moviesSimilar(mMovieId);

            movieInfo.add(movieDetails);
            movieInfo.add(movieCredits);
            movieInfo.add(movieVideos);
            movieInfo.add(movieRecommended);
            movieInfo.add(movieSimilar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieInfo;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
