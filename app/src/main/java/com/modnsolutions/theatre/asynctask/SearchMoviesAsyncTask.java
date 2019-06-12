package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.SearchMoviesAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class SearchMoviesAsyncTask extends AsyncTask<String, Void, JSONObject> {
    private int mCurrentPage;
    private WeakReference<ProgressBar> mLoading;
    private SearchMoviesAdapter mAdapter;

    public SearchMoviesAsyncTask(ProgressBar loading, SearchMoviesAdapter adapter, int currentPage) {
        mCurrentPage = currentPage;
        mLoading = new WeakReference<>(loading);
        mAdapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject results = new JSONObject();

        try {
            results = NetworkUtils.searchMovies(strings[0], mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            if (mLoading.get() != null) mLoading.get().setVisibility(View.GONE);
            List<JSONObject> results = new LinkedList<>();
            for (int i = 0; i < jsonObject.getJSONArray("results").length(); i++) {
                JSONObject result = jsonObject.getJSONArray("results").getJSONObject(i);
                results.add(result);
            }
            mAdapter.setResults(results);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
