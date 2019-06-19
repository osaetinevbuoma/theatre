package com.modnsolutions.theatre.db.dao.movie;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MovieUpcomingEntity;

import java.util.List;

@Dao
public interface MovieUpcomingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieUpcomingEntity movieUpcomingEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieUpcomingEntity... movieUpcomingEntities);

    @Update
    void update(MovieUpcomingEntity... movieUpcomingEntities);

    @Query("DELETE FROM movie_upcoming")
    void deleteAll();

    @Query("SELECT * FROM movie_upcoming ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, MovieUpcomingEntity> fetchMovies();

    @Query("SELECT * FROM movie_upcoming WHERE id = :id")
    MovieUpcomingEntity findMovieById(int id);

    @Query("SELECT * FROM movie_upcoming")
    List<MovieUpcomingEntity> findAllMovies();
}
