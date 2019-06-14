package com.modnsolutions.theatre.db.dao.favorite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteTypeEntity;

import java.util.List;

@Dao
public interface FavoriteTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteTypeEntity favoriteTypeEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FavoriteTypeEntity... favoriteTypeEntities);

    @Update
    void update(FavoriteTypeEntity... favoriteTypeEntities);

    @Query("SELECT * FROM favorite_type")
    List<FavoriteTypeEntity> fetchAllTypes();
}
