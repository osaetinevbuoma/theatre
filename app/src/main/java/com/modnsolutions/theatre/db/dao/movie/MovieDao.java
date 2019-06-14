package com.modnsolutions.theatre.db.dao.movie;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... movieEntities);

    @Query("DELETE FROM movie WHERE date_downloaded < Date(:date)")
    void deleteAll(String date);
}
