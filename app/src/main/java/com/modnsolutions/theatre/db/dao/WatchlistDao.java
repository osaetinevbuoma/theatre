package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.WatchlistEntity;

@Dao
public interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WatchlistEntity... watchlistEntities);

    @Update
    void update(WatchlistEntity... watchlistEntities);

    @Delete
    void delete(WatchlistEntity watchlistEntity);

    @Query("SELECT * FROM watchlist WHERE theatre_type_id = :theatreTypeId")
    DataSource.Factory<Integer, WatchlistEntity> findWatchlistByTheatreType(int theatreTypeId);

    @Query("SELECT * FROM watchlist WHERE id = :id")
    LiveData<WatchlistEntity> findOneById(int id);

    @Query("SELECT * FROM watchlist WHERE remote_id = :remoteId")
    WatchlistEntity findOneByRemoteId(int remoteId);
}
