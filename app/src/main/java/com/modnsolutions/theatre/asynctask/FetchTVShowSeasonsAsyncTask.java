package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.view.View;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowSeasonsAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class FetchTVShowSeasonsAsyncTask extends AsyncTask<Integer, Void, JSONObject> {
    private WeakReference<View> mRootView;
    private TVShowSeasonsAdapter mAdapter;

    public FetchTVShowSeasonsAsyncTask(View rootView, TVShowSeasonsAdapter adapter) {
        mRootView = new WeakReference<>(rootView);
        mAdapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = NetworkUtils.tvShowsDetails(integers[0]);
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
            List<JSONObject> seasons = new LinkedList<>();
            for (int i = 0; i < jsonObject.getJSONArray("seasons").length(); i++) {
                JSONObject season = jsonObject.getJSONArray("seasons").getJSONObject(i);
                if (season.getInt("season_number") != 0) {
                    seasons.add(jsonObject.getJSONArray("seasons").getJSONObject(i));
                }
            }
            mAdapter.setSeasons(seasons);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
