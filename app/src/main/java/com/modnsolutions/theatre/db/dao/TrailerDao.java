package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.TrailerEntity;

import java.util.List;

@Dao
public interface TrailerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrailerEntity... trailerEntities);

    @Query("SELECT * FROM trailer WHERE theatre_id = :theatreId")
    LiveData<List<TrailerEntity>> findByTheatreId(int theatreId);
}
