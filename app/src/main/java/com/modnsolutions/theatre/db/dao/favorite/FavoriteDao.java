package com.modnsolutions.theatre.db.dao.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEntity;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favoriteEntity);

    @Delete
    void delete(FavoriteEntity favoriteEntity);

    @Query("DELETE FROM favorite")
    void deleteAll();

    @Query("SELECT * FROM favorite WHERE type_id = :id LIMIT :offset, 10")
    LiveData<List<FavoriteEntity>> fetchFavorites(int id, int offset);
}
