package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.MovieRecommendedAdapter;
import com.modnsolutions.theatre.adapter.MovieSimilarAdapter;
import com.modnsolutions.theatre.adapter.MovieVideoAdapter;
import com.modnsolutions.theatre.asynctask.FetchMovieDetailsAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment {
    private int mMovieId;
    private RecyclerView mTrailersRV;
    private RecyclerView mRecommendedRV;
    private RecyclerView mSimilarRV;
    private MovieVideoAdapter mTrailersAdapter;
    private MovieRecommendedAdapter mMovieRecommendedAdapter;
    private MovieSimilarAdapter mMovieSimilarAdapter;

    public static final String MOVIE_ID_INTENT = "MOVIE_ID_INTENT";

    public MovieInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mMovieId = intent.getIntExtra(MOVIE_ID_INTENT, -1);

            // Trailers recyclerview
            mTrailersRV = rootView.findViewById(R.id.trailers);
            mTrailersAdapter = new MovieVideoAdapter(getContext());
            mTrailersRV.setAdapter(mTrailersAdapter);
            mTrailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended movies recyclerview
            mMovieRecommendedAdapter = new MovieRecommendedAdapter(getContext());
            mRecommendedRV = rootView.findViewById(R.id.recommended);
            mRecommendedRV.setAdapter(mMovieRecommendedAdapter);
            mRecommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar movies recyclerview
            mMovieSimilarAdapter = new MovieSimilarAdapter(getContext());
            mSimilarRV = rootView.findViewById(R.id.similar);
            mSimilarRV.setAdapter(mMovieSimilarAdapter);
            mSimilarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Fetch results from server
            if (Utilities.checkInternetConnectivity(getContext()))
                new FetchMovieDetailsAsyncTask(getActivity(), rootView, mTrailersAdapter,
                        mMovieRecommendedAdapter, mMovieSimilarAdapter).execute(mMovieId);
            else {
                rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return rootView;
    }
}
