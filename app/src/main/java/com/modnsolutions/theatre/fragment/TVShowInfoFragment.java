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
import com.modnsolutions.theatre.adapter.TVShowRecommendedAdapter;
import com.modnsolutions.theatre.adapter.TVShowSimilarAdapter;
import com.modnsolutions.theatre.adapter.TVShowVideoAdapter;
import com.modnsolutions.theatre.loader.TVShowDetailsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<JSONObject>> {
    private int mTVShowID;
    private RecyclerView mTrailersRV;
    private RecyclerView mRecommendedRV;
    private RecyclerView mSimilarRV;
    private TVShowVideoAdapter mTrailerAdapter;
    private TVShowRecommendedAdapter mRecommendedAdapter;
    private TVShowSimilarAdapter mSimilarAdapter;
    private LoaderManager mLoaderManager;
    private static final String TV_SHOW_ID_BUNDLE = "TV_SHOW_ID_BUNDLE";
    private final int LOADER_ID = 1;
    private View mRootView;

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
            mTVShowID = intent.getIntExtra(TV_SHOW_EXTRA, -1);

            // Trailer recyclerview
            mTrailerAdapter = new TVShowVideoAdapter(getContext());
            mTrailersRV = mRootView.findViewById(R.id.trailers);
            mTrailersRV.setAdapter(mTrailerAdapter);
            mTrailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended recyclerview
            mRecommendedAdapter = new TVShowRecommendedAdapter(getContext());
            mRecommendedRV = mRootView.findViewById(R.id.recommended);
            mRecommendedRV.setAdapter(mRecommendedAdapter);
            mRecommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar recyclerview
            mSimilarAdapter = new TVShowSimilarAdapter(getContext());
            mSimilarRV = mRootView.findViewById(R.id.similar);
            mSimilarRV.setAdapter(mSimilarAdapter);
            mSimilarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(TV_SHOW_ID_BUNDLE, mTVShowID);
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
        return new TVShowDetailsAsyncTaskLoader(getContext(), args.getInt(TV_SHOW_ID_BUNDLE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
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
}
