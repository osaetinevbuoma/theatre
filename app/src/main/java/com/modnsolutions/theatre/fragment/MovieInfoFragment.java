package com.modnsolutions.theatre.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.asynctask.FetchMovieDetailsAsyncTask;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment {
    private AppBarLayout mAppBarLayout;
    private TextView mAppBarTitle;
    private int mMovieId;

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

            // Fetch results from server
            new FetchMovieDetailsAsyncTask(getActivity(), rootView).execute(mMovieId);
        }

        return rootView;
    }
}
