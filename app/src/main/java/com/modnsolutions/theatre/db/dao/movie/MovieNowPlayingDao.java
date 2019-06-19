package com.modnsolutions.theatre.db.dao.movie;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;

import java.util.List;

@Dao
public interface MovieNowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieNowPlayingEntity movieNowPlayingEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieNowPlayingEntity... nowPlayingEntities);

    @Update
    void update(MovieNowPlayingEntity... nowPlayingEntities);

    @Query("DELETE FROM movie_now_playing")
    void deleteAll();

    @Query("SELECT * FROM movie_now_playing ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, MovieNowPlayingEntity> fetchMovies();

    @Query("SELECT * FROM movie_now_playing WHERE id = :id")
    MovieNowPlayingEntity findMovieById(int id);

    @Query("SELECT * FROM movie_now_playing")
    List<MovieNowPlayingEntity> findAllMovies();
}
