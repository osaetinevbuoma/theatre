package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TrailerAdapter;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.entity.TrailerEntity;
import com.modnsolutions.theatre.db.viewmodel.SaveTypeHasTheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreSaveTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TrailerViewModel;
import com.modnsolutions.theatre.utils.Utilities;

import java.text.ParseException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheatreMovieDetailsFragment extends Fragment {
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
    private SaveTypeHasTheatreViewModel mSaveTypeHasTheatreViewModel;
    private TheatreEntity mMovie;
    private List<TrailerEntity> mTrailerEntities;
    private TrailerAdapter mTrailerAdapter;
    private boolean isMovieInFavorite = false;
    private boolean isMovieInWatchlist = false;

    public static final String THEATRE_MOVIE_ID_INTENT = "com.modnsolutions.THEATRE_MOVIE_ID_INTENT";


    public TheatreMovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_theatre_movie_details, container,
                false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            int movieId = intent.getIntExtra(THEATRE_MOVIE_ID_INTENT, -1);

            RecyclerView trailerRecyclerView = mRootView.findViewById(R.id.trailers);
            mTrailerAdapter = new TrailerAdapter(getContext());
            trailerRecyclerView.setAdapter(mTrailerAdapter);
            trailerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            mTheatreViewModel = ViewModelProviders.of(this).get(TheatreViewModel.class);
            mTrailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
            mSaveTypeHasTheatreViewModel = ViewModelProviders.of(this)
                    .get(SaveTypeHasTheatreViewModel.class);
            mTheatreSaveTypeViewModel = ViewModelProviders.of(this)
                    .get(TheatreSaveTypeViewModel.class);

            // Check if movie is in favorite or watchlist
            mMovie = mTheatreViewModel.findOneById(movieId);
            mTrailerViewModel.findByTheatreId(mMovie.getId()).observe(this,
                    new Observer<List<TrailerEntity>>() {
                @Override
                public void onChanged(List<TrailerEntity> trailerEntities) {
                    mTrailerAdapter.setVideos(trailerEntities);
                    mTrailerEntities = trailerEntities;
                }
            });
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

            // Use movie to populate view
            populateView(mMovie);

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
                        addMovie(1);
                    } else {
                        // add to favorite.
                        removeMovie(1);
                    }
                }
            });

            mFabActionWatchlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMovieInWatchlist) {
                        // remove from watchlist.
                        addMovie(2);
                    } else {
                        // add to watchlist.
                        removeMovie(2);
                    }
                }
            });

            // Default state of fab should be collapsed.
            closeFabSubMenus();
        }

        return mRootView;
    }



    /**
     * Populate view with either JSONObject or TheatreEntity objects.
     * @param theatreEntity
     */
    private void populateView(final TheatreEntity theatreEntity) {
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

        // Remove progress bar
        mRootView.findViewById(R.id.loading).setVisibility(View.GONE);

        // Movie poster and backdrop views
        Glide.with(getActivity())
                .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                        theatreEntity.getBackdropPath())
                .fitCenter()
                .into(movieBackdrop);
        movieBackdrop.setContentDescription(theatreEntity.getTitle());

        Glide.with(getActivity())
                .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                        theatreEntity.getPosterPath())
                .fitCenter()
                .into(moviePoster);
        moviePoster.setContentDescription(theatreEntity.getTitle());

        // Add custom app bar title using the movie title
        // Show title when appbar is collapsed. Remove title when app bar is expanded.
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    String title = theatreEntity.getTitle();
                    if (title.length() > 12) {
                        title = title.substring(0, 13) + "...";
                    }

                    appBarTitle.setText(title);
                } else {
                    appBarTitle.setText(null);
                }
            }
        });

        // Movie brief details
        movieTitle.setText(theatreEntity.getTitle());
        try {
            movieReleaseDate.setText(Utilities.formatDate(theatreEntity.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        movieRuntime.setText(Utilities.convertMinutesToStringTime(theatreEntity.getRuntime()));
        movieGenre.setText(theatreEntity.getGenre());
        movieRating.setText(String.valueOf(theatreEntity.getRating()));

        // Movie info
        overview.setText(theatreEntity.getOverview());
        budget.setText(String.format("$%s", Utilities.formatNumber((int) theatreEntity
                .getBudget())));
        revenue.setText(String.format("$%s", Utilities.formatNumber((int) theatreEntity
                .getRevenue())));
        website.setText(theatreEntity.getWebsite());
        director.setText(theatreEntity.getDirector());
        cast.setText(theatreEntity.getCast());
    }

    /**
     * If movie was added to both favorite and watchlist, delete only favorite entry and preserve
     * movie. If movie was added as only favorite or watchlist, delete movie completely.
     */
    private void removeMovie(int operation) {
        if (mSaveTypeHasTheatreViewModel.findByTheatreId(mMovie.getId()).length > 1) {
            if (operation == 1) {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mMovie.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Favorites").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            }

            if (operation == 2) {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mMovie.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Watchlist").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            }
        } else {
            mTheatreViewModel.delete(mMovie);
        }

        if (operation == 1) {
            isMovieInFavorite = false;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been removed from your favorite list.");
        }

        if (operation == 2) {
            isMovieInWatchlist = false;
            setAppropriateWatchlistFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been removed from your watchlist.");
        }
    }

    private void addMovie(int operation) {
        if (mTheatreViewModel.findOneById(mMovie.getId()) == null)
            mTheatreViewModel.insert(mMovie);

        for (TrailerEntity entity : mTrailerEntities) {
            mTrailerViewModel.insert(entity);
        }

        if (operation == 1) {
            SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                    mTheatreSaveTypeViewModel.findOneByType("Favorites").getId(), mMovie.getId());
            mSaveTypeHasTheatreViewModel.insert(joinEntity);

            isMovieInFavorite = true;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mMovie.getTitle() +
                    " has been added to your favorite list.");
        }

        if (operation == 2) {
            SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                    mTheatreSaveTypeViewModel.findOneByType("Watchlist").getId(), mMovie.getId());
            mSaveTypeHasTheatreViewModel.insert(joinEntity);

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

}
