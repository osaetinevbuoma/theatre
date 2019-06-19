package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;

import java.util.List;

@Dao
public interface TVShowReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowReviewEntity tvShowReviewEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowReviewEntity... tvShowReviewEntities);

    @Query("SELECT * FROM tv_show_review WHERE popular_id = :tvShowId LIMIT :offset, 10")
    LiveData<List<TVShowReviewEntity>> fetchPopularTVShowReviews(int tvShowId, int offset);

    @Query("SELECT * FROM tv_show_review WHERE top_rated_id = :tvShowId LIMIT :offset, 10")
    LiveData<List<TVShowReviewEntity>> fetchTopRatedTVShowReviews(int tvShowId, int offset);

    @Query("SELECT * FROM tv_show_review WHERE airing_today_id = :tvShowId LIMIT :offset, 10")
    LiveData<List<TVShowReviewEntity>> fetchAiringTodayTVShowReviews(int tvShowId, int offset);

    @Query("SELECT * FROM tv_show_review WHERE on_the_air_id = :tvShowId LIMIT :offset, 10")
    LiveData<List<TVShowReviewEntity>> fetchOnTheAirTVShowReviews(int tvShowId, int offset);

    /**
     * For testing
     */
    @Query("SELECT * FROM tv_show_review WHERE popular_id = :tvShowId")
    List<TVShowReviewEntity> findAllTVShowReviews(int tvShowId);
}
