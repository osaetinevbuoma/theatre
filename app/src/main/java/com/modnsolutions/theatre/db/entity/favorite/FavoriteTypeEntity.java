package com.modnsolutions.theatre.db.entity.favorite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_type")
public class FavoriteTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    public FavoriteTypeEntity(String type) {
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
