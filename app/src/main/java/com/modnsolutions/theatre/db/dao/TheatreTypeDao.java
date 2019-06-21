package com.modnsolutions.theatre.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.TheatreTypeEntity;

import java.util.List;

@Dao
public interface TheatreTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TheatreTypeEntity... theatreTypeEntities);

    @Query("SELECT * FROM theatre_type")
    List<TheatreTypeEntity> findAll();

    @Query("SELECT * FROM theatre_type WHERE id = :id")
    TheatreTypeEntity findOneById(int id);

    @Query("SELECT * FROM theatre_type WHERE type = :type")
    TheatreTypeEntity findOneByType(String type);
}
