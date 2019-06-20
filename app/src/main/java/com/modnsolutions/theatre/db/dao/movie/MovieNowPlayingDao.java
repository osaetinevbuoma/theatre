package com.modnsolutions.theatre.db.dao.movie;

import androidx.lifecycle.LiveData;
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
    LiveData<MovieNowPlayingEntity> findMovieById(int id);

    /**
     * Live Data jumbles up order of returned result. Therefore,
     * this interface method returns the right order so that the save order is the same
     * as the download order from remote server.
     */
    @Query("SELECT * FROM movie_now_playing WHERE id = :id")
    MovieNowPlayingEntity findMovieByIdWithoutLiveData(int id);

    @Query("SELECT * FROM movie_now_playing")
    List<MovieNowPlayingEntity> findAllMovies();
}
