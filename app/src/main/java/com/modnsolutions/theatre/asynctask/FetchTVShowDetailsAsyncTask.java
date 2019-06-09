package com.modnsolutions.theatre.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowRecommendedAdapter;
import com.modnsolutions.theatre.adapter.TVShowSimilarAdapter;
import com.modnsolutions.theatre.adapter.TVShowVideoAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class FetchTVShowDetailsAsyncTask extends AsyncTask<Integer, Void, List<JSONObject>> {
    private WeakReference<Activity> mActivity;
    private WeakReference<View> mRootView;
    private TVShowVideoAdapter mVideoAdapter;
    private TVShowRecommendedAdapter mRecommendedAdapter;
    private TVShowSimilarAdapter mSimilarAdapter;

    public static final String LOG_TAG = FetchTVShowDetailsAsyncTask.class.getSimpleName();

    public FetchTVShowDetailsAsyncTask(Activity activity, View rootView,
                                       TVShowVideoAdapter videoAdapter,
                                       TVShowRecommendedAdapter recommendedAdapter,
                                       TVShowSimilarAdapter similarAdapter) {
        mActivity = new WeakReference<>(activity);
        mRootView = new WeakReference<>(rootView);
        mVideoAdapter = videoAdapter;
        mRecommendedAdapter = recommendedAdapter;
        mSimilarAdapter = similarAdapter;
    }

    @Override
    protected List<JSONObject> doInBackground(Integer... integers) {
        List<JSONObject> tvShowInfo = new LinkedList<>();
        JSONObject tvShowDetails;
        JSONObject tvShowCredits;
        JSONObject tvShowVideos;
        JSONObject tvShowRecommendations;
        JSONObject tvShowSimilar;

        try {
            tvShowDetails = NetworkUtils.tvShowsDetails(integers[0]);
            tvShowCredits = NetworkUtils.tvShowsCredits(integers[0]);
            tvShowVideos = NetworkUtils.tvShowsVideos(integers[0]);
            tvShowRecommendations = NetworkUtils.tvShowsRecommendations(integers[0]);
            tvShowSimilar = NetworkUtils.tvShowsSimilar(integers[0]);

            tvShowInfo.add(tvShowDetails);
            tvShowInfo.add(tvShowCredits);
            tvShowInfo.add(tvShowVideos);
            tvShowInfo.add(tvShowRecommendations);
            tvShowInfo.add(tvShowSimilar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tvShowInfo;
    }

    @Override
    protected void onPostExecute(List<JSONObject> jsonObjects) {
        super.onPostExecute(jsonObjects);

        final JSONObject tvShowDetails = jsonObjects.get(0);
        JSONObject tvShowCredits = jsonObjects.get(1);
        JSONObject tvShowVideos = jsonObjects.get(2);
        JSONObject tvShowRecommendations = jsonObjects.get(3);
        JSONObject tvShowSimilar = jsonObjects.get(4);

        // Remove progress bar
        WeakReference<ProgressBar> progressBar = new WeakReference<>((ProgressBar) mActivity.get()
                .findViewById(R.id.loading));
        progressBar.get().setVisibility(View.GONE);

        // Movie poster and backdrop views
        WeakReference<ImageView> tvShowBackdrop = new WeakReference<>((ImageView) mActivity.get()
                .findViewById(R.id.tv_show_backdrop));
        WeakReference<ImageView> tvShowPoster = new WeakReference<>((ImageView) mActivity.get()
                .findViewById(R.id.tv_show_poster));

        // App bar title view config
        WeakReference<AppBarLayout> appBarLayout = new WeakReference<>((AppBarLayout) mActivity
                .get().findViewById(R.id.app_bar));
        final WeakReference<TextView> appBarTitle = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.app_bar_title));

        // TV Show brief details
        WeakReference<TextView> tvShowName = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.tv_show_name));
        WeakReference<TextView> tvShowFirstAirDate = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.tv_show_first_air_date));
        WeakReference<TextView> tvShowEpisodeRuntime = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.tv_show_episode_runtime));
        WeakReference<TextView> tvShowGenre = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.tv_show_genre));
        WeakReference<TextView> tvShowRating = new WeakReference<>((TextView) mActivity.get()
                .findViewById(R.id.tv_show_rating));

        // TV Show info
        WeakReference<TextView> overview = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.tv_show_overview));
        WeakReference<TextView> seasons = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.seasons));
        WeakReference<TextView> episodes = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.episodes));
        WeakReference<TextView> website = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.website));
        WeakReference<TextView> lastAired = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.last_aired));
        WeakReference<TextView> cast = new WeakReference<>((TextView) mRootView.get()
                .findViewById(R.id.cast));

        try {
            // Load poster and backdrop images
            Glide.with(mActivity.get())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            tvShowDetails.getString("backdrop_path"))
                    .fitCenter()
                    .into(tvShowBackdrop.get());
            tvShowBackdrop.get().setContentDescription(tvShowDetails.getString("name"));

            Glide.with(mActivity.get())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            tvShowDetails.getString("poster_path"))
                    .fitCenter()
                    .into(tvShowPoster.get());
            tvShowPoster.get().setContentDescription(tvShowDetails.getString("name"));

            // Add custom app bar title using the movie title
            // Show title when appbar is collapsed. Remove title when app bar is expanded.
            appBarLayout.get().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    try {
                        if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                            String title = tvShowDetails.getString("name");
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

            // TV Show brief details
            tvShowName.get().setText(tvShowDetails.getString("name"));
            tvShowFirstAirDate.get().setText(Utilities.formatDate(tvShowDetails
                    .getString("first_air_date")));
            tvShowEpisodeRuntime.get().setText(Utilities.convertMinutesToStringTime((int)
                    tvShowDetails.getJSONArray("episode_run_time").get(0)));
            tvShowGenre.get().setText(Utilities.extractNamesFromArray(tvShowDetails.getJSONArray(
                    "genres")));
            tvShowRating.get().setText(String.valueOf(tvShowDetails.getDouble("vote_average")));

            // Movie Info
            overview.get().setText(tvShowDetails.getString("overview"));
            seasons.get().setText(String.valueOf(tvShowDetails.getInt("number_of_seasons")));
            episodes.get().setText(String.valueOf(tvShowDetails.getInt("number_of_episodes")));
            website.get().setText(tvShowDetails.getString("homepage"));
            lastAired.get().setText(Utilities.formatDate(tvShowDetails
                    .getString("last_air_date")));

            // Get cast members
            cast.get().setText(Utilities.extractNamesFromArray(tvShowCredits.getJSONArray(
                    "cast")));

            // Set video trailers
            List<JSONObject> videos = new LinkedList<>();
            for (int i = 0; i < tvShowVideos.getJSONArray("results").length(); i++) {
                JSONObject video = tvShowVideos.getJSONArray("results").getJSONObject(i);
                videos.add(video);
            }
            mVideoAdapter.setVideos(videos);

            // Set recommended tv shows
            List<JSONObject> recTVShows = new LinkedList<>();
            for (int i = 0; i < tvShowRecommendations.getJSONArray("results").length();
                 i++) {
                JSONObject tvShow = tvShowRecommendations.getJSONArray("results")
                        .getJSONObject(i);
                recTVShows.add(tvShow);
            }
            mRecommendedAdapter.setTVShows(recTVShows);

            // Set similar tv shows
            List<JSONObject> similarTVShows = new LinkedList<>();
            for (int i = 0; i < tvShowSimilar.getJSONArray("results").length();
                 i++) {
                JSONObject tvShow = tvShowSimilar.getJSONArray("results")
                        .getJSONObject(i);
                similarTVShows.add(tvShow);
            }
            mSimilarAdapter.setTVShows(similarTVShows);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
