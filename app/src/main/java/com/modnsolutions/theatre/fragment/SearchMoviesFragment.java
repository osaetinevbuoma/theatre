package com.modnsolutions.theatre.fragment;

import android.app.SearchManager;
import android.content.Context;
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
import com.modnsolutions.theatre.adapter.SearchMoviesAdapter;
import com.modnsolutions.theatre.loader.SearchAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


public class SearchMoviesFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<JSONObject> {

    private OnSearchMoviesFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private SearchMoviesAdapter mAdapter;
    private int mCurrentPage = 1;
    private String mQueryString;
    private LoaderManager mLoaderManager;
    private int LOADER_ID = 1;

    public SearchMoviesFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_search_movies, container,
                false);

        Intent intent = getActivity().getIntent();
        if (intent != null && Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQueryString = intent.getStringExtra(SearchManager.QUERY);
            mLoading = rootView.findViewById(R.id.loading);
            mAdapter = new SearchMoviesAdapter(getContext(), mListener);
            mRecyclerView = rootView.findViewById(R.id.recyclerview);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    loadMore(recyclerView);
                }
            });

            if (Utilities.checkInternetConnectivity(getContext())) {
                mLoaderManager.restartLoader(LOADER_ID, null, this);
            } else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchMoviesFragmentInteractionListener) {
            mListener = (OnSearchMoviesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchMoviesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Load more search results.
     * @param recyclerView
     */
    private void loadMore(RecyclerView recyclerView) {
        int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mAdapter.getItemCount() - 1) {
            mLoading.setVisibility(View.VISIBLE);
            mCurrentPage += 1;
            if (Utilities.checkInternetConnectivity(getContext()))
                mLoaderManager.restartLoader(LOADER_ID, null, this);
            else {
                mLoading.setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
            mRecyclerView.scrollToPosition(lastPosition + 1);
        }
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        return new SearchAsyncTaskLoader(getContext(), mQueryString, mCurrentPage, 1);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        try {
            if (mLoading != null) mLoading.setVisibility(View.GONE);
            List<JSONObject> results = new LinkedList<>();
            for (int i = 0; i < data.getJSONArray("results").length(); i++) {
                JSONObject result = data.getJSONArray("results").getJSONObject(i);
                results.add(result);
            }
            mAdapter.setResults(results);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }


    public interface OnSearchMoviesFragmentInteractionListener {
        void onDisplayMovieDetails(int movieID);
    }
}
