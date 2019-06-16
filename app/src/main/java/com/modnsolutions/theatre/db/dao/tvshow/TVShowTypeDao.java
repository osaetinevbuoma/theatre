package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeEntity;

import java.util.List;

@Dao
public interface TVShowTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowTypeEntity tvShowTypeEntity);

    @Delete
    void delete(TVShowTypeEntity tvShowTypeEntity);

    @Update
    void update(TVShowTypeEntity... tvShowTypeEntities);

    @Query("SELECT * FROM tv_show_type")
    List<TVShowTypeEntity> fetchAllTVShowTypes();

    /**
     * Used for testing
     */
    @Query("SELECT * FROM tv_show_type WHERE id = :id")
    TVShowTypeEntity findTVShowTypeById(int id);
}
