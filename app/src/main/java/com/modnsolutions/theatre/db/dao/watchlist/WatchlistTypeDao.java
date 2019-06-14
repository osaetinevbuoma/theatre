package com.modnsolutions.theatre.db.dao.watchlist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTypeEntity;

@Dao
public interface WatchlistTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WatchlistTypeEntity watchlistTypeEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WatchlistTypeEntity... watchlistTypeEntities);

    @Update
    void update(WatchlistTypeEntity... watchlistTypeEntities);
}
