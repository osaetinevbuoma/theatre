package com.modnsolutions.theatre.db.dao.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEpisodeEntity;

import java.util.List;

@Dao
public interface FavoriteEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FavoriteEpisodeEntity favoriteEpisodeEntity);

    @Query("SELECT * FROM favorite_episode WHERE season_id = :id")
    LiveData<List<FavoriteEpisodeEntity>> fetchFavoriteSesaonEpisodes(int id);
}
