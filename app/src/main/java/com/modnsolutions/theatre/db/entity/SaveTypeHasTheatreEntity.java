package com.modnsolutions.theatre.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "save_type_has_theatre",
        primaryKeys = { "save_type_id", "theatre_id" },
        foreignKeys = {
            @ForeignKey(entity = TheatreSaveTypeEntity.class, parentColumns = "id",
                    childColumns = "save_type_id", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = TheatreEntity.class, parentColumns = "id",
                    childColumns = "theatre_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index(name = "save_type_has_theatre_type_id_index", value = "save_type_id"),
            @Index(name = "save_type_has_theatre_id_index", value = "theatre_id")
        })
public class SaveTypeHasTheatre {
    @ColumnInfo(name = "save_type_id")
    private int saveTypeId;

    @ColumnInfo(name = "theatre_id")
    private int theatreId;

    public SaveTypeHasTheatre(int saveTypeId, int theatreId) {
        this.saveTypeId = saveTypeId;
        this.theatreId = theatreId;
    }

    public int getSaveTypeId() {
        return saveTypeId;
    }

    public void setSaveTypeId(int saveTypeId) {
        this.saveTypeId = saveTypeId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }
}
