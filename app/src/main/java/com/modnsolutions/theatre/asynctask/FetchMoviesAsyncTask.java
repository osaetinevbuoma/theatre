package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.MovieType;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class FetchMoviesAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private WeakReference<ProgressBar> mLoading;
    private MovieAdapter mAdapter;
    private MovieType mMovieType;

    public FetchMoviesAsyncTask(ProgressBar loading, MovieAdapter adapter, MovieType movieType) {
        mLoading = new WeakReference<>(loading);
        mAdapter = adapter;
        mMovieType = movieType;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        JSONArray results = new JSONArray();

        switch (mMovieType) {
            case NOW_PLAYING:
                try {
                    results = NetworkUtils.moviesNowPlaying(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case POPULAR:
                try {
                    results = NetworkUtils.moviesPopular(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case TOP_RATED:
                try {
                    results = NetworkUtils.moviesTopRated(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UPCOMING:
                try {
                    results = NetworkUtils.moviesUpcoming(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

        return results;
    }

    @Override
    protected void onPostExecute(JSONArray array) {
        super.onPostExecute(array);

        mLoading.get().setVisibility(View.INVISIBLE);
        List<JSONObject> movies = new LinkedList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    movies.add(array.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        mAdapter.setMovies(movies);
    }
}
