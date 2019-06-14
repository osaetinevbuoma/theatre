package com.modnsolutions.theatre.db.dao.watchlist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteTrailerEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTrailerEntity;

import java.util.List;

@Dao
public interface WatchlistTrailerDao {
    @Insert
    void insertAll(WatchlistTrailerEntity... watchlistTrailerEntities);

    @Query("SELECT * FROM watchlist_trailer WHERE id = :id")
    LiveData<List<WatchlistTrailerEntity>> fetchTrailers(int id);
}
