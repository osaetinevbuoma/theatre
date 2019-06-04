package com.modnsolutions.theatre.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.MovieType;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.asynctask.FetchMoviesAsyncTask;

public class MoviesUpcomingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private ProgressBar mLoadingMore;
    private MovieAdapter mAdapter;
    private int page = 1;

    public MoviesUpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies_upcoming, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mLoadingMore = rootView.findViewById(R.id.loading_more);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // TODO: Check internet connectivity if fetching from remote server.
        new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.UPCOMING).execute(page);

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
            new FetchMoviesAsyncTask(mLoadingMore, mAdapter, MovieType.UPCOMING).execute(page);
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }
}
