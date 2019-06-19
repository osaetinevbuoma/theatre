package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowTopRatedEntity;

import java.util.List;

@Dao
public interface TVShowTopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowTopRatedEntity tvShowTopRatedEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowTopRatedEntity... tvShowTopRatedEntities);

    @Update
    void update(TVShowTopRatedEntity... tvShowTopRatedEntities);

    @Query("DELETE FROM tv_show_top_rated")
    void deleteAll();

    @Query("SELECT * FROM tv_show_top_rated ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, TVShowTopRatedEntity> fetchTVShows();

    @Query("SELECT * FROM tv_show_top_rated WHERE id = :id")
    TVShowTopRatedEntity findTVShowById(int id);

    @Query("SELECT * FROM tv_show_top_rated")
    List<TVShowTopRatedEntity> findAllTVShows();
}
