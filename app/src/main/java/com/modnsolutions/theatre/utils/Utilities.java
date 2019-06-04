package com.modnsolutions.theatre.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.MovieType;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.asynctask.FetchMoviesAsyncTask;

import java.lang.ref.WeakReference;

public class Utilities {
    private static WeakReference<RecyclerView> mRecyclerView;
    private static WeakReference<ProgressBar> mLoading;
    private static WeakReference<ProgressBar> mLoadingMore;
    private static WeakReference<MovieAdapter> mAdapter;
    private static int mCurrentPage;

    /**
     * Utilities function to start an activity
     *
     * @param context
     * @param cls
     */
    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * To avoid duplication of code in movie fragments, this method creates the views in the
     * inflated layout and loads movies from server. Scrolling down to bottom of Recycler View loads
     * more movies.
     *
     * @param rootView Inflated fragment layout.
     */
    public static void onMoviesCreateView(View rootView, final MovieType movieType) {
        int page = 1;
        mCurrentPage = page;
        mLoading = new WeakReference<>((ProgressBar) rootView.findViewById(R.id.loading));
        mLoadingMore = new WeakReference<>((ProgressBar) rootView.findViewById(R.id.loading_more));
        mRecyclerView = new WeakReference<>((RecyclerView) rootView.findViewById(R.id.recyclerview));
        mAdapter = new WeakReference<>(new MovieAdapter(rootView.getContext()));
        mRecyclerView.get().setAdapter(mAdapter.get());
        mRecyclerView.get().setLayoutManager(new GridLayoutManager(rootView.getContext(),
                3));

        // TODO: Check internet connectivity if fetching from remote server.
        new FetchMoviesAsyncTask(mLoading.get(), mAdapter.get(), movieType).execute(mCurrentPage);

        mRecyclerView.get().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(recyclerView, movieType);
            }
        });
    }

    /**
     * Load more movies from DB/remote server
     * @param recyclerView
     */
    private static void loadMore(RecyclerView recyclerView, MovieType movieType) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mAdapter.get().getItemCount() - 1) {
            mLoadingMore.get().setVisibility(View.VISIBLE);
            mCurrentPage += 1;
            new FetchMoviesAsyncTask(mLoadingMore.get(), mAdapter.get(), movieType)
                    .execute(mCurrentPage);
            mRecyclerView.get().scrollToPosition(lastPosition + 1);
        }
    }

}
