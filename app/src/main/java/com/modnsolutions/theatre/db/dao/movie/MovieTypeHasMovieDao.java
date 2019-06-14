package com.modnsolutions.theatre.db.dao.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeHasMovieEntity;

import java.util.List;

@Dao
public interface MovieTypeHasMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieTypeHasMovieEntity movieTypeHasMovieEntity);

    @Query("SELECT * FROM movie INNER JOIN movie_type ON movie.type_id = " +
            "movie_type.id WHERE movie_type.id = :id LIMIT :offset, 22")
    LiveData<List<MovieEntity>> fetchMoviesOfType(int id, int offset);
}
