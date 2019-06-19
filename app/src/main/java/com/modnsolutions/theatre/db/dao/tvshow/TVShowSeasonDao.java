package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;

import java.util.List;

@Dao
public interface TVShowSeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowSeasonEntity tvShowSeasonEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowSeasonEntity... tvShowSeasonEntities);

    @Query("SELECT * FROM tv_show_season WHERE popular_id = :id")
    LiveData<List<TVShowSeasonEntity>> fetchAllPopularSeasons(int id);

    @Query("SELECT * FROM tv_show_season WHERE top_rated_id = :id")
    LiveData<List<TVShowSeasonEntity>> fetchAllTopRatedSeasons(int id);

    @Query("SELECT * FROM tv_show_season WHERE airing_today_id = :id")
    LiveData<List<TVShowSeasonEntity>> fetchAllAiringTodaySeasons(int id);

    @Query("SELECT * FROM tv_show_season WHERE on_the_air_id = :id")
    LiveData<List<TVShowSeasonEntity>> fetchAllOnTheAirSeasons(int id);

    /**
     * For testing
     */
    @Query("SELECT * FROM tv_show_season WHERE popular_id = :id")
    List<TVShowSeasonEntity> findAllTVShowSeasons(int id);
}
