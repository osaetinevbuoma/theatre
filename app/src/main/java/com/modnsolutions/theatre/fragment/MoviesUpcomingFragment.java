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

import com.modnsolutions.theatre.adapter.MovieUpcomingAdapter;
import com.modnsolutions.theatre.db.entity.movie.MovieUpcomingEntity;
import com.modnsolutions.theatre.db.viewmodel.movie.MovieUpcomingViewModel;
import com.modnsolutions.theatre.enums.MovieType;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.loader.MovieAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviesUpcomingFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<JSONObject>> {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private MovieUpcomingAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mPosition;
    private static final int LOADER_ID = 4;
    private static final String CURRENT_PAGE = "com.modnsolutions.MoviesTopRatedFragment" +
            ".CURRENT_PAGE";
    private LoaderManager mLoaderManager;
    private MovieUpcomingViewModel mMovieViewModel;

    public MoviesUpcomingFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_movies_upcoming, container,
                false);

        mLoading = rootView.findViewById(R.id.loading);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new MovieUpcomingAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mMovieViewModel = ViewModelProviders.of(this).get(MovieUpcomingViewModel.class);
        mMovieViewModel.fetchMovies().observe(this, new Observer<PagedList<MovieUpcomingEntity>>() {
            @Override
            public void onChanged(PagedList<MovieUpcomingEntity> movieUpcomingEntities) {
                mAdapter.submitList(movieUpcomingEntities);
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
        return new MovieAsyncTaskLoader(getContext(), MovieType.UPCOMING, args.getInt(
                CURRENT_PAGE, -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<JSONObject>> loader, List<JSONObject> data) {
        try {
            mLoading.setVisibility(View.GONE);

            if (mCurrentPage == 1 && mMovieViewModel.findAllMovies().size() > 0 && mMovieViewModel
                    .findAllMovies().get(0).getExpiryDate().before(new Date())) {
                mMovieViewModel.deleteAll();
            }

            for (int i = 0; i < data.size(); i++) {
                JSONObject movie = data.get(i);
                if (mMovieViewModel.findMovieById(movie.getInt("id")) == null) {
                    Date dateDownloaded = new Date();
                    Date expiryDate = Utilities.expiryDate();
                    MovieUpcomingEntity entity = new MovieUpcomingEntity(movie.getInt("id"),
                            movie.getString("backdrop_path"), movie.getString("overview"),
                            movie.getString("poster_path"), movie.getString("release_date"),
                            movie.getString("title"), movie.getString("original_title"),
                            movie.getInt("vote_average"), dateDownloaded, expiryDate);
                    mMovieViewModel.insert(entity);
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
