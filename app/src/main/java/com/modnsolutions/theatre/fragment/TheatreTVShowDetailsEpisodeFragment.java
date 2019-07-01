package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowEpisodesAdapter;
import com.modnsolutions.theatre.adapter.TheatreEpisodesAdapter;
import com.modnsolutions.theatre.db.entity.EpisodeEntity;
import com.modnsolutions.theatre.db.viewmodel.EpisodeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreViewModel;
import com.modnsolutions.theatre.loader.TVShowEpisodesAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TheatreTVShowDetailsEpisodeFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<JSONObject> {
    private View mRootView;
    private LoaderManager mLoaderManager;
    private int mSeasonNumber;
    private int mSeasonId;
    private int mTheatreRemoteId;
    private EpisodeViewModel mEpisodeViewModel;

    public static final String SEASON_ID_INTENT = "com.modnsolutions.SEASON_ID_INTENT";
    public static final String SEASON_NUMBER_INTENT = "com.modnsolutions.SEASON_NUMBER_INTENT";
    public static final String THEATRE_ID_INTENT = "com.modnsolutions.THEATRE_ID_INTENT";

    public TheatreTVShowDetailsEpisodeFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_theatre_tvshow_details_episode, container,
                false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mSeasonId = intent.getIntExtra(SEASON_ID_INTENT, -1);
            mSeasonNumber = intent.getIntExtra(SEASON_NUMBER_INTENT, -1);
            int theatreId = intent.getIntExtra(THEATRE_ID_INTENT, -1);

            // Get remote id of theatre entity
            TheatreViewModel theatreViewModel = ViewModelProviders.of(this)
                    .get(TheatreViewModel.class);
            mTheatreRemoteId = theatreViewModel.findOneById(theatreId).getRemoteId();

            final TheatreEpisodesAdapter adapter = new TheatreEpisodesAdapter(getContext());
            RecyclerView recyclerview = mRootView.findViewById(R.id.recyclerview);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecoration);

            // Observe episodes table for data.
            mEpisodeViewModel = ViewModelProviders.of(this).get(EpisodeViewModel.class);
            mEpisodeViewModel.findBySeasonId(mSeasonId).observe(this,
                    new Observer<List<EpisodeEntity>>() {
                @Override
                public void onChanged(List<EpisodeEntity> episodeEntities) {
                    adapter.removeAll(); // To avoid repeating items being downloaded.
                    adapter.setEpisodes(episodeEntities);
                    mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
                }
            });

            // Check if episodes exist in database, if not, download.
            EpisodeEntity[] entity = mEpisodeViewModel.findAllBySeasonId(mSeasonId);
            if (entity.length == 0) {
                // Download from server and save in DB.
                if (Utilities.checkInternetConnectivity(getContext())) {
                    mLoaderManager.restartLoader(1, null, this);
                }
            }
        }

        return mRootView;
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        return new TVShowEpisodesAsyncTaskLoader(getContext(), mTheatreRemoteId, mSeasonNumber);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        try {
            mRootView.findViewById(R.id.loading).setVisibility(View.GONE);

            for (int i = 0; i < data.getJSONArray("episodes").length(); i++) {
                JSONObject episode = data.getJSONArray("episodes").getJSONObject(i);

                EpisodeEntity entity = new EpisodeEntity(mSeasonId, episode.getInt("id"),
                        episode.getString("air_date"), episode.getInt("episode_number"),
                        episode.getString("name"), episode.getString("overview"),
                        episode.getString("still_path"),
                        episode.getDouble("vote_average"));

                mEpisodeViewModel.insert(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
