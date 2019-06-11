package com.modnsolutions.theatre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.modnsolutions.theatre.fragment.TVShowEpisodesFragment;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;

public class TVShowEpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_episodes);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tv_show_episode_fragment, new TVShowEpisodesFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int tvShowID = getIntent().getIntExtra(TVShowInfoFragment.TV_SHOW_EXTRA,
                    -1);
            int currentTab = getIntent().getIntExtra(TVShowsDetailsActivity.TV_SHOW_DETAILS_INTENT,
                    -1);
            Intent intent = new Intent(this, TVShowsDetailsActivity.class);
            intent.putExtra(TVShowInfoFragment.TV_SHOW_EXTRA, tvShowID);
            intent.putExtra(TVShowsDetailsActivity.TV_SHOW_DETAILS_INTENT, currentTab);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
