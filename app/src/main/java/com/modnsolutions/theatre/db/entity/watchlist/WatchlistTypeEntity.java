package com.modnsolutions.theatre.db.entity.watchlist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist_type")
public class WatchlistTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    public WatchlistTypeEntity(String type) {
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
