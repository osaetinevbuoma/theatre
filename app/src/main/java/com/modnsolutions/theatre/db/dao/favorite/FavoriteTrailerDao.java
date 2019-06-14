package com.modnsolutions.theatre.db.dao.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteTrailerEntity;

import java.util.List;

@Dao
public interface FavoriteTrailerDao {
    @Insert
    void insertAll(FavoriteTrailerEntity... favoriteTrailerEntities);

    @Query("SELECT * FROM favorite_trailer WHERE id = :id")
    LiveData<List<FavoriteTrailerEntity>> fetchTrailers(int id);
}
