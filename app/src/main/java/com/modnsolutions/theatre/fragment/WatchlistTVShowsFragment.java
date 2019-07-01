package com.modnsolutions.theatre.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TheatreWatchlistAdapter;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.WatchlistEntity;
import com.modnsolutions.theatre.db.viewmodel.SaveTypeHasTheatreViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreSaveTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.WatchlistViewModel;

public class WatchlistTVShowsFragment extends Fragment {


    public WatchlistTVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_watchlist_tvshows, container,
                false);

        final TextView noTheatre = rootView.findViewById(R.id.no_theatre);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        final TheatreWatchlistAdapter adapter = new TheatreWatchlistAdapter(getContext(), false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        TheatreTypeViewModel theatreTypeViewModel = ViewModelProviders.of(this)
                .get(TheatreTypeViewModel.class);
        TheatreSaveTypeViewModel typeViewModel = ViewModelProviders.of(this)
                .get(TheatreSaveTypeViewModel.class);
        SaveTypeHasTheatreViewModel viewModel = ViewModelProviders.of(this)
                .get(SaveTypeHasTheatreViewModel.class);
        int theatreTypeId = theatreTypeViewModel.findOneByType("TVShows").getId();
        int typeId = typeViewModel.findOneByType("Watchlist").getId();
        viewModel.findByTheatreType(typeId, theatreTypeId).observe(this,
                new Observer<PagedList<TheatreEntity>>() {
            @Override
            public void onChanged(PagedList<TheatreEntity> theatreEntities) {
                if (theatreEntities.size() == 0) {
                    noTheatre.setVisibility(View.VISIBLE);
                    noTheatre.setText(getString(R.string.watchlist_tvshow_no_theatre));
                } else {
                    adapter.submitList(theatreEntities);
                    noTheatre.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }

}
