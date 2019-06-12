package com.modnsolutions.theatre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.SearchPagerAdapter;
import com.modnsolutions.theatre.fragment.MovieInfoFragment;
import com.modnsolutions.theatre.fragment.SearchMoviesFragment;
import com.modnsolutions.theatre.fragment.SearchTVShowsFragment;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;

public class SearchActivity extends AppCompatActivity implements
        SearchMoviesFragment.OnSearchMoviesFragmentInteractionListener,
        SearchTVShowsFragment.OnSearchTVShowsFragmentInteractionListener {

    public static final String ACTIVITY_TAB_INTENT = "com.modnsolutions.ACTIVITY_TAB_INTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_movies)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_tv_shows)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = findViewById(R.id.pager);
        SearchPagerAdapter adapter = new SearchPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
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

        // Set tab to TV Shows if search was done from TVShowsActivity
        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            viewPager.setCurrentItem(appData.getInt(ACTIVITY_TAB_INTENT, -1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.searchable, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;

            case R.id.action_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public void onDisplayMovieDetails(int movieID) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieInfoFragment.MOVIE_ID_INTENT, movieID);
        startActivity(intent);
    }

    @Override
    public void onDisplayTVShowDetails(int tvShowID) {
        Intent intent = new Intent(this, TVShowsDetailsActivity.class);
        intent.putExtra(TVShowInfoFragment.TV_SHOW_EXTRA, tvShowID);
        startActivity(intent);
    }
}
