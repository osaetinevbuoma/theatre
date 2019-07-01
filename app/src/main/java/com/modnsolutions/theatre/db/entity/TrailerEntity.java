package com.modnsolutions.theatre.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "trailer",
        foreignKeys = {
            @ForeignKey(entity = TheatreEntity.class, parentColumns = "id",
                    childColumns = "theatre_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index(name = "trailer_theatre_id_index", value = "theatre_id")
        })
public class TrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "theatre_id")
    private Integer theatreId;

    private String name;
    private String key;

    public TrailerEntity(@NonNull String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Integer getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Integer theatreId) {
        this.theatreId = theatreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
