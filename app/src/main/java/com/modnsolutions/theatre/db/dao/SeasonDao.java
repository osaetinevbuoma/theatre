package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.SeasonEntity;

import java.util.List;

@Dao
public interface SeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SeasonEntity... seasonEntities);

    @Query("SELECT * FROM season WHERE theatre_id = :theatreId")
    LiveData<List<SeasonEntity>> findByTheatreId(int theatreId);

    @Query("SELECT * FROM season WHERE remote_id = :remoteId")
    SeasonEntity findByRemoteId(int remoteId);
}
