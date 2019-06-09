package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.ReviewsAdapter;
import com.modnsolutions.theatre.asynctask.FetchReviewsAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowReviewsFragment extends Fragment {
    private ProgressBar mLoading;
    private RecyclerView mReviewsRV;
    private ReviewsAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mID;

    public TVShowReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mID = intent.getIntExtra(TVShowInfoFragment.TV_SHOW_EXTRA, -1);

            mLoading = rootView.findViewById(R.id.loading);
            mAdapter = new ReviewsAdapter(getContext());
            mReviewsRV = rootView.findViewById(R.id.reviews);
            mReviewsRV.setAdapter(mAdapter);
            mReviewsRV.setLayoutManager(new LinearLayoutManager(getContext()));

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);
            mReviewsRV.addItemDecoration(itemDecoration);

            if (Utilities.checkInternetConnectivity(getContext()))
                new FetchReviewsAsyncTask(mLoading, mAdapter, false).execute(mID,
                        mCurrentPage);
            else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }

            mReviewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    loadMore(recyclerView);
                }
            });
        }

        return rootView;
    }

    /**
     * Load more reviews
     *
     * @param recyclerView
     */
    private void loadMore(RecyclerView recyclerView) {
        int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mAdapter.getItemCount() - 1) {
            mLoading.setVisibility(View.VISIBLE);
            mCurrentPage += 1;
            if (Utilities.checkInternetConnectivity(getContext())) {
                new FetchReviewsAsyncTask(mLoading, mAdapter, false).execute(mID,
                        mCurrentPage);
            } else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mReviewsRV.scrollToPosition(lastPosition + 1);
        }
    }

}
