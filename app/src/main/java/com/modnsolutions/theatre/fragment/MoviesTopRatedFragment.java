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
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.asynctask.FetchMoviesAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

public class MoviesTopRatedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private MovieAdapter mAdapter;
    private int page = 1;

    public MoviesTopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies_top_rated, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (Utilities.checkInternetConnectivity(getContext()))
            new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.TOP_RATED).execute(page);
        else {
            mLoading.setVisibility(View.GONE);
            Utilities.displayToast(getContext(), getString(R.string.no_internet));
        }

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
            mLoading.setVisibility(View.VISIBLE);
            page += 1;
            if (Utilities.checkInternetConnectivity(getContext()))
                new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.TOP_RATED).execute(page);
            else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }
}
