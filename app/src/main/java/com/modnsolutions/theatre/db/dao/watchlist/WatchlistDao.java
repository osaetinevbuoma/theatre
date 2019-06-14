package com.modnsolutions.theatre.db.dao.watchlist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEntity;

import java.util.List;

@Dao
public interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WatchlistEntity watchlistEntity);

    @Update
    void update(WatchlistEntity... watchlistEntities);

    @Delete
    void delete(WatchlistEntity watchlistEntity);

    @Query("DELETE FROM watchlist")
    void deleteAll();

    @Query("SELECT * FROM watchlist WHERE type_id = :id LIMIT :offset, 10")
    LiveData<List<WatchlistEntity>> fetchWatchlist(int id, int offset);
}
