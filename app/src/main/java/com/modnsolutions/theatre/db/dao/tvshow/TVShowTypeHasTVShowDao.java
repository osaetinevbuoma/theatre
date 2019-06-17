package com.modnsolutions.theatre.db.dao.tvshow;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeHasTVShowEntity;

import java.util.List;

@Dao
public interface TVShowTypeHasTVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TVShowTypeHasTVShowEntity tvShowTypeHasTVShowEntity);

    @Query("SELECT tv_show.* FROM tv_show INNER JOIN tv_show_type ON tv_show_type.id = " +
            "tv_show.type_id AND tv_show_type.id = :id LIMIT :offset, 22")
    LiveData<List<TVShowEntity>> fetchTVShowsOfType(int id, int offset);
}
