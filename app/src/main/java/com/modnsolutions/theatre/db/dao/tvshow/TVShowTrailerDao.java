package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;

import java.util.List;

@Dao
public interface TVShowTrailerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowTrailerEntity trailerEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowTrailerEntity... trailerEntities);

    @Query("SELECT * FROM tv_show_trailer WHERE popular_id = :tvShowId")
    LiveData<List<TVShowTrailerEntity>> fetchAllPopularTVShowTrailers(int tvShowId);

    @Query("SELECT * FROM tv_show_trailer WHERE top_rated_id = :tvShowId")
    LiveData<List<TVShowTrailerEntity>> fetchAllTopRatedTVShowTrailers(int tvShowId);

    @Query("SELECT * FROM tv_show_trailer WHERE airing_today_id = :tvShowId")
    LiveData<List<TVShowTrailerEntity>> fetchAllAiringTodayTVShowTrailers(int tvShowId);

    @Query("SELECT * FROM tv_show_trailer WHERE on_the_air_id = :tvShowId")
    LiveData<List<TVShowTrailerEntity>> fetchAllOnTheAirTVShowTrailers(int tvShowId);

    /**
     * For testing
     */
    @Query("SELECT * FROM tv_show_trailer WHERE popular_id = :tvShowId")
    List<TVShowTrailerEntity> findAllTVShowTrailers(int tvShowId);
}
