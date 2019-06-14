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

    @Query("SELECT * FROM tv_show_season WHERE tv_show_id = :id")
    LiveData<List<TVShowSeasonEntity>> fetchAllSeaons(int id);
}
