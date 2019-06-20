package com.modnsolutions.theatre.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowEpisodesAdapter;
import com.modnsolutions.theatre.loader.TVShowEpisodesAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TVShowEpisodesFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<JSONObject> {
    private int mTVShowID;
    private int mSeasonNumber;
    private TVShowEpisodesAdapter mAdapter;
    private RecyclerView mRecyclerview;
    private View mRootView;
    private LoaderManager mLoaderManager;
    private final int LOADER_ID = 1;

    public TVShowEpisodesFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_tvshow_episodes, container,
                false);

        mAdapter = new TVShowEpisodesAdapter(getContext());
        mRecyclerview = mRootView.findViewById(R.id.recyclerview);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerview.addItemDecoration(itemDecoration);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mTVShowID = intent.getIntExtra(TVShowInfoFragment.TV_SHOW_EXTRA, -1);
            mSeasonNumber = intent.getIntExtra(TVShowSeasonsFragment.SEASON_EXTRA_INTENT,
                    -1);

            if (Utilities.checkInternetConnectivity(getContext())) {
                mLoaderManager.restartLoader(LOADER_ID, null, this);
            } else {
                mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return mRootView;
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        return new TVShowEpisodesAsyncTaskLoader(getContext(), mTVShowID, mSeasonNumber);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        try {
            mRootView.findViewById(R.id.loading).setVisibility(View.GONE);

            List<JSONObject> episodes = new LinkedList<>();
            for (int i = 0; i < data.getJSONArray("episodes").length(); i++) {
                episodes.add(data.getJSONArray("episodes").getJSONObject(i));
            }
            mAdapter.setEpisodes(episodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
