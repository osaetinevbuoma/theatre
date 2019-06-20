package com.modnsolutions.theatre.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "trailer", foreignKeys = @ForeignKey(entity = TheatreEntity.class,
        parentColumns = "id", childColumns = "theatre_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "theatre_id_index", value = "theatre_id"))
public class TrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "theatre_id")
    private int theatreId;

    private String name;
    private String key;

    public TrailerEntity(String id, int theatreId, String name, String key) {
        this.id = id;
        this.theatreId = theatreId;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
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
