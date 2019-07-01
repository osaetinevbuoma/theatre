package com.modnsolutions.theatre.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.EpisodeEntity;

import java.util.List;

@Dao
public interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EpisodeEntity... episodeEntities);

    @Query("SELECT * FROM episode WHERE season_id = :seasonId")
    LiveData<List<EpisodeEntity>> findBySeasonId(int seasonId);

    @Query("SELECT * FROM episode WHERE season_id = :seasonId")
    EpisodeEntity[] findAllBySeasonId(int seasonId);
}
