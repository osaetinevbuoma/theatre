package com.modnsolutions.theatre.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.MovieType;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.asynctask.FetchMoviesAsyncTask;
import com.modnsolutions.theatre.loader.MovieAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONObject;

import java.util.List;

public class MoviesNowPlayingFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private MovieAdapter mAdapter;
    private int mCurrentPage = 1;
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
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (Utilities.checkInternetConnectivity(getContext()))
            new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.NOW_PLAYING).execute(mCurrentPage);
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
            mCurrentPage += 1;
            mPosition = lastPosition + 1; // might remove soon.
            if (Utilities.checkInternetConnectivity(getContext()))
                new FetchMoviesAsyncTask(mLoading, mAdapter, MovieType.NOW_PLAYING).execute(mCurrentPage);
            else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }
}
