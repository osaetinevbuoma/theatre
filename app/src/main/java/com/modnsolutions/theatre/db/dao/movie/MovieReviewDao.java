package com.modnsolutions.theatre.db.dao.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;

import java.util.List;

@Dao
public interface MovieReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieReviewEntity movieReviewEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieReviewEntity... movieReviewEntities);

    @Query("SELECT * FROM movie_review WHERE now_playing_id = :movieId LIMIT :offset, 10")
    LiveData<List<MovieReviewEntity>> fetchNowPlayingReviews(int movieId, int offset);

    @Query("SELECT * FROM movie_review WHERE popular_id = :movieId LIMIT :offset, 10")
    LiveData<List<MovieReviewEntity>> fetchPopularReviews(int movieId, int offset);

    @Query("SELECT * FROM movie_review WHERE top_rated_id = :movieId LIMIT :offset, 10")
    LiveData<List<MovieReviewEntity>> fetchTopRatedReviews(int movieId, int offset);

    @Query("SELECT * FROM movie_review WHERE upcoming_id = :movieId LIMIT :offset, 10")
    LiveData<List<MovieReviewEntity>> fetchUpcomingReviews(int movieId, int offset);


    /**
     * Used for testing
     */
    @Query("SELECT * FROM movie_review WHERE now_playing_id = :movieId")
    List<MovieReviewEntity> fetchTestMovieReviews(int movieId);
}
