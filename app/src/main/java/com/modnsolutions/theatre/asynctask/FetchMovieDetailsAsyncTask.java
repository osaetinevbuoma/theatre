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
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.adapter.MovieRecommendedAdapter;
import com.modnsolutions.theatre.adapter.MovieSimilarAdapter;
import com.modnsolutions.theatre.adapter.MovieVideoAdapter;
import com.modnsolutions.theatre.utils.NetworkUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class FetchMovieDetailsAsyncTask extends AsyncTask<Integer, Void, List<JSONObject>> {
    private WeakReference<Activity> mActivity;
    private WeakReference<View> mRootView;
    private MovieVideoAdapter mVideoAdapter;
    private MovieRecommendedAdapter mMovieRecommendedAdapter;
    private MovieSimilarAdapter mMovieSimilarAdapter;

    public FetchMovieDetailsAsyncTask(Activity activity, View rootView,
                                      MovieVideoAdapter videoAdapter,
                                      MovieRecommendedAdapter movieRecommendedAdapter,
                                      MovieSimilarAdapter movieSimilarAdapter) {
        mActivity = new WeakReference<>(activity);
        mRootView = new WeakReference<>(rootView);
        mVideoAdapter = videoAdapter;
        mMovieRecommendedAdapter = movieRecommendedAdapter;
        mMovieSimilarAdapter = movieSimilarAdapter;
    }

    @Override
    protected List<JSONObject> doInBackground(Integer... integers) {
        List<JSONObject> movieInfo = new LinkedList<>();
        JSONObject movieDetails;
        JSONObject movieCredits;
        JSONObject movieVideos;
        JSONObject movieRecommended;
        JSONObject movieSimilar;

        try {
            movieDetails = NetworkUtils.movieDetails(integers[0]);
            movieCredits = NetworkUtils.movieCredits(integers[0]);
            movieVideos = NetworkUtils.movieVideos(integers[0]);
            movieRecommended = NetworkUtils.moviesRecommended(integers[0]);
            movieSimilar = NetworkUtils.moviesSimilar(integers[0]);

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
    protected void onPostExecute(final List<JSONObject> jsonObjects) {
        super.onPostExecute(jsonObjects);

        final JSONObject movieDetails = jsonObjects.get(0);
        final JSONObject movieCredits = jsonObjects.get(1);
        JSONObject movieVideos = jsonObjects.get(2);
        JSONObject movieRecommended = jsonObjects.get(3);
        JSONObject movieSimilar = jsonObjects.get(4);

        // Remove progress bar
        WeakReference<ProgressBar> progressBar = new WeakReference<>((ProgressBar) mActivity.get()
                .findViewById(R.id.loading));
        progressBar.get().setVisibility(View.GONE);

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
                            movieDetails.getString("backdrop_path"))
                    .fitCenter()
                    .into(movieBackdrop.get());
            movieBackdrop.get().setContentDescription(movieDetails.getString("title"));

            Glide.with(mActivity.get())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            movieDetails.getString("poster_path"))
                    .fitCenter()
                    .into(moviePoster.get());
            moviePoster.get().setContentDescription(movieDetails.getString("title"));

            // Add custom app bar title using the movie title
            // Show title when appbar is collapsed. Remove title when app bar is expanded.
            appBarLayout.get().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    try {
                        if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                            String title = movieDetails.getString("title");
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
            movieTitle.get().setText(movieDetails.getString("title"));
            movieReleaseDate.get().setText(String.valueOf(Utilities.extractYearFromDate(movieDetails
                    .getString("release_date"))));
            movieRuntime.get().setText(Utilities.convertMinutesToStringTime(movieDetails.getInt(
                    "runtime")));
            movieGenre.get().setText(Utilities.extractNamesFromArray(movieDetails.getJSONArray(
                    "genres")));
            movieRating.get().setText(String.valueOf(movieDetails.getDouble("vote_average")));

            // Movie Info
            overview.get().setText(movieDetails.getString("overview"));
            budget.get().setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "budget"))));
            revenue.get().setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "revenue"))));
            website.get().setText(movieDetails.getString("homepage"));

            // Get director from crew array
            String directorName = null;
            for (int i = 0; i < movieCredits.getJSONArray("crew").length(); i++) {
                JSONObject crew = (JSONObject) movieCredits.getJSONArray("crew").get(i);
                if (crew.getString("job").equals("Director")) {
                    directorName = crew.getString("name");
                    break;
                }
            }
            director.get().setText(directorName);

            // Get cast members
            cast.get().setText(Utilities.extractNamesFromArray(movieCredits.getJSONArray(
                    "cast")));

            // Set video trailers
            List<JSONObject> videos = new LinkedList<>();
            for (int i = 0; i < movieVideos.getJSONArray("results").length(); i++) {
                JSONObject video = movieVideos.getJSONArray("results").getJSONObject(i);
                videos.add(video);
            }
            mVideoAdapter.setVideos(videos);

            // Set recommended movies
            List<JSONObject> recMovies = new LinkedList<>();
            for (int i = 0; i < movieRecommended.getJSONArray("results").length(); i++) {
                JSONObject movie = movieRecommended.getJSONArray("results").getJSONObject(i);
                recMovies.add(movie);
            }
            mMovieRecommendedAdapter.setMovies(recMovies);

            // Set similar movies
            List<JSONObject> similarMovies = new LinkedList<>();
            for (int i = 0; i < movieSimilar.getJSONArray("results").length(); i++) {
                JSONObject movie = movieSimilar.getJSONArray("results").getJSONObject(i);
                similarMovies.add(movie);
            }
            mMovieSimilarAdapter.setMovies(similarMovies);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
