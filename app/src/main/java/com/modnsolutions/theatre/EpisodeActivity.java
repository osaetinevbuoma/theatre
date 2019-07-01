package com.modnsolutions.theatre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.modnsolutions.theatre.fragment.TheatreTVShowDetailsEpisodeFragment;

public class EpisodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.tv_show_episode_fragment, new TheatreTVShowDetailsEpisodeFragment())
                .commit();
    }
}
