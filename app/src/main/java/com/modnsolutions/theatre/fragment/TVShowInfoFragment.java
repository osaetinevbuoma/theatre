package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;
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
import com.modnsolutions.theatre.adapter.TVShowRecommendedAdapter;
import com.modnsolutions.theatre.adapter.TVShowSimilarAdapter;
import com.modnsolutions.theatre.adapter.TVShowVideoAdapter;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.entity.TrailerEntity;
import com.modnsolutions.theatre.db.viewmodel.SaveTypeHasTheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.SeasonViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreSaveTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TrailerViewModel;
import com.modnsolutions.theatre.loader.TVShowDetailsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class TVShowInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<JSONObject>> {
    private TVShowVideoAdapter mTrailerAdapter;
    private TVShowRecommendedAdapter mRecommendedAdapter;
    private TVShowSimilarAdapter mSimilarAdapter;
    private LoaderManager mLoaderManager;
    private static final String TV_SHOW_ID_BUNDLE = "TV_SHOW_ID_BUNDLE";
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
    private SeasonViewModel mSeasonViewModel;
    private TheatreTypeViewModel mTheatreTypeViewModel;
    private SaveTypeHasTheatreViewModel mSaveTypeHasTheatreViewModel;
    private TheatreEntity mTVShow;
    private boolean isTVShowInFavorite = false;
    private boolean isTVShowInWatchlist = false;

    private List<JSONObject> mTVShowData = new LinkedList<>();

    public static final String TV_SHOW_EXTRA = "TV_SHOW_EXTRA";

    public TVShowInfoFragment() {
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

        mRootView = inflater.inflate(R.layout.fragment_tvshow_info, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            int tvShowID = intent.getIntExtra(TV_SHOW_EXTRA, -1);

            mTheatreViewModel = ViewModelProviders.of(this).get(TheatreViewModel.class);
            mTrailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
            mSeasonViewModel = ViewModelProviders.of(this).get(SeasonViewModel.class);
            mTheatreTypeViewModel = ViewModelProviders.of(this).get(TheatreTypeViewModel.class);
            mSaveTypeHasTheatreViewModel = ViewModelProviders.of(this)
                    .get(SaveTypeHasTheatreViewModel.class);
            mTheatreSaveTypeViewModel = ViewModelProviders.of(this)
                    .get(TheatreSaveTypeViewModel.class);

            // Check if movie is in favorite or watchlist
            mTVShow = mTheatreViewModel.findOneByRemoteId(tvShowID);
            if (mTVShow != null) {
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

            // Trailer recyclerview
            mTrailerAdapter = new TVShowVideoAdapter(getContext());
            RecyclerView trailersRV = mRootView.findViewById(R.id.trailers);
            trailersRV.setAdapter(mTrailerAdapter);
            trailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended recyclerview
            mRecommendedAdapter = new TVShowRecommendedAdapter(getContext());
            RecyclerView recommendedRV = mRootView.findViewById(R.id.recommended);
            recommendedRV.setAdapter(mRecommendedAdapter);
            recommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar recyclerview
            mSimilarAdapter = new TVShowSimilarAdapter(getContext());
            RecyclerView similarRV = mRootView.findViewById(R.id.similar);
            similarRV.setAdapter(mSimilarAdapter);
            similarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(TV_SHOW_ID_BUNDLE, tvShowID);
                mLoaderManager.restartLoader(1, bundle, this);
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
        return new TVShowDetailsAsyncTaskLoader(getContext(), args.getInt(TV_SHOW_ID_BUNDLE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        mTVShowData = data;

        final JSONObject tvShowDetails = data.get(0);
        JSONObject tvShowCredits = data.get(1);
        JSONObject tvShowVideos = data.get(2);
        JSONObject tvShowRecommendations = data.get(3);
        JSONObject tvShowSimilar = data.get(4);

        getActivity().findViewById(R.id.loading).setVisibility(View.GONE);
        mRootView.findViewById(R.id.loading).setVisibility(View.GONE);

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

        try {
            // Load poster and backdrop images
            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            tvShowDetails.getString("backdrop_path"))
                    .fitCenter()
                    .into(tvShowBackdrop);
            tvShowBackdrop.setContentDescription(tvShowDetails.getString("name"));

            Glide.with(getActivity())
                    .load(BuildConfig.IMAGE_BASE_URL + "/w185" +
                            tvShowDetails.getString("poster_path"))
                    .fitCenter()
                    .into(tvShowPoster);
            tvShowPoster.setContentDescription(tvShowDetails.getString("name"));

            // Add custom app bar title using the movie title
            // Show title when appbar is collapsed. Remove title when app bar is expanded.
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    try {
                        if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                            String title = tvShowDetails.getString("name");
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

            // TV Show brief details
            tvShowName.setText(tvShowDetails.getString("name"));
            tvShowFirstAirDate.setText(Utilities.formatDate(tvShowDetails
                    .getString("first_air_date")));
            tvShowEpisodeRuntime.setText(Utilities.convertMinutesToStringTime((int)
                    tvShowDetails.getJSONArray("episode_run_time").get(0)));
            tvShowGenre.setText(Utilities.extractNamesFromArray(tvShowDetails.getJSONArray(
                    "genres")));
            tvShowRating.setText(String.valueOf(tvShowDetails.getDouble("vote_average")));

            // Movie Info
            overview.setText(tvShowDetails.getString("overview"));
            seasons.setText(String.valueOf(tvShowDetails.getInt("number_of_seasons")));
            episodes.setText(String.valueOf(tvShowDetails.getInt("number_of_episodes")));
            website.setText(tvShowDetails.getString("homepage"));
            lastAired.setText(Utilities.formatDate(tvShowDetails
                    .getString("last_air_date")));

            // Get cast members
            cast.setText(Utilities.extractNamesFromArray(tvShowCredits.getJSONArray(
                    "cast")));

            // Set video trailers
            List<JSONObject> videos = new LinkedList<>();
            for (int i = 0; i < tvShowVideos.getJSONArray("results").length(); i++) {
                JSONObject video = tvShowVideos.getJSONArray("results").getJSONObject(i);
                videos.add(video);
            }
            mTrailerAdapter.removeAll();
            mTrailerAdapter.setVideos(videos);

            // Set recommended tv shows
            List<JSONObject> recTVShows = new LinkedList<>();
            for (int i = 0; i < tvShowRecommendations.getJSONArray("results").length();
                 i++) {
                JSONObject tvShow = tvShowRecommendations.getJSONArray("results")
                        .getJSONObject(i);
                recTVShows.add(tvShow);
            }
            mRecommendedAdapter.removeAll();
            mRecommendedAdapter.setTVShows(recTVShows);

            // Set similar tv shows
            List<JSONObject> similarTVShows = new LinkedList<>();
            for (int i = 0; i < tvShowSimilar.getJSONArray("results").length();
                 i++) {
                JSONObject tvShow = tvShowSimilar.getJSONArray("results")
                        .getJSONObject(i);
                similarTVShows.add(tvShow);
            }
            mSimilarAdapter.removeAll();
            mSimilarAdapter.setTVShows(similarTVShows);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<JSONObject>> loader) {

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
        JSONObject tvShowDetails = mTVShowData.get(0);
        JSONObject tvShowCredits = mTVShowData.get(1);
        JSONObject tvShowVideos = mTVShowData.get(2);

        try {
            if (mTVShow == null) {
                mTVShow = new TheatreEntity();
                mTVShow.setTheatreTypeId(mTheatreTypeViewModel.findOneByType("TVShows").getId());
                mTVShow.setRemoteId(tvShowDetails.getInt("id"));
                mTVShow.setBackdropPath(tvShowDetails.getString("backdrop_path"));
                mTVShow.setFirstAirDate(tvShowDetails.getString("first_air_date"));
                mTVShow.setGenre(Utilities.extractNamesFromArray(tvShowDetails.getJSONArray(
                        "genres")));
                mTVShow.setWebsite(tvShowDetails.getString("homepage"));
                mTVShow.setOverview(tvShowDetails.getString("overview"));
                mTVShow.setPosterPath(tvShowDetails.getString("poster_path"));
                mTVShow.setEpisodeRuntime((int) tvShowDetails.getJSONArray("episode_run_time")
                        .get(0));
                mTVShow.setNumberOfSeasons(tvShowDetails.getInt("number_of_seasons"));
                mTVShow.setNumberOfEpisodes(tvShowDetails.getInt("number_of_episodes"));
                mTVShow.setTitle(tvShowDetails.getString("name"));
                mTVShow.setOriginalTitle(tvShowDetails.getString("original_name"));
                mTVShow.setRating(tvShowDetails.getDouble("vote_average"));
                mTVShow.setLastAirDate(tvShowDetails.getString("last_air_date"));
                mTVShow.setCast(Utilities.extractNamesFromArray(tvShowCredits.getJSONArray(
                        "cast")));

                mTheatreViewModel.insert(mTVShow);
                mTVShow = mTheatreViewModel.findOneByRemoteId(mTVShow.getRemoteId());
            }

            if (operation == 1) {
                SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                        mTheatreSaveTypeViewModel.findOneByType("Favorites").getId(),
                        mTVShow.getId());
                mSaveTypeHasTheatreViewModel.insert(joinEntity);
            }

            if (operation == 2) {
                SaveTypeHasTheatreEntity joinEntity = new SaveTypeHasTheatreEntity(
                        mTheatreSaveTypeViewModel.findOneByType("Watchlist").getId(),
                        mTVShow.getId());
                mSaveTypeHasTheatreViewModel.insert(joinEntity);
            }

            for (int i = 0; i < tvShowDetails.getJSONArray("seasons").length(); i++) {
                JSONObject season = tvShowDetails.getJSONArray("seasons").getJSONObject(i);
                if (season.getInt("season_number") != 0) {
                    SeasonEntity seasonEntity = new SeasonEntity(mTVShow.getId(),
                            season.getInt("id"), season.getString("air_date"),
                            season.getInt("episode_count"), season.getString("name"),
                            season.getString("overview"), season.getString("poster_path"),
                            season.getInt("season_number"));

                    if (mSeasonViewModel.findByRemoteId(seasonEntity.getRemoteId()) == null) {
                        mSeasonViewModel.insert(seasonEntity);
                    }
                }
            }

            for (int i = 0; i < tvShowVideos.getJSONArray("results").length(); i++) {
                JSONObject video = tvShowVideos.getJSONArray("results").getJSONObject(i);
                TrailerEntity trailer = new TrailerEntity(video.getString("id"),
                        video.getString("name"), video.getString("key"));
                trailer.setTheatreId(mTVShow.getId());

                mTrailerViewModel.insert(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (operation == 1) {
            isTVShowInFavorite = true;
            setAppropriateFavoriteFab();
            Utilities.displayToast(getContext(), mTVShow.getTitle() +
                    " has been added to your favorite list.");
        }

        if (operation == 2) {
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
