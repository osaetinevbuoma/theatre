package com.modnsolutions.theatre.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowSeasonsAdapter;
import com.modnsolutions.theatre.asynctask.FetchTVShowSeasonsAsyncTask;
import com.modnsolutions.theatre.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowSeasonsFragment extends Fragment {
    private int mTVShowID;
    private RecyclerView mRecyclerView;
    private TVShowSeasonsAdapter mAdapter;
    private OnTVShowSeasonsFragmentInteraction mListener;

    public static final String SEASON_EXTRA_INTENT = "SEASON_EXTRA_INTENT";

    public TVShowSeasonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tvshow_seasons, container,
                false);

        mAdapter = new TVShowSeasonsAdapter(getContext(), mListener);
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mTVShowID = intent.getIntExtra(TVShowInfoFragment.TV_SHOW_EXTRA, -1);
            mAdapter.setTVShowID(mTVShowID);

            if (Utilities.checkInternetConnectivity(getContext())) {
                new FetchTVShowSeasonsAsyncTask(rootView, mAdapter).execute(mTVShowID);
            } else {
                rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTVShowSeasonsFragmentInteraction) {
            mListener = (OnTVShowSeasonsFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTVShowSeasonsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnTVShowSeasonsFragmentInteraction {
        void onDisplaySeasonEpisodes(int tvShowID, int seasonNum);
    }

}
