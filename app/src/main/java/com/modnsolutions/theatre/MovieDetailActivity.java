package com.modnsolutions.theatre;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.MovieDetailPagerAdapter;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.entity.TheatreTypeEntity;
import com.modnsolutions.theatre.db.viewmodel.TheatreSaveTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreTypeViewModel;
import com.modnsolutions.theatre.db.viewmodel.TheatreViewModel;

import static com.modnsolutions.theatre.fragment.MovieInfoFragment.MOVIE_ID_INTENT;

public class MovieDetailActivity extends AppCompatActivity {
    private int mMovieId;
    private TheatreEntity mTheatreEntity;
    private TheatreViewModel mTheatreViewModel;
    private TheatreSaveTypeViewModel mTheatreSaveTypeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Replace default action bar title with custom view (textview)
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Don't show default title
        getSupportActionBar().setDisplayShowCustomEnabled(true); // Display custom view
        getSupportActionBar().setCustomView(R.layout.app_bar_title);

        // Set up tab layout for movie details
        TabLayout tabLayout = findViewById(R.id.movie_detail_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_info)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_reviews)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = findViewById(R.id.movie_detail_pager);
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(
                getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();
        mMovieId = intent.getIntExtra(MOVIE_ID_INTENT, -1);

        mTheatreSaveTypeViewModel = ViewModelProviders.of(this)
                .get(TheatreSaveTypeViewModel.class);
        mTheatreViewModel = ViewModelProviders.of(this).get(TheatreViewModel.class);
        mTheatreEntity = mTheatreViewModel.findOneByRemoteId(mMovieId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_program_detail, menu);

        MenuItem favoriteIcon = menu.getItem(0);
        MenuItem watchlistIcon = menu.getItem(1);

        if (mTheatreEntity != null && mTheatreEntity.getTheatreSaveTypeId() ==
                mTheatreSaveTypeViewModel.findOneByType("Favorite").getId()) {
            favoriteIcon.setIcon(R.drawable.ic_action_favorite);
        }

        if (mTheatreEntity != null && mTheatreEntity.getTheatreSaveTypeId() ==
                mTheatreSaveTypeViewModel.findOneByType("Watchlist").getId()) {
            watchlistIcon.setIcon(R.drawable.ic_action_watchlist_added);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                break;

            case R.id.action_watchlist:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
