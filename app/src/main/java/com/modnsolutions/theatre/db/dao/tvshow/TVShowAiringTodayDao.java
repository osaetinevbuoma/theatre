package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowAiringTodayEntity;

import java.util.List;

@Dao
public interface TVShowAiringTodayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowAiringTodayEntity tvShowAiringTodayEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowAiringTodayEntity... tvShowAiringTodayEntities);

    @Update
    void update(TVShowAiringTodayEntity... tvShowAiringTodayEntities);

    @Query("DELETE FROM tv_show_airing_today")
    void deleteAll();

    @Query("SELECT * FROM tv_show_airing_today ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, TVShowAiringTodayEntity> fetchTVShows();

    @Query("SELECT * FROM tv_show_airing_today WHERE id = :id")
    TVShowAiringTodayEntity findTVShowById(int id);

    @Query("SELECT * FROM tv_show_airing_today")
    List<TVShowAiringTodayEntity> findAllTVShows();
}
