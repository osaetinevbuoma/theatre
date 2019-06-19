package com.modnsolutions.theatre.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowAiringTodayAdapter;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowAiringTodayEntity;
import com.modnsolutions.theatre.db.viewmodel.tvshow.TVShowAiringTodayViewModel;
import com.modnsolutions.theatre.enums.TVShowType;
import com.modnsolutions.theatre.loader.TVShowAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowsAiringTodayFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<JSONObject>> {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private TVShowAiringTodayAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mPosition;
    private static final int LOADER_ID = 3;
    private LoaderManager mLoaderManager;
    private static final String CURRENT_PAGE = "com.modnsolutions.TVShowsAiringTodayFragment" +
            ".CURRENT_PAGE";
    private TVShowAiringTodayViewModel mTVShowViewModel;


    public TVShowsAiringTodayFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_tvshows_airing_today, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new TVShowAiringTodayAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mTVShowViewModel = ViewModelProviders.of(this).get(TVShowAiringTodayViewModel.class);
        mTVShowViewModel.fetchTVShows().observe(this, new Observer<PagedList<TVShowAiringTodayEntity>>() {
            @Override
            public void onChanged(PagedList<TVShowAiringTodayEntity> tvShowAiringTodayEntities) {
                mAdapter.submitList(tvShowAiringTodayEntities);
                mLoading.setVisibility(View.GONE);
            }
        });

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
        return new TVShowAsyncTaskLoader(getContext(), TVShowType.AIRING_TODAY, args.getInt(CURRENT_PAGE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        try {
            mLoading.setVisibility(View.GONE);

            if (mCurrentPage == 1 && mTVShowViewModel.findAllTVShows().size() > 0 && mTVShowViewModel
                    .findAllTVShows().get(0).getExpiryDate().before(new Date())) {
                mTVShowViewModel.deleteAll();
            }

            for (int i = 0; i < data.size(); i++) {
                JSONObject tvShow = data.get(i);
                if (mTVShowViewModel.findTVShowById(tvShow.getInt("id")) == null) {
                    Date dateDownloaded = new Date();
                    Date expiryDate = Utilities.expiryDate();
                    TVShowAiringTodayEntity entity = new TVShowAiringTodayEntity(tvShow.getInt("id"),
                            tvShow.getString("backdrop_path"), tvShow.getString(
                            "first_air_date"), tvShow.getString("name"),
                            tvShow.getString("original_name"), tvShow.getString(
                            "overview"), tvShow.getString("poster_path"),
                            tvShow.getInt("vote_average"), dateDownloaded, expiryDate);
                    mTVShowViewModel.insert(entity);
                }
            }

            mRecyclerView.scrollToPosition(mPosition);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<JSONObject>> loader) {

    }
}
