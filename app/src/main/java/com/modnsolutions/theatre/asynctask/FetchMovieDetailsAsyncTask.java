package com.modnsolutions.theatre.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.utils.NetworkUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.text.ParseException;

public class FetchMovieDetailsAsyncTask extends AsyncTask<Integer, Void, JSONObject> {
    private WeakReference<Activity> mActivity;
    private WeakReference<View> mRootView;

    public FetchMovieDetailsAsyncTask(Activity activity, View rootView) {
        mActivity = new WeakReference<>(activity);
        mRootView = new WeakReference<>(rootView);
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = NetworkUtils.movieDetails(integers[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(final JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        // Movie poster and backdrop views
        WeakReference<ImageView> movieBackdrop = new WeakReference<>((ImageView) mActivity.get()
                .findViewById(R.id.movie_backdrop));
        WeakReference<ImageView> moviePoster = new WeakReference<>((ImageView) mActivity.get()
                .findViewById(R.id.movie_poster));

        // App bar title view config
        WeakReference<AppBarLayout> appBarLayout = new WeakReference<>((AppBarLayout) mActivity
                .get().findViewById(R.id.app_bar));
        final WeakReference<TextView> appBarTitle = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.app_bar_title));

        // Movie brief details
        WeakReference<TextView> movieTitle = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.movie_title));
        WeakReference<TextView> movieReleaseDate = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.movie_release_date));
        WeakReference<TextView> movieRuntime = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.movie_runtime));
        WeakReference<TextView> movieGenre = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.movie_genre));
        WeakReference<TextView> movieRating = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.movie_rating));


        // Movie info
        WeakReference<TextView> overview = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.movie_overview));
        WeakReference<TextView> budget = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.budget));
        WeakReference<TextView> revenue = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.revenue));
        WeakReference<TextView> website = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.website));
        WeakReference<TextView> director = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.director));
        WeakReference<TextView> cast = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.cast));

        try {
            // Load poster and backdrop images
            Glide.with(mActivity.get())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            jsonObject.getString("backdrop_path"))
                    .fitCenter()
                    .into(movieBackdrop.get());
            movieBackdrop.get().setContentDescription(jsonObject.getString("title"));

            Glide.with(mActivity.get())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            jsonObject.getString("poster_path"))
                    .fitCenter()
                    .into(moviePoster.get());
            moviePoster.get().setContentDescription(jsonObject.getString("title"));

            // Add custom app bar title using the movie title
            // Show title when appbar is collapsed. Remove title when app bar is expanded.
            appBarLayout.get().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    try {
                        if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                            String title = jsonObject.getString("title");
                            if (title.length() > 12) {
                                title = title.substring(0, 13) + "...";
                            }

                            appBarTitle.get().setText(title);
                        } else {
                            appBarTitle.get().setText(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Movie brief details
            movieTitle.get().setText(jsonObject.getString("title"));
            movieReleaseDate.get().setText(String.valueOf(Utilities.extractYearFromDate(jsonObject
                    .getString("release_date"))));
            movieRuntime.get().setText(Utilities.convertMinutesToStringTime(jsonObject.getInt(
                    "runtime")));
            movieGenre.get().setText(Utilities.extractGenreArray(jsonObject.getJSONArray(
                    "genres")));
            movieRating.get().setText(String.valueOf(jsonObject.getDouble("vote_average")));

            // Movie Info
            overview.get().setText(jsonObject.getString("overview"));
            budget.get().setText("$" + Utilities.formatNumber(jsonObject.getInt("budget")));
            revenue.get().setText("$" + Utilities.formatNumber(jsonObject.getInt("revenue")));
            website.get().setText(jsonObject.getString("homepage"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
