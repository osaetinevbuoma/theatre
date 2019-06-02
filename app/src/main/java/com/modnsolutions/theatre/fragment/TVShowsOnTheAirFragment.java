package com.modnsolutions.theatre.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.modnsolutions.theatre.R;

public class TVShowsOnTheAirFragment extends Fragment {

    public TVShowsOnTheAirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshows_on_the_air, container, false);
    }
}
