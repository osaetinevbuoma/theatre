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
import com.modnsolutions.theatre.utils.Utilities;

public class TVShowsTopRatedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private TVShowAdapter mAdapter;
    private int page = 1;

    public TVShowsTopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tvshows_top_rated, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new TVShowAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (Utilities.checkInternetConnectivity(getContext()))
            new FetchTVShowsAsyncTask(mLoading, mAdapter, TVShowType.TOP_RATED).execute(page);
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
                new FetchTVShowsAsyncTask(mLoading, mAdapter, TVShowType.TOP_RATED).execute(page);
            else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }

}
