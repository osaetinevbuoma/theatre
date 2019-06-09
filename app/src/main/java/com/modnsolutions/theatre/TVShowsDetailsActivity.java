package com.modnsolutions.theatre;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.MovieDetailPagerAdapter;
import com.modnsolutions.theatre.adapter.TVShowDetailPagerAdapter;
import com.modnsolutions.theatre.adapter.TVShowsPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TVShowsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Replace default action bar title with custom view (textview)
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Don't show default title
        getSupportActionBar().setDisplayShowCustomEnabled(true); // Display custom view
        getSupportActionBar().setCustomView(R.layout.app_bar_title);

        // Set up tab layout for movie details
        TabLayout tabLayout = findViewById(R.id.tv_show_detail_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_info)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_reviews)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_seasons)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager viewPager = findViewById(R.id.tv_show_detail_pager);
        TVShowDetailPagerAdapter pagerAdapter = new TVShowDetailPagerAdapter(
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_program_detail, menu);
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
