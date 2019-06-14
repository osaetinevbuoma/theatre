package com.modnsolutions.theatre.db.dao.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteSeasonEntity;

import java.util.List;

@Dao
public interface FavoriteSeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FavoriteSeasonEntity... favoriteSeasonEntities);

    @Query("SELECT * FROM favorite_season WHERE favorite_id = :id")
    LiveData<List<FavoriteSeasonEntity>> fetchFavoriteSeasons(int id);
}
