package com.modnsolutions.theatre;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.MoviesPagerAdapter;
import com.modnsolutions.theatre.databinding.ActivityMainBinding;
import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.viewmodel.TheatreTypeViewModel;
import com.modnsolutions.theatre.utils.Utilities;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        DrawerLayout drawer = activityMainBinding.drawerLayout;
        NavigationView navigationView = activityMainBinding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = findViewById(R.id.movies_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_now_playing)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_popular)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_top_rated)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_upcoming)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = findViewById(R.id.movies_pager);
        MoviesPagerAdapter moviesPagerAdapter = new MoviesPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(moviesPagerAdapter);
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

        // Hack to force room to create database and initialize defaults on start up.
        ViewModelProviders.of(this).get(TheatreTypeViewModel.class).findOneById(1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = activityMainBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_movies:
                Utilities.startActivity(this, MainActivity.class);
                break;

            case R.id.nav_tv_show:
                Utilities.startActivity(this, TVShowsActivity.class);
                break;

            case R.id.nav_favorite:
                Utilities.startActivity(this, FavoriteActivity.class);
                break;

            case R.id.nav_watch_list:
                Utilities.startActivity(this, WatchlistActivity.class);
                break;
        }

        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }
}
