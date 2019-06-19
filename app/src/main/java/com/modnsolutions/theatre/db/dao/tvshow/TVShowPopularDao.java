package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowPopularEntity;

import java.util.List;

@Dao
public interface TVShowPopularDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowPopularEntity tvShowPopularEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowPopularEntity... tvShowPopularEntities);

    @Update
    void update(TVShowPopularEntity... tvShowPopularEntities);

    @Query("DELETE FROM tv_show_popular")
    void deleteAll();

    @Query("SELECT * FROM tv_show_popular ORDER BY date_downloaded ASC")
    DataSource.Factory<Integer, TVShowPopularEntity> fetchTVShows();

    @Query("SELECT * FROM tv_show_popular WHERE id = :id")
    TVShowPopularEntity findTVShowById(int id);

    @Query("SELECT * FROM tv_show_popular")
    List<TVShowPopularEntity> findAllTVShows();
}
