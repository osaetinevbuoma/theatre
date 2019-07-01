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
public class TheatreTVShowDetailsFragment extends Fragment {
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
    private TheatreEntity mTVShow;
    private List<TrailerEntity> mTrailerEntities;
    private TrailerAdapter mTrailerAdapter;
    private boolean isTVShowInFavorite = false;
    private boolean isTVShowInWatchlist = false;

    public static final String THEATRE_TVSHOW_ID_INTENT = "com.modnsolutions.THEATRE_TVSHOW_ID_INTENT";

    public TheatreTVShowDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_theatre_tvshow_details, container,
                false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            int tvShowId = intent.getIntExtra(THEATRE_TVSHOW_ID_INTENT, -1);

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
            mTVShow = mTheatreViewModel.findOneById(tvShowId);
            mTrailerViewModel.findByTheatreId(mTVShow.getId()).observe(this,
                    new Observer<List<TrailerEntity>>() {
                        @Override
                        public void onChanged(List<TrailerEntity> trailerEntities) {
                            mTrailerAdapter.setVideos(trailerEntities);
                            mTrailerEntities = trailerEntities;
                        }
                    });
            TheatreSaveTypeEntity favoriteSaveTypeEntity = mTheatreSaveTypeViewModel
                    .findOneByType("Favorites");
            if (mSaveTypeHasTheatreViewModel.findByTheatreIdAndSaveTypeId(mTVShow.getId(),
                    favoriteSaveTypeEntity.getId()) != null) {
                isTVShowInFavorite = true;
            }
            TheatreSaveTypeEntity watchlistSaveTypeEntity = mTheatreSaveTypeViewModel
                    .findOneByType("Watchlist");
            if (mSaveTypeHasTheatreViewModel.findByTheatreIdAndSaveTypeId(mTVShow.getId(),
                    watchlistSaveTypeEntity.getId()) != null) {
                isTVShowInWatchlist = true;
            }

            // Use movie to populate view
            populateView(mTVShow);

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
                    if (!isTVShowInFavorite) {
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
                    if (!isTVShowInWatchlist) {
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
        // Movie poster and backdrop views
        ImageView tvShowBackdrop = getActivity().findViewById(R.id.tv_show_backdrop);
        ImageView tvShowPoster = getActivity().findViewById(R.id.tv_show_poster);

        // App bar title view config
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.app_bar);
        final TextView appBarTitle = getActivity().findViewById(R.id.app_bar_title);

        // TV Show brief details
        TextView tvShowName = getActivity().findViewById(R.id.tv_show_name);
        TextView tvShowFirstAirDate = getActivity().findViewById(R.id.tv_show_first_air_date);
        TextView tvShowEpisodeRuntime = getActivity().findViewById(R.id.tv_show_episode_runtime);
        TextView tvShowGenre = getActivity().findViewById(R.id.tv_show_genre);
        TextView tvShowRating = getActivity().findViewById(R.id.tv_show_rating);

        // TV Show info
        TextView overview = mRootView.findViewById(R.id.tv_show_overview);
        TextView seasons = mRootView.findViewById(R.id.seasons);
        TextView episodes = mRootView.findViewById(R.id.episodes);
        TextView website = mRootView.findViewById(R.id.website);
        TextView lastAired = mRootView.findViewById(R.id.last_aired);
        TextView cast = mRootView.findViewById(R.id.cast);

        // Remove progress bar
        mRootView.findViewById(R.id.loading).setVisibility(View.GONE);

        // Movie poster and backdrop views
        Glide.with(getActivity())
                .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                        theatreEntity.getBackdropPath())
                .fitCenter()
                .into(tvShowBackdrop);
        tvShowBackdrop.setContentDescription(theatreEntity.getTitle());

        Glide.with(getActivity())
                .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                        theatreEntity.getPosterPath())
                .fitCenter()
                .into(tvShowPoster);
        tvShowPoster.setContentDescription(theatreEntity.getTitle());

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
        tvShowName.setText(theatreEntity.getTitle());
        try {
            tvShowFirstAirDate.setText(Utilities.formatDate(theatreEntity.getFirstAirDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvShowEpisodeRuntime.setText(Utilities.convertMinutesToStringTime(theatreEntity
                .getEpisodeRuntime()));
        tvShowGenre.setText(theatreEntity.getGenre());
        tvShowRating.setText(String.valueOf(theatreEntity.getRating()));

        // Movie info
        overview.setText(theatreEntity.getOverview());
        seasons.setText(String.valueOf(theatreEntity.getNumberOfSeasons()));
        episodes.setText(String.valueOf(theatreEntity.getNumberOfEpisodes()));
        website.setText(theatreEntity.getWebsite());
        try {
            lastAired.setText(Utilities.formatDate(theatreEntity.getLastAirDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cast.setText(theatreEntity.getCast());
    }

    /**
     * If movie was added to both favorite and watchlist, delete only favorite entry and preserve
     * movie. If movie was added as only favorite or watchlist, delete movie completely.
     */
    private void removeMovie(int operation) {
        if (mSaveTypeHasTheatreViewModel.findByTheatreId(mTVShow.getId()).length > 1) {
            if (operation == 1) {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mTVShow.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Favorites").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            }

            if (operation == 2) {
                SaveTypeHasTheatreEntity entity = mSaveTypeHasTheatreViewModel
                        .findByTheatreIdAndSaveTypeId(mTVShow.getId(), mTheatreSaveTypeViewModel
                                .findOneByType("Watchlist").getId());
                mSaveTypeHasTheatreViewModel.delete(entity);
            }
        } else {
            mTheatreViewModel.delete(mTVShow);
        }

        if (operation == 1) {
            isTVShowInFavorite = false;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mTVShow.getTitle() +
                    " has been removed from your favorite list.");
        }

        if (operation == 2) {
            isTVShowInWatchlist = false;
            setAppropriateWatchlistFab();
            Utilities.displayToast(getContext(), mTVShow.getTitle() +
                    " has been removed from your watchlist.");
        }
    }

    private void addMovie(int operation) {
        if (mTheatreViewModel.findOneById(mTVShow.getId()) == null)
            mTheatreViewModel.insert(mTVShow);

        for (TrailerEntity entity : mTrailerEntities) {
            mTrailerViewModel.insert(entity);
        }

        if (operation == 1) {
            SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                    mTheatreSaveTypeViewModel.findOneByType("Favorites").getId(), mTVShow.getId());
            mSaveTypeHasTheatreViewModel.insert(joinEntity);

            isTVShowInFavorite = true;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mTVShow.getTitle() +
                    " has been added to your favorite list.");
        }

        if (operation == 2) {
            SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                    mTheatreSaveTypeViewModel.findOneByType("Watchlist").getId(), mTVShow.getId());
            mSaveTypeHasTheatreViewModel.insert(joinEntity);

            isTVShowInWatchlist = true;
            setAppropriateWatchlistFab();
            Utilities.displayToast(getContext(), mTVShow.getTitle() +
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
        if (isTVShowInFavorite) {
            mFabActionFavorite.setImageResource(R.drawable.ic_action_favorite);
            mFabCardFavorite.setText(getString(R.string.label_remove_from_favorite));
        } else {
            mFabActionFavorite.setImageResource(R.drawable.ic_action_not_favorite);
            mFabCardFavorite.setText(getString(R.string.label_add_to_favorite));
        }
    }

    private void setAppropriateWatchlistFab() {
        if (isTVShowInWatchlist) {
            mFabActionWatchlist.setImageResource(R.drawable.ic_action_watchlist_added);
            mFabCardWatchlist.setText(getString(R.string.label_remove_from_watchlist));
        } else {
            mFabActionWatchlist.setImageResource(R.drawable.ic_action_watchlist_add);
            mFabCardWatchlist.setText(getString(R.string.label_add_to_watchlist));
        }
    }

}
