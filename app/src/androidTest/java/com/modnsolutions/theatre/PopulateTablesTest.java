package com.modnsolutions.theatre;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTypeDao;
import com.modnsolutions.theatre.db.dao.movie.MovieTypeDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTypeDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistTypeDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteTypeEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTypeEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PopulateTablesTest {
    private MovieTypeDao movieTypeDao;
    private TVShowTypeDao tvShowTypeDao;
    private FavoriteTypeDao favoriteTypeDao;
    private WatchlistTypeDao watchlistTypeDao;
    private TheatreDatabase database;

    private String[] movieTypes = { "Now Playing", "Popular", "Top Rated", "Upcoming" };
    private String[] tvShowTypes = { "Popular", "Top Rated", "Airing Today", "On The Air" };
    private String[] types = { "Movies", "TV Shows" };

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TheatreDatabase.class).build();
        movieTypeDao = database.movieTypeDao();
        tvShowTypeDao = database.tvShowTypeDao();
        favoriteTypeDao = database.favoriteTypeDao();
        watchlistTypeDao = database.watchlistTypeDao();
    }

    @After
    public void closeDB() {
        database.close();
    }

    @Test
    public void testPopulateMovieTypeTable() {
        for (String type : movieTypes) {
            movieTypeDao.insert(new MovieTypeEntity(type));
        }

        List<MovieTypeEntity> types = movieTypeDao.fetchAllMovieTypes();
        assertThat(types.size(), is(equalTo(movieTypes.length)));
        assertThat(types.get(0).getType(), is(equalTo(movieTypes[0])));
    }

    @Test
    public void testPopulateTVShowTypeTable() {
        for (String type : tvShowTypes) {
            tvShowTypeDao.insert(new TVShowTypeEntity(type));
        }

        List<TVShowTypeEntity> types = tvShowTypeDao.fetchAllTVShowTypes();
        assertThat(types.size(), is(equalTo(tvShowTypes.length)));
        assertThat(types.get(0).getType(), is((equalTo(tvShowTypes[0]))));
    }

    @Test
    public void testPopulateFavoriteTypeTable() {
        FavoriteTypeEntity[] favoriteTypeEntities = new FavoriteTypeEntity[types.length];

        for (int i = 0; i < types.length; i++) {
            favoriteTypeEntities[i] = new FavoriteTypeEntity(types[i]);
        }

        favoriteTypeDao.insertAll(favoriteTypeEntities);

        List<FavoriteTypeEntity> favoriteTypeEntityList = favoriteTypeDao.fetchAllTypes();
        assertThat(favoriteTypeEntityList.size(), is(equalTo(types.length)));
        assertThat(favoriteTypeEntityList.get(0).getType(), is(equalTo(types[0])));
    }

    @Test
    public void testPopulateWatchlistTypeTable() {
        WatchlistTypeEntity[] watchlistTypeEntities = new WatchlistTypeEntity[types.length];

        for (int i = 0; i <types.length; i++) {
            watchlistTypeEntities[i] = new WatchlistTypeEntity(types[i]);
        }

        watchlistTypeDao.insertAll(watchlistTypeEntities);

        List<WatchlistTypeEntity> watchlistTypeEntityList = watchlistTypeDao.fetchAllTypes();
        assertThat(watchlistTypeEntityList.size(), is(equalTo(types.length)));
        assertThat(watchlistTypeEntityList.get(1).getType(), is(equalTo(types[1])));
    }
}
