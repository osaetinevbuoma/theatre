package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.view.View;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowEpisodesAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class FetchTVShowEpisodesAsyncTask extends AsyncTask<Integer, Void, JSONObject> {
    private WeakReference<View> mRootView;
    private TVShowEpisodesAdapter mAdapter;

    public FetchTVShowEpisodesAsyncTask(View rootView, TVShowEpisodesAdapter adapter) {
        mRootView = new WeakReference<>(rootView);
        mAdapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = NetworkUtils.tvShowsSeasonDetails(integers[0], integers[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            mRootView.get().findViewById(R.id.loading).setVisibility(View.GONE);

            List<JSONObject> episodes = new LinkedList<>();
            for (int i = 0; i < jsonObject.getJSONArray("episodes").length(); i++) {
                episodes.add(jsonObject.getJSONArray("episodes").getJSONObject(i));
            }
            mAdapter.setEpisodes(episodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
