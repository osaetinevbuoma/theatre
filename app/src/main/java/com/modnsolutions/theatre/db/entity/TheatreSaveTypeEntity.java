package com.modnsolutions.theatre.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theatre_type")
public class TheatreTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    public TheatreTypeEntity(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
