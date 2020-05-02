package com.modnsolutions.theatre;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.modnsolutions.theatre.adapter.TheatrePagerAdapter;
import com.modnsolutions.theatre.databinding.ActivityFavoriteBinding;
import com.modnsolutions.theatre.utils.Utilities;

public class FavoriteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityFavoriteBinding activityFavoriteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityFavoriteBinding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(activityFavoriteBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = activityFavoriteBinding.drawerLayout;
        NavigationView navigationView = activityFavoriteBinding.navView;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = findViewById(R.id.theatre_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_movies)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_tv_shows)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = findViewById(R.id.theatre_pager);
        TheatrePagerAdapter theatrePagerAdapter = new TheatrePagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(), true);
        viewPager.setAdapter(theatrePagerAdapter);
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
        DrawerLayout drawer = activityFavoriteBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

        activityFavoriteBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }
}
