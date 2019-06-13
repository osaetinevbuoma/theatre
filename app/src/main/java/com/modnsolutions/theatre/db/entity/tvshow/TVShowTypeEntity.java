package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_show_type")
public class TVShowTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    public TVShowTypeEntity(int id, String type) {
        this.id = id;
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
