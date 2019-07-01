package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.adapter.TheatreSeasonsAdapter;
import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.db.viewmodel.SeasonViewModel;

import java.util.List;

import static com.modnsolutions.theatre.fragment.TheatreTVShowDetailsFragment.THEATRE_TVSHOW_ID_INTENT;

public class TheatreTVShowDetailsSeasonsFragment extends Fragment {

    public TheatreTVShowDetailsSeasonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_theatre_tvshow_details_seasons,
                container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            int theatreId = intent.getIntExtra(THEATRE_TVSHOW_ID_INTENT, -1);

            final TheatreSeasonsAdapter adapter = new TheatreSeasonsAdapter(getContext());
            RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);

            SeasonViewModel seasonViewModel = ViewModelProviders.of(this)
                    .get(SeasonViewModel.class);

            seasonViewModel.findByTheatreId(theatreId).observe(this,
                    new Observer<List<SeasonEntity>>() {
                @Override
                public void onChanged(List<SeasonEntity> seasonEntities) {
                    adapter.setSeasons(seasonEntities);
                    rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                }
            });
        }

        return rootView;
    }

}
