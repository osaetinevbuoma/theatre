package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.FavoriteEntity;

@Dao
public interface TheatreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity... theatreEntities);

    @Update
    void update(FavoriteEntity... theatreEntities);

    @Delete
    void delete(FavoriteEntity favoriteEntity);

    @Query("SELECT * FROM theatre WHERE theatre_type_id = :theatreTypeId AND " +
            "theatre_save_type_id = :theatreSaveTypeId")
    DataSource.Factory<Integer, FavoriteEntity> findByTheatreTypeAndTheatreSaveType(
            int theatreTypeId, int theatreSaveTypeId);

    @Query("SELECT * FROM theatre WHERE id = :id")
    LiveData<FavoriteEntity> findOneById(int id);

    @Query("SELECT * FROM theatre WHERE remote_id = :remoteId")
    FavoriteEntity findOneByRemoteId(int remoteId);
}
