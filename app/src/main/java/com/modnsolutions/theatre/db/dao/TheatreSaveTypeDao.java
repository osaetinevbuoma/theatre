package com.modnsolutions.theatre.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;

import java.util.List;

@Dao
public interface TheatreSaveTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TheatreSaveTypeEntity... theatreSaveTypeEntities);

    @Query("SELECT * FROM theatre_save_type")
    List<TheatreSaveTypeEntity> findAll();

    @Query("SELECT * FROM theatre_save_type WHERE id = :id")
    TheatreSaveTypeEntity findOneById(int id);

    @Query("SELECT * FROM theatre_save_type WHERE type = :type")
    TheatreSaveTypeEntity findOneByType(String type);
}
