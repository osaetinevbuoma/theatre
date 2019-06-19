package com.modnsolutions.theatre.db.dao.movie;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;

import java.util.List;

@Dao
public interface MoviePopularDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MoviePopularEntity moviePopularEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MoviePopularEntity... moviePopularEntities);

    @Update
    void update(MoviePopularEntity... moviePopularEntities);

    @Query("DELETE FROM movie_popular")
    void deleteAll();

    @Query("SELECT * FROM movie_popular ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, MoviePopularEntity> fetchMovies();

    @Query("SELECT * FROM movie_popular WHERE id = :id")
    MoviePopularEntity findMovieById(int id);

    @Query("SELECT * FROM movie_popular")
    List<MoviePopularEntity> findAllMovies();
}
