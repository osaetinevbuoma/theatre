package com.modnsolutions.theatre.db.dao.movie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.modnsolutions.theatre.db.entity.movie.MovieTypeEntity;

@Dao
public interface MovieTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieTypeEntity movieTypeEntity);

    @Delete
    void delete(MovieTypeEntity movieTypeEntity);

    @Update
    void update(MovieTypeEntity... movieTypeEntities);
}
