package com.modnsolutions.theatre.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.TVShowsDetailsActivity;
import com.modnsolutions.theatre.adapter.TVShowEpisodesAdapter;
import com.modnsolutions.theatre.asynctask.FetchTVShowEpisodesAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

public class TVShowEpisodesFragment extends Fragment {
    private int mTVShowID;
    private int mSeasonNumber;
    private TVShowEpisodesAdapter mAdapter;
    private RecyclerView mRecyclerview;

    public TVShowEpisodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tvshow_episodes, container,
                false);

        mAdapter = new TVShowEpisodesAdapter(getContext());
        mRecyclerview = rootView.findViewById(R.id.recyclerview);
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
                new FetchTVShowEpisodesAsyncTask(rootView, mAdapter).execute(mTVShowID,
                        mSeasonNumber);
            } else {
                rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return rootView;
    }
}
