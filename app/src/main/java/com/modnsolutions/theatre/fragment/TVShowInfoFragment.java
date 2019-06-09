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
import com.modnsolutions.theatre.adapter.TVShowRecommendedAdapter;
import com.modnsolutions.theatre.adapter.TVShowSimilarAdapter;
import com.modnsolutions.theatre.adapter.TVShowVideoAdapter;
import com.modnsolutions.theatre.asynctask.FetchTVShowDetailsAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowInfoFragment extends Fragment {
    private int mTVShowID;
    private RecyclerView mTrailersRV;
    private RecyclerView mRecommendedRV;
    private RecyclerView mSimilarRV;
    private TVShowVideoAdapter mTrailerAdapter;
    private TVShowRecommendedAdapter mRecommendedAdapter;
    private TVShowSimilarAdapter mSimilarAdapter;

    public static final String TV_SHOW_EXTRA = "TV_SHOW_EXTRA";

    public TVShowInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tvshow_info, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mTVShowID = intent.getIntExtra(TV_SHOW_EXTRA, -1);

            // Trailer recyclerview
            mTrailerAdapter = new TVShowVideoAdapter(getContext());
            mTrailersRV = rootView.findViewById(R.id.trailers);
            mTrailersRV.setAdapter(mTrailerAdapter);
            mTrailersRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Recommended recyclerview
            mRecommendedAdapter = new TVShowRecommendedAdapter(getContext());
            mRecommendedRV = rootView.findViewById(R.id.recommended);
            mRecommendedRV.setAdapter(mRecommendedAdapter);
            mRecommendedRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            // Similar recyclerview
            mSimilarAdapter = new TVShowSimilarAdapter(getContext());
            mSimilarRV = rootView.findViewById(R.id.similar);
            mSimilarRV.setAdapter(mSimilarAdapter);
            mSimilarRV.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.HORIZONTAL, false));

            if (Utilities.checkInternetConnectivity(getContext())) {
                new FetchTVShowDetailsAsyncTask(getActivity(), rootView, mTrailerAdapter,
                        mRecommendedAdapter, mSimilarAdapter).execute(mTVShowID);
            } else {
                rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return rootView;
    }

}
