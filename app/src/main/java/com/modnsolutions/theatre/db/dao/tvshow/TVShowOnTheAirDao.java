package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowOnTheAirEntity;

import java.util.List;

@Dao
public interface TVShowOnTheAirDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowOnTheAirEntity tvShowOnTheAirEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowOnTheAirEntity... tvShowOnTheAirEntities);

    @Update
    void update(TVShowOnTheAirEntity... tvShowOnTheAirEntities);

    @Query("DELETE FROM tv_show_on_the_air")
    void deleteAll();

    @Query("SELECT * FROM tv_show_on_the_air ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, TVShowOnTheAirEntity> fetchTVShows();

    @Query("SELECT * FROM tv_show_on_the_air WHERE id = :id")
    TVShowOnTheAirEntity findTVShowById(int id);

    @Query("SELECT * FROM tv_show_on_the_air")
    List<TVShowOnTheAirEntity> findAllTVShows();
}
