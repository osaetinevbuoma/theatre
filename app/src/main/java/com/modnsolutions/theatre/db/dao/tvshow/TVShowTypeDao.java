package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeEntity;

@Dao
public interface TVShowTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowTypeEntity tvShowTypeEntity);

    @Delete
    void delete(TVShowTypeEntity tvShowTypeEntity);

    @Update
    void update(TVShowTypeEntity... tvShowTypeEntities);
}
