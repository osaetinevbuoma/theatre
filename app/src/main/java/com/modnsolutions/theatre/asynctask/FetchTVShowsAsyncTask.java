package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.enums.TVShowType;
import com.modnsolutions.theatre.adapter.TVShowAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class FetchTVShowsAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private WeakReference<ProgressBar> mLoading;
    private TVShowAdapter mAdapter;
    private TVShowType mTVShowsType;

    public FetchTVShowsAsyncTask(ProgressBar loading, TVShowAdapter adapter, TVShowType tvShowsType) {
        mLoading = new WeakReference<>(loading);
        mAdapter = adapter;
        mTVShowsType = tvShowsType;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        JSONArray results = new JSONArray();

        switch (mTVShowsType) {
            case AIRING_TODAY:
                try {
                    results = NetworkUtils.tvShowsAiringToday(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case ON_THE_AIR:
                try {
                    results = NetworkUtils.tvShowsOnTheAir(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case POPULAR:
                try {
                    results = NetworkUtils.tvShowsPopular(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case TOP_RATED:
                try {
                    results = NetworkUtils.tvShowsTopRated(integers[0]);
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
        List<JSONObject> tvShows = new LinkedList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                tvShows.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mAdapter.setTVShows(tvShows);
    }
}
