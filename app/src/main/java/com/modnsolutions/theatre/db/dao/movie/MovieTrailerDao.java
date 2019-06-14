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

    @Query("SELECT * FROM movie_trailer WHERE movie_id = :movieId")
    LiveData<List<MovieTrailerEntity>> fetchAllMovieTrailers(int movieId);
}
