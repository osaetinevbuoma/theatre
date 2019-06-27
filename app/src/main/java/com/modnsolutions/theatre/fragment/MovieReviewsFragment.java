package com.modnsolutions.theatre.fragment;


import android.content.Intent;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.ReviewsAdapter;
import com.modnsolutions.theatre.loader.ReviewsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MovieReviewsFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<JSONObject> {
    private ProgressBar mLoading;
    private RecyclerView mReviewsRV;
    private ReviewsAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mID;
    private LoaderManager mLoaderManager;
    private int LOADER_ID = 2;
    private static final String MOVIE_ID_BUNDLE = "MOVIE_ID_BUNDLE";
    private static final String CURRENT_PAGE_BUNDLE = "CURRENT_PAGE_BUNDLE";

    public MovieReviewsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mID = intent.getIntExtra(MovieInfoFragment.MOVIE_ID_INTENT, -1);

            mLoading = rootView.findViewById(R.id.loading);
            mAdapter = new ReviewsAdapter(getContext());
            mReviewsRV = rootView.findViewById(R.id.reviews);
            mReviewsRV.setAdapter(mAdapter);
            mReviewsRV.setLayoutManager(new LinearLayoutManager(getContext()));

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);
            mReviewsRV.addItemDecoration(itemDecoration);

            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(MOVIE_ID_BUNDLE, mID);
                bundle.putInt(CURRENT_PAGE_BUNDLE, mCurrentPage);
                mLoaderManager.restartLoader(LOADER_ID, bundle, this);
            } else {
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
                Bundle bundle = new Bundle();
                bundle.putInt(MOVIE_ID_BUNDLE, mID);
                bundle.putInt(CURRENT_PAGE_BUNDLE, mCurrentPage);
                mLoaderManager.restartLoader(LOADER_ID, bundle, this);
            } else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mReviewsRV.scrollToPosition(lastPosition + 1);
        }
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        int currentPage = args.getInt(CURRENT_PAGE_BUNDLE, -1);
        int movieId = args.getInt(MOVIE_ID_BUNDLE, -1);

        return new ReviewsAsyncTaskLoader(getContext(), currentPage, movieId, true);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        List<JSONObject> reviews = new LinkedList<>();

        try {
            for (int i = 0; i < data.getJSONArray("results").length(); i++) {
                JSONObject review = data.getJSONArray("results").getJSONObject(i);
                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mLoading.setVisibility(View.GONE);

        // To avoid the adapter being set with the data already in the recyclerview
        if (mAdapter.getItemCount() == 0) {
            mAdapter.setReviews(reviews);
        }
        else if (mCurrentPage * reviews.size() != mAdapter.getItemCount()) {
            mAdapter.setReviews(reviews);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
