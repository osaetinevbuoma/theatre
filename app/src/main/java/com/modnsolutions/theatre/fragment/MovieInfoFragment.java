package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieRecommendedAdapter;
import com.modnsolutions.theatre.adapter.MovieSimilarAdapter;
import com.modnsolutions.theatre.adapter.MovieVideoAdapter;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.entity.TrailerEntity;
import com.modnsolutions.theatre.db.viewmodel.SaveTypeHasTheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreSaveTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TrailerViewModel;
import com.modnsolutions.theatre.loader.MovieDetailsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class MovieInfoFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<JSONObject>> {
    private MovieVideoAdapter mTrailersAdapter;
    private MovieRecommendedAdapter mMovieRecommendedAdapter;
    private MovieSimilarAdapter mMovieSimilarAdapter;
    private LoaderManager mLoaderManager;
    private static final String MOVIE_ID_BUNDLE = "MOVIE_ID_BUNDLE";
    private View mRootView;

    // boolean to know if main FAB is in open or closed state
    private boolean isFabExpanded = false;
    private FloatingActionButton mFabMain;
    private FloatingActionButton mFabActionFavorite;
    private FloatingActionButton mFabActionWatchlist;
    private LinearLayout mFabFavorite;
    private LinearLayout mFabWatchlist;
    private TextView mFabCardFavorite;
    private TextView mFabCardWatchlist;

    private TheatreSaveTypeViewModel mTheatreSaveTypeViewModel;
    private TheatreViewModel mTheatreViewModel;
    private TrailerViewModel mTrailerViewModel;
    private TheatreTypeViewModel mTheatreTypeViewModel;
    private SaveTypeHasTheatreViewModel mSaveTypeHasTheatreViewModel;
    private TheatreEntity mMovie;
    private boolean isMovieInFavorite = false;
    private boolean isMovieInWatchlist = false;

    private List<JSONObject> mMovieData = new LinkedList<>();

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
            int movieId = intent.getIntExtra(MOVIE_ID_INTENT, -1);

            mTheatreViewModel = ViewModelProviders.of(this).get(TheatreViewModel.class);
            mTrailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
            mTheatreTypeViewModel = ViewModelProviders.of(this).get(TheatreTypeViewModel.class);
            mSaveTypeHasTheatreViewModel = ViewModelProviders.of(this)
                    .get(SaveTypeHasTheatreViewModel.class);
            mTheatreSaveTypeViewModel = ViewModelProviders.of(this)
                    .get(TheatreSaveTypeViewModel.class);

            // Check if movie is in favorite or watchlist
            mMovie = mTheatreViewModel.findOneByRemoteId(movieId);
            if (mMovie != null) {
                TheatreSaveTypeEntity favoriteSaveTypeEntity = mTheatreSaveTypeViewModel
                        .findOneByType("Favorites");
                if (mSaveTypeHasTheatreViewModel.findByTheatreIdAndSaveTypeId(mMovie.getId(),
                        favoriteSaveTypeEntity.getId()) != null) {
                    isMovieInFavorite = true;
                }
                TheatreSaveTypeEntity watchlistSaveTypeEntity = mTheatreSaveTypeViewModel
                        .findOneByType("Watchlist");
                if (mSaveTypeHasTheatreViewModel.findByTheatreIdAndSaveTypeId(mMovie.getId(),
                        watchlistSaveTypeEntity.getId()) != null) {
                    isMovieInWatchlist = true;
                }
            }

            mFabMain = getActivity().findViewById(R.id.fab);
            mFabActionFavorite = getActivity().findViewById(R.id.fab_action_favorite);
            mFabActionWatchlist = getActivity().findViewById(R.id.fab_action_watchlist);
            mFabFavorite = getActivity().findViewById(R.id.fab_favorite);
            mFabWatchlist = getActivity().findViewById(R.id.fab_watchlist);
            mFabCardFavorite = getActivity().findViewById(R.id.fab_card_favorite);
            mFabCardWatchlist = getActivity().findViewById(R.id.fab_card_watchlist);

            // Display appropriate icon and label if movie is in favorite or not.
            setAppropriateFavoriteFab();

            // Display appropriate icon and label if movie is in watchlist or not.
            setAppropriateWatchlistFab();

            // Expand main fab if not expanded and collapse if not collapsed.
            mFabMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFabExpanded) closeFabSubMenus();
                    else openFabSubMenus();
                }
            });

            mFabActionFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMovieInFavorite) {
                        // remove from favorite.
                        addMovie(true);
                    } else {
                        // add to favorite.
                        removeMovie(true);
                    }
                }
            });

            mFabActionWatchlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMovieInWatchlist) {
                        // remove from watchlist.
                        addMovie(false);
                    } else {
                        // add to watchlist.
                        removeMovie(false);
                    }
                }
            });

            // Default state of fab should be collapsed.
            closeFabSubMenus();

            // Trailers recyclerview
            RecyclerView trailersRV = mRootView.findViewById(R.id.trailers);
            mTrailersAdapter = new MovieVideoAdapter(getContext());
            trailersRV.setAdapter(mTrailersAdapter);
            trailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended movies recyclerview
            mMovieRecommendedAdapter = new MovieRecommendedAdapter(getContext());
            RecyclerView recommendedRV = mRootView.findViewById(R.id.recommended);
            recommendedRV.setAdapter(mMovieRecommendedAdapter);
            recommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar movies recyclerview
            mMovieSimilarAdapter = new MovieSimilarAdapter(getContext());
            RecyclerView similarRV = mRootView.findViewById(R.id.similar);
            similarRV.setAdapter(mMovieSimilarAdapter);
            similarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));


            // Fetch results from server
            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(MOVIE_ID_BUNDLE, movieId);
                mLoaderManager.restartLoader(1, bundle, this);
            } else {
                mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return mRootView;
    }

    /**
     * If movie was added to both favorite and watchlist, delete only favorite entry and preserve
     * movie. If movie was added as only favorite or watchlist, delete movie completely.
     */
    private void removeMovie(boolean favorite) {
        if (mSaveTypeHasTheatreViewModel.findByTheatreId(mMovie.getId()).length > 1) {
            if (favorite) {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mMovie.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Favorites").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            } else {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mMovie.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Watchlist").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            }
        } else {
            mTheatreViewModel.delete(mMovie);
        }

        if (favorite) {
            isMovieInFavorite = false;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been removed from your favorite list.");
        } else {
            isMovieInWatchlist = false;
            setAppropriateWatchlistFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been removed from your watchlist.");
        }
    }

    private void addMovie(boolean favorite) {
        JSONObject movieDetails = mMovieData.get(0);
        JSONObject movieCredits = mMovieData.get(1);
        JSONObject movieVideos = mMovieData.get(2);

        try {
            if (mMovie == null) {
                mMovie = new TheatreEntity();
                mMovie.setTheatreTypeId(mTheatreTypeViewModel.findOneByType("Movies").getId());
                mMovie.setRemoteId(movieDetails.getInt("id"));
                mMovie.setBackdropPath(movieDetails.getString("backdrop_path"));
                mMovie.setBudget(movieDetails.getInt("budget"));
                mMovie.setGenre(Utilities.extractNamesFromArray(movieDetails
                        .getJSONArray("genres")));
                mMovie.setWebsite(movieDetails.getString("homepage"));
                mMovie.setOverview(movieDetails.getString("overview"));
                mMovie.setPosterPath(movieDetails.getString("poster_path"));
                mMovie.setReleaseDate(movieDetails.getString("release_date"));
                mMovie.setRevenue(movieDetails.getInt("revenue"));
                mMovie.setRuntime(movieDetails.getInt("runtime"));
                mMovie.setTitle(movieDetails.getString("title"));
                mMovie.setOriginalTitle(movieDetails.getString("original_title"));
                mMovie.setRating(movieDetails.getDouble("vote_average"));
                mMovie.setDirector(Utilities.extractDirectorNameFromArray(
                        movieCredits.getJSONArray("crew")));
                mMovie.setCast(Utilities.extractNamesFromArray(movieCredits
                        .getJSONArray("cast")));

                mTheatreViewModel.insert(mMovie);
                mMovie = mTheatreViewModel.findOneByRemoteId(mMovie.getRemoteId());
            }

            if (favorite) {
                SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                        mTheatreSaveTypeViewModel.findOneByType("Favorites").getId(),
                        mMovie.getId());
                mSaveTypeHasTheatreViewModel.insert(joinEntity);
            } else {
                SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                        mTheatreSaveTypeViewModel.findOneByType("Watchlist").getId(),
                        mMovie.getId());
                mSaveTypeHasTheatreViewModel.insert(joinEntity);
            }

            for (int i = 0; i < movieVideos.getJSONArray("results").length(); i++) {
                JSONObject video = movieVideos.getJSONArray("results").getJSONObject(i);
                TrailerEntity trailer = new TrailerEntity(video.getString("id"),
                        video.getString("name"), video.getString("key"));
                trailer.setTheatreId(mMovie.getId());

                mTrailerViewModel.insert(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (favorite) {
            isMovieInFavorite = true;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been added to your favorite list.");
        } else {
            isMovieInWatchlist = true;
            setAppropriateWatchlistFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been added to your watchlist.");
        }
    }

    /**
     * Open FAB submenus.
     */
    private void openFabSubMenus() {
        mFabFavorite.setVisibility(View.VISIBLE);
        mFabWatchlist.setVisibility(View.VISIBLE);
        mFabMain.setImageResource(R.drawable.ic_action_close);
        isFabExpanded = true;
    }

    /**
     * Close FAB submenus.
     */
    private void closeFabSubMenus() {
        mFabFavorite.setVisibility(View.GONE);
        mFabWatchlist.setVisibility(View.GONE);
        mFabMain.setImageResource(R.drawable.ic_action_save);
        isFabExpanded = false;
    }

    private void setAppropriateFavoriteFab() {
        if (isMovieInFavorite) {
            mFabActionFavorite.setImageResource(R.drawable.ic_action_favorite);
            mFabCardFavorite.setText(getString(R.string.label_remove_from_favorite));
        } else {
            mFabActionFavorite.setImageResource(R.drawable.ic_action_not_favorite);
            mFabCardFavorite.setText(getString(R.string.label_add_to_favorite));
        }
    }

    private void setAppropriateWatchlistFab() {
        if (isMovieInWatchlist) {
            mFabActionWatchlist.setImageResource(R.drawable.ic_action_watchlist_added);
            mFabCardWatchlist.setText(getString(R.string.label_remove_from_watchlist));
        } else {
            mFabActionWatchlist.setImageResource(R.drawable.ic_action_watchlist_add);
            mFabCardWatchlist.setText(getString(R.string.label_add_to_watchlist));
        }
    }

    @NonNull
    @Override
    public Loader<List<JSONObject>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieDetailsAsyncTaskLoader(getContext(), args.getInt(MOVIE_ID_BUNDLE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        populateView(data);
        mMovieData = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<JSONObject>> loader) {

    }

    /**
     * Populate view with either JSONObject or TheatreEntity objects.
     * @param data
     */
    private void populateView(List<JSONObject> data) {
        final JSONObject movieDetails = data.get(0);
        JSONObject movieCredits = data.get(1);
        JSONObject movieVideos = data.get(2);
        JSONObject movieRecommended = data.get(3);
        JSONObject movieSimilar = data.get(4);

        ImageView movieBackdrop = getActivity().findViewById(R.id.movie_backdrop);
        ImageView moviePoster = getActivity().findViewById(R.id.movie_poster);
        // App bar title view config
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.app_bar);
        final TextView appBarTitle = getActivity().findViewById(R.id.app_bar_title);
        // Movie brief details
        TextView movieTitle = getActivity().findViewById(R.id.movie_title);
        TextView movieReleaseDate = getActivity().findViewById(R.id.movie_release_date);
        TextView movieRuntime = getActivity().findViewById(R.id.movie_runtime);
        TextView movieGenre = getActivity().findViewById(R.id.movie_genre);
        TextView movieRating = getActivity().findViewById(R.id.movie_rating);
        TextView overview = mRootView.findViewById(R.id.movie_overview);
        TextView budget = mRootView.findViewById(R.id.budget);
        TextView revenue = mRootView.findViewById(R.id.revenue);
        TextView website = mRootView.findViewById(R.id.website);
        TextView director = mRootView.findViewById(R.id.director);
        TextView cast = mRootView.findViewById(R.id.cast);

        try {
            // Remove progress bar
            getActivity().findViewById(R.id.loading).setVisibility(View.GONE);

            // Movie poster and backdrop views
            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            movieDetails.getString("backdrop_path"))
                    .fitCenter()
                    .into(movieBackdrop);
            movieBackdrop.setContentDescription(movieDetails.getString("title"));

            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            movieDetails.getString("poster_path"))
                    .fitCenter()
                    .into(moviePoster);
            moviePoster.setContentDescription(movieDetails.getString("title"));

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
            overview.setText(movieDetails.getString("overview"));
            budget.setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "budget"))));
            revenue.setText(String.format("$%s", Utilities.formatNumber(movieDetails.getInt(
                    "revenue"))));
            website.setText(movieDetails.getString("homepage"));
            director.setText(Utilities.extractDirectorNameFromArray(movieCredits.getJSONArray(
                    "crew")));
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
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
