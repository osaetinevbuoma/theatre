package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;

@Dao
public interface TVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowEntity tvShowEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TVShowEntity... tvShowEntities);

    @Update
    void update(TVShowEntity... tvShowEntities);

    @Query("DELETE FROM tv_show WHERE date_downloaded < Date(:date)")
    void deleteAll(String date);
}
