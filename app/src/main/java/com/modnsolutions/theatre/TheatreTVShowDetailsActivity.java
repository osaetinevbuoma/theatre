package com.modnsolutions.theatre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.TheatreTVShowPagerAdapter;
import com.modnsolutions.theatre.fragment.TheatreMovieDetailsFragment;
import com.modnsolutions.theatre.fragment.TheatreTVShowDetailsFragment;
import com.modnsolutions.theatre.utils.Utilities;

public class TheatreTVShowDetailsActivity extends AppCompatActivity {
    private boolean isNavigationFromFavorites = false;
    private boolean isNavigationFromWatchlist = false;

    public static final String NAVIGATION_FROM_FAVORITES =
            "com.modnsolutions.NAVIGATION_FROM_FAVORITES";
    public static final String NAVIGATION_FROM_WATCHLIST =
            "com.modnsolutions.NAVIGATION_FROM_WATCHLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_tvshow_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Replace default action bar title with custom view (textview)
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Don't show default title
        getSupportActionBar().setDisplayShowCustomEnabled(true); // Display custom view
        getSupportActionBar().setCustomView(R.layout.app_bar_title);

        // Set up tab layout for movie details
        TabLayout tabLayout = findViewById(R.id.theatre_tvshow_detail_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_info)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_seasons)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = findViewById(R.id.tvshow_detail_pager);
        TheatreTVShowPagerAdapter pagerAdapter = new TheatreTVShowPagerAdapter(
                getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                // Display FAB button only when displaying movie info fragment.
                if (tab.getPosition() == 0)
                    findViewById(R.id.fab_frame).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.fab_frame).setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
