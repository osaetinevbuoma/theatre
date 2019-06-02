package com.modnsolutions.theatre.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modnsolutions.theatre.R;

public class MoviesUpcomingFragment extends Fragment {

    private OnMoviesUpcomingFragmentListener mListener;

    public MoviesUpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_upcoming, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMoviesUpcomingFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoviesUpcomingFragmentListener) {
            mListener = (OnMoviesUpcomingFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMoviesUpcomingFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMoviesUpcomingFragmentListener {
        // TODO: Update argument type and name
        void onMoviesUpcomingFragmentInteraction(Uri uri);
    }
}
