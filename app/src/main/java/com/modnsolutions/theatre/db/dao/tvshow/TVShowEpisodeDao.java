package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;

import java.util.List;

@Dao
public interface TVShowEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowEpisodeEntity tvShowEpisodeEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowEpisodeEntity... tvShowEpisodeEntities);

    @Query("SELECT * FROM tv_show_episode WHERE season_id = :id")
    LiveData<List<TVShowEpisodeEntity>> fetchAllSeasonEpisodes(int id);
}
