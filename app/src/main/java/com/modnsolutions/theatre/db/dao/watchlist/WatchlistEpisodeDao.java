package com.modnsolutions.theatre.db.dao.watchlist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEpisodeEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEpisodeEntity;

import java.util.List;

@Dao
public interface WatchlistEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WatchlistEpisodeEntity watchlistEpisodeEntity);

    @Query("SELECT * FROM watchlist_episode WHERE season_id = :id")
    LiveData<List<WatchlistEpisodeEntity>> fetchWatchlistSesaonEpisodes(int id);
}
