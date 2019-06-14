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

    @Query("SELECT * FROM tv_show_trailer WHERE tv_show_id = :tvShowId")
    LiveData<List<TVShowTrailerEntity>> fetchAllTVShowTrailers(int tvShowId);
}
