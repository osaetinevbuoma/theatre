package com.modnsolutions.theatre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.TVShowsPagerAdapter;
import com.modnsolutions.theatre.utils.Utilities;

public class TVShowsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = findViewById(R.id.tv_shows_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_airing_today)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_on_the_air)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_popular)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_top_rated)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = findViewById(R.id.tv_shows_pager);
        TVShowsPagerAdapter tvShowsPagerAdapter = new TVShowsPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tvShowsPagerAdapter);
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_movies:
                Utilities.startActivity(this, MainActivity.class);
                break;

            case R.id.nav_tv_show:
                Utilities.startActivity(this, TVShowsActivity.class);
                break;

            case R.id.nav_favorite:
                break;

            case R.id.nav_watch_list:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
