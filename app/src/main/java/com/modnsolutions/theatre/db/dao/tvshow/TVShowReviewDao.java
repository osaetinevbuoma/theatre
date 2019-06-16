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

    @Query("SELECT * FROM tv_show_review WHERE tv_show_id = :tvShowId LIMIT :offset, 10")
    LiveData<List<TVShowReviewEntity>> fetchTVShowReviews(int tvShowId, int offset);

    /**
     * For testing
     */
    @Query("SELECT * FROM tv_show_review WHERE tv_show_id = :tvShowId")
    List<TVShowReviewEntity> findAllTVShowReviews(int tvShowId);
}
