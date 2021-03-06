package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.TheatreEntity;

@Dao
public interface TheatreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TheatreEntity... theatreEntities);

    @Update
    void update(TheatreEntity... theatreEntities);

    @Delete
    void delete(TheatreEntity theatreEntity);

    @Query("SELECT * FROM theatre WHERE id = :id")
    TheatreEntity findOneById(int id);

    @Query("SELECT * FROM theatre WHERE remote_id = :remoteId")
    TheatreEntity findOneByRemoteId(int remoteId);
}
