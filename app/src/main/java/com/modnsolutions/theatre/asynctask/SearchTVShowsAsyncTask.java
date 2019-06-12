package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.SearchMoviesAdapter;
import com.modnsolutions.theatre.adapter.SearchTVShowsAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class SearchTVShowsAsyncTask extends AsyncTask<String, Void, JSONObject> {
    private int mCurrentPage;
    private WeakReference<ProgressBar> mProgressBar;
    private SearchTVShowsAdapter mAdapter;

    public SearchTVShowsAsyncTask(ProgressBar progressBar, SearchTVShowsAdapter adapter,
                                  int currentPage) {
        mCurrentPage = currentPage;
        mProgressBar = new WeakReference<>(progressBar);
        mAdapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject results = new JSONObject();

        try {
            results = NetworkUtils.searchTVShows(strings[0], mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            mProgressBar.get().setVisibility(View.GONE);
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
