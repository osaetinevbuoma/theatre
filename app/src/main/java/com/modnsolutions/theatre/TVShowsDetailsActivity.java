package com.modnsolutions.theatre;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.TVShowDetailPagerAdapter;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;
import com.modnsolutions.theatre.fragment.TVShowSeasonsFragment;

public class TVShowsDetailsActivity extends AppCompatActivity implements
        TVShowSeasonsFragment.OnTVShowSeasonsFragmentInteraction {
    public static final String TV_SHOW_DETAILS_INTENT = "TV_SHOW_DETAILS_INTENT";

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

        Intent intent = getIntent();

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

        if (intent.hasExtra(TV_SHOW_DETAILS_INTENT))
            viewPager.setCurrentItem(intent.getIntExtra(TV_SHOW_DETAILS_INTENT, -1));
    }

    @Override
    public void onDisplaySeasonEpisodes(int tvShowID, int seasonNum) {
        Intent intent = new Intent(this, TVShowEpisodesActivity.class);
        intent.putExtra(TVShowInfoFragment.TV_SHOW_EXTRA, tvShowID);
        intent.putExtra(TVShowSeasonsFragment.SEASON_EXTRA_INTENT, seasonNum);
        intent.putExtra(TV_SHOW_DETAILS_INTENT, 2);
        startActivity(intent);
    }
}
