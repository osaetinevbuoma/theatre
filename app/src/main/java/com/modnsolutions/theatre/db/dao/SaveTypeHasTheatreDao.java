package com.modnsolutions.theatre.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;

@Dao
public interface SaveTypeHasTheatreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities);

    @Delete
    void delete(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities);

    @Query("SELECT theatre.* FROM theatre INNER JOIN save_type_has_theatre ON " +
            "theatre.id = save_type_has_theatre.theatre_id WHERE " +
            "save_type_has_theatre.save_type_id = :typeId AND theatre.theatre_type_id = :theatreTypeId")
    DataSource.Factory<Integer, TheatreEntity> findByTheatreType(int typeId, int theatreTypeId);

    @Query("SELECT * FROM save_type_has_theatre WHERE theatre_id = :theatreId")
    SaveTypeHasTheatreEntity[] findByTheatreId(int theatreId);

    @Query("SELECT * FROM save_type_has_theatre WHERE theatre_id = :theatreId AND save_type_id = :saveTypeId")
    SaveTypeHasTheatreEntity findByTheatreIdAndSaveTypeId(int theatreId, int saveTypeId);
}
