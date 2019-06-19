package com.modnsolutions.theatre.db.dao.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;

import java.util.List;

@Dao
public interface MovieTrailerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieTrailerEntity movieTrailerEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieTrailerEntity... movieTrailerEntities);

    @Query("SELECT * FROM movie_trailer WHERE now_playing_id = :movieId")
    LiveData<List<MovieTrailerEntity>> fetchAllNowPlayingTrailers(int movieId);

    @Query("SELECT * FROM movie_trailer WHERE popular_id = :movieId")
    LiveData<List<MovieTrailerEntity>> fetchAllPopularTrailers(int movieId);

    @Query("SELECT * FROM movie_trailer WHERE top_rated_id = :movieId")
    LiveData<List<MovieTrailerEntity>> fetchAllTopRatedTrailers(int movieId);

    @Query("SELECT * FROM movie_trailer WHERE upcoming_id = :movieId")
    LiveData<List<MovieTrailerEntity>> fetchAllUpcomingTrailers(int movieId);


    /**
     * Used for testing
     */
    @Query("SELECT * FROM movie_trailer WHERE now_playing_id = :movieId")
    List<MovieTrailerEntity> fetchTestMovieTrailers(int movieId);
}
