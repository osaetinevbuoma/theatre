package com.modnsolutions.theatre.db.dao.watchlist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistSeasonEntity;

import java.util.List;

@Dao
public interface WatchlistSeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WatchlistSeasonEntity... watchlistSeasonEntities);

    @Query("SELECT * FROM watchlist_season WHERE watchlist_id = :id")
    LiveData<List<WatchlistSeasonEntity>> fetchWatchlistSeasons(int id);
}
