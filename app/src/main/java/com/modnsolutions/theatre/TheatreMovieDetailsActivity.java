package com.modnsolutions.theatre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.util.Util;
import com.modnsolutions.theatre.fragment.TheatreMovieDetailsFragment;
import com.modnsolutions.theatre.utils.Utilities;

public class TheatreMovieDetailsActivity extends AppCompatActivity {
    private boolean isNavigationFromFavorites = false;
    private boolean isNavigationFromWatchlist = false;

    public static final String NAVIGATION_FROM_FAVORITES =
            "com.modnsolutions.NAVIGATION_FROM_FAVORITES";
    public static final String NAVIGATION_FROM_WATCHLIST =
            "com.modnsolutions.NAVIGATION_FROM_WATCHLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Replace default action bar title with custom view (textview)
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Don't show default title
        getSupportActionBar().setDisplayShowCustomEnabled(true); // Display custom view
        getSupportActionBar().setCustomView(R.layout.app_bar_title);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.theatre_movie_details_fragment, new TheatreMovieDetailsFragment())
                .commit();

        Intent intent = getIntent();
        if (intent != null) {
            isNavigationFromFavorites = intent.getBooleanExtra(NAVIGATION_FROM_FAVORITES,
                    false);
            isNavigationFromWatchlist = intent.getBooleanExtra(NAVIGATION_FROM_WATCHLIST,
                    false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isNavigationFromWatchlist) Utilities.startActivity(this, WatchlistActivity.class);
            if (isNavigationFromFavorites) Utilities.startActivity(this, FavoriteActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }
}
