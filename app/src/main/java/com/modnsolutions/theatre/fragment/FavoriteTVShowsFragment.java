package com.modnsolutions.theatre.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVShowsFragment extends Fragment {


    public FavoriteTVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tvshows, container, false);
    }

}
