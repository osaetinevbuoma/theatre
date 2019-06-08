package com.modnsolutions.theatre.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.adapter.ReviewsAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class FetchReviewsAsyncTask extends AsyncTask<Integer, Void, JSONObject> {
    private WeakReference<ProgressBar> mProgressBar;
    private ReviewsAdapter mAdapter;
    private boolean isMovie;

    public FetchReviewsAsyncTask(ProgressBar progressBar, ReviewsAdapter adapter, boolean isMovie) {
        mProgressBar = new WeakReference<>(progressBar);
        mAdapter = adapter;
        this.isMovie = isMovie;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject reviews = new JSONObject();
        Log.d(FetchReviewsAsyncTask.class.getSimpleName(), "Current Page: " + integers[1]);

        try {
            reviews = NetworkUtils.reviews(integers[0], integers[1], isMovie);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        List<JSONObject> reviews = new LinkedList<>();

        try {
            for (int i = 0; i < jsonObject.getJSONArray("results").length(); i++) {
                JSONObject review = jsonObject.getJSONArray("results").getJSONObject(i);
                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mProgressBar.get().setVisibility(View.GONE);
        mAdapter.setReviews(reviews);
    }
}
