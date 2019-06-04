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

import com.modnsolutions.theatre.MovieType;
import com.modnsolutions.theatre.asynctask.FetchMoviesAsyncTask;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;

public class MoviesNowPlayingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private ProgressBar mLoadingMore;
    private MovieAdapter mAdapter;
    private int page = 1;
    private int mPosition;

    public MoviesNowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies_now_playing, container, false);

        mLoading = rootView.findViewById(R.id.loading);
        mLoadingMore = rootView.findViewById(R.id.loading_more);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // TODO: Check internet connectivity if fetching from remote server.
        new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.NOW_PLAYING).execute(page);

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
            mPosition = lastPosition + 1; // might remove soon.
            new FetchMoviesAsyncTask(mLoadingMore, mAdapter, MovieType.NOW_PLAYING).execute(page);
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }
}
