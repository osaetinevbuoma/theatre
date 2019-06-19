package com.modnsolutions.theatre.db.dao.movie;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTopRatedEntity;

import java.util.List;

@Dao
public interface MovieTopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieTopRatedEntity movieTopRatedEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieTopRatedEntity... movieTopRatedEntities);

    @Update
    void update(MovieTopRatedEntity... movieTopRatedEntities);

    @Query("DELETE FROM movie_top_rated")
    void deleteAll();

    @Query("SELECT * FROM movie_top_rated ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, MovieTopRatedEntity> fetchMovies();

    @Query("SELECT * FROM movie_top_rated WHERE id = :id")
    MovieTopRatedEntity findMovieById(int id);

    @Query("SELECT * FROM movie_top_rated")
    List<MovieTopRatedEntity> findAllMovies();
}
