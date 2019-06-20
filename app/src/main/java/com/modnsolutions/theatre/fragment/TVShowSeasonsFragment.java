package com.modnsolutions.theatre.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TVShowSeasonsAdapter;
import com.modnsolutions.theatre.loader.TVShowSeasonsAsyncTaskLoader;
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowSeasonsFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<JSONObject> {
    private int mTVShowID;
    private RecyclerView mRecyclerView;
    private TVShowSeasonsAdapter mAdapter;
    private OnTVShowSeasonsFragmentInteraction mListener;
    private LoaderManager mLoaderManager;
    private int LOADER_ID = 3;
    private static final String TV_SHOW_ID_BUNDLE = "TV_SHOW_ID_BUNDLE";
    private View mRootView;

    public static final String SEASON_EXTRA_INTENT = "SEASON_EXTRA_INTENT";

    public TVShowSeasonsFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_tvshow_seasons, container,
                false);

        mAdapter = new TVShowSeasonsAdapter(getContext(), mListener);
        mRecyclerView = mRootView.findViewById(R.id.recyclerview);
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
                Bundle bundle = new Bundle();
                bundle.putInt(TV_SHOW_ID_BUNDLE, mTVShowID);
                mLoaderManager.restartLoader(LOADER_ID, bundle, this);
            } else {
                mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
                Utilities.displayToast(getContext(), getString(R.string.no_internet));
            }
        }

        return mRootView;
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

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        return new TVShowSeasonsAsyncTaskLoader(getContext(), args.getInt(TV_SHOW_ID_BUNDLE,
                -1));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        try {
            mRootView.findViewById(R.id.loading).setVisibility(View.GONE);
            List<JSONObject> seasons = new LinkedList<>();
            for (int i = 0; i < data.getJSONArray("seasons").length(); i++) {
                JSONObject season = data.getJSONArray("seasons").getJSONObject(i);
                if (season.getInt("season_number") != 0) {
                    seasons.add(data.getJSONArray("seasons").getJSONObject(i));
                }
            }
            mAdapter.setSeasons(seasons);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }


    public interface OnTVShowSeasonsFragmentInteraction {
        void onDisplaySeasonEpisodes(int tvShowID, int seasonNum);
    }

}
