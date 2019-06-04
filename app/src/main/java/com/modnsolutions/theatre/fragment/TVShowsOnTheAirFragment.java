package com.modnsolutions.theatre.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.TVShowType;
import com.modnsolutions.theatre.adapter.TVShowAdapter;
import com.modnsolutions.theatre.asynctask.FetchTVShowsAsyncTask;

public class TVShowsOnTheAirFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private ProgressBar mLoadingMore;
    private TVShowAdapter mAdapter;
    private int page = 1;

    public TVShowsOnTheAirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tvshows_on_the_air, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mLoadingMore = rootView.findViewById(R.id.loading_more);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new TVShowAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // TODO: Check internet connectivity if fetching from remote server.
        new FetchTVShowsAsyncTask(mLoading, mAdapter, TVShowType.ON_THE_AIR).execute(page);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(recyclerView);
            }
        });

        return rootView;
    }

    /**
     * Load more movies from DB/remote server
     * @param recyclerView
     */
    private void loadMore(RecyclerView recyclerView) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mAdapter.getItemCount() - 1) {
            mLoadingMore.setVisibility(View.VISIBLE);
            page += 1;
            new FetchTVShowsAsyncTask(mLoadingMore, mAdapter, TVShowType.ON_THE_AIR).execute(page);
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }
}
