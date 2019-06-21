package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieRecommendedAdapter;
import com.modnsolutions.theatre.adapter.MovieSimilarAdapter;
import com.modnsolutions.theatre.adapter.MovieVideoAdapter;
import com.modnsolutions.theatre.loader.MovieDetailsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class MovieInfoFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<JSONObject>> {
    private int mMovieId;
    private RecyclerView mTrailersRV;
    private RecyclerView mRecommendedRV;
    private RecyclerView mSimilarRV;
    private MovieVideoAdapter mTrailersAdapter;
    private MovieRecommendedAdapter mMovieRecommendedAdapter;
    private MovieSimilarAdapter mMovieSimilarAdapter;
    private LoaderManager mLoaderManager;
    private static final String MOVIE_ID_BUNDLE = "MOVIE_ID_BUNDLE";
    private final int LOADER_ID = 1;
    private View mRootView;

    public static final String MOVIE_ID_INTENT = "MOVIE_ID_INTENT";

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderManager = LoaderManager.getInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_movie_info, container,
                false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mMovieId = intent.getIntExtra(MOVIE_ID_INTENT, -1);

            // Trailers recyclerview
            mTrailersRV = mRootView.findViewById(R.id.trailers);
            mTrailersAdapter = new MovieVideoAdapter(getContext());
            mTrailersRV.setAdapter(mTrailersAdapter);
            mTrailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended movies recyclerview
            mMovieRecommendedAdapter = new MovieRecommendedAdapter(getContext());
            mRecommendedRV = mRootView.findViewById(R.id.recommended);
            mRecommendedRV.setAdapter(mMovieRecommendedAdapter);
            mRecommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar movies recyclerview
            mMovieSimilarAdapter = new MovieSimilarAdapter(getContext());
            mSimilarRV = mRootView.findViewById(R.id.similar);
            mSimilarRV.setAdapter(mMovieSimilarAdapter);
            mSimilarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));


            // Fetch results from server
            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(MOVIE_ID_BUNDLE, mMovieId);
                mLoaderManager.restartLoader(LOADER_ID, bundle, this);
            } else {
                mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return mRootView;
    }

    @NonNull
    @Override
    public Loader<List<JSONObject>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieDetailsAsyncTaskLoader(getContext(), args.getInt(MOVIE_ID_BUNDLE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        final JSONObject movieDetails = data.get(0);
        JSONObject movieCredits = data.get(1);
        JSONObject movieVideos = data.get(2);
        JSONObject movieRecommended = data.get(3);
        JSONObject movieSimilar = data.get(4);

        try {
            // Remove progress bar
            getActivity().findViewById(R.id.loading).setVisibility(View.GONE);

            // Movie poster and backdrop views
            ImageView movieBackdrop = getActivity().findViewById(R.id.movie_backdrop);
            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            movieDetails.getString("backdrop_path"))
                    .fitCenter()
                    .into(movieBackdrop);
            movieBackdrop.setContentDescription(movieDetails.getString("title"));

            ImageView moviePoster = getActivity().findViewById(R.id.movie_poster);
            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            movieDetails.getString("poster_path"))
                    .fitCenter()
                    .into(moviePoster);
            moviePoster.setContentDescription(movieDetails.getString("title"));

            // App bar title view config
            AppBarLayout appBarLayout = getActivity().findViewById(R.id.app_bar);
            final TextView appBarTitle = getActivity().findViewById(R.id.app_bar_title);
            // Add custom app bar title using the movie title
            // Show title when appbar is collapsed. Remove title when app bar is expanded.
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    try {
                        if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                            String title = movieDetails.getString("title");
                            if (title.length() > 12) {
                                title = title.substring(0, 13) + "...";
                            }

                            appBarTitle.setText(title);
                        } else {
                            appBarTitle.setText(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Movie brief details
            TextView movieTitle = getActivity().findViewById(R.id.movie_title);
            TextView movieReleaseDate = getActivity().findViewById(R.id.movie_release_date);
            TextView movieRuntime = getActivity().findViewById(R.id.movie_runtime);
            TextView movieGenre = getActivity().findViewById(R.id.movie_genre);
            TextView movieRating = getActivity().findViewById(R.id.movie_rating);
            // Movie brief details
            movieTitle.setText(movieDetails.getString("title"));
            try {
                movieReleaseDate.setText(Utilities.formatDate(movieDetails.getString(
                        "release_date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            movieRuntime.setText(Utilities.convertMinutesToStringTime(movieDetails.getInt(
                    "runtime")));
            movieGenre.setText(Utilities.extractNamesFromArray(movieDetails.getJSONArray(
                    "genres")));
            movieRating.setText(String.valueOf(movieDetails.getDouble("vote_average")));

            // Movie info
            TextView overview = mRootView.findViewById(R.id.movie_overview);
            overview.setText(movieDetails.getString("overview"));
            TextView budget = mRootView.findViewById(R.id.budget);
            budget.setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "budget"))));
            TextView revenue = mRootView.findViewById(R.id.revenue);
            revenue.setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "revenue"))));
            TextView website = mRootView.findViewById(R.id.website);
            website.setText(movieDetails.getString("homepage"));
            TextView director = mRootView.findViewById(R.id.director);
            director.setText(Utilities.extractDirectorNameFromArray(movieCredits.getJSONArray(
                    "crew")));
            TextView cast = mRootView.findViewById(R.id.cast);
            cast.setText(Utilities.extractNamesFromArray(movieCredits.getJSONArray("cast")));

            // Set video trailers
            List<JSONObject> videos = new LinkedList<>();
            for (int i = 0; i < movieVideos.getJSONArray("results").length(); i++) {
                JSONObject video = movieVideos.getJSONArray("results").getJSONObject(i);
                videos.add(video);
            }
            mTrailersAdapter.removeAll();
            mTrailersAdapter.setVideos(videos);

            // Set recommended movies
            List<JSONObject> recMovies = new LinkedList<>();
            for (int i = 0; i < movieRecommended.getJSONArray("results").length(); i++) {
                JSONObject movie = movieRecommended.getJSONArray("results").getJSONObject(i);
                recMovies.add(movie);
            }
            mMovieRecommendedAdapter.removeAll();
            mMovieRecommendedAdapter.setMovies(recMovies);

            // Set similar movies
            List<JSONObject> similarMovies = new LinkedList<>();
            for (int i = 0; i < movieSimilar.getJSONArray("results").length(); i++) {
                JSONObject movie = movieSimilar.getJSONArray("results").getJSONObject(i);
                similarMovies.add(movie);
            }
            mMovieSimilarAdapter.removeAll();
            mMovieSimilarAdapter.setMovies(similarMovies);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<JSONObject>> loader) {

    }
}
