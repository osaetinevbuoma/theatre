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

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieAdapter;
import com.modnsolutions.theatre.enums.MovieType;
import com.modnsolutions.theatre.loader.MovieAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONObject;

import java.util.List;

public class MoviesNowPlayingFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<JSONObject>> {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private MovieAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mPosition;
    private static final int LOADER_ID = 1;
    private LoaderManager mLoaderManager;
    private static final String CURRENT_PAGE = "com.modnsolutions.MoviesNowPlayingFragment" +
            ".CURRENT_PAGE";

    public MoviesNowPlayingFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_movies_now_playing, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (Utilities.checkInternetConnectivity(getContext())) {
            Bundle bundle = new Bundle();
            bundle.putInt(CURRENT_PAGE, mCurrentPage);
            mLoaderManager.restartLoader(LOADER_ID, bundle, this);
        } else {
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
            mPosition = lastPosition + 1;

            if (Utilities.checkInternetConnectivity(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt(CURRENT_PAGE, mCurrentPage);
                mLoaderManager.restartLoader(LOADER_ID, bundle, this);
            } else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }
    }

    @NonNull
    @Override
    public Loader<List<JSONObject>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieAsyncTaskLoader(getContext(), MovieType.NOW_PLAYING,
                args.getInt(CURRENT_PAGE, -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        mLoading.setVisibility(View.GONE);

        // To avoid the adapter being set with the data already in the recyclerview
        if (mAdapter.getItemCount() == 0) {
            mAdapter.setMovies(data);
            mRecyclerView.scrollToPosition(mPosition);
        }
        else if (mCurrentPage * data.size() != mAdapter.getItemCount()) {
            mAdapter.setMovies(data);
            mRecyclerView.scrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<JSONObject>> loader) {

    }
}
