package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_type")
public class MovieTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    public MovieTypeEntity(String type) {
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
