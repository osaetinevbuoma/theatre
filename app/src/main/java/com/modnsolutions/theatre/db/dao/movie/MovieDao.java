package com.modnsolutions.theatre.db.dao.movie;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... movieEntities);

    @Update
    void update(MovieEntity... movieEntities);

    @Query("DELETE FROM movie WHERE date_downloaded < :date")
    void deleteAll(Date date);

    /**
     * Used for testing
     */
    @Query("SELECT * FROM movie WHERE id = :id")
    MovieEntity findMovieById(int id);
    @Query("SELECT * FROM movie")
    List<MovieEntity> findAllMovies();
}
