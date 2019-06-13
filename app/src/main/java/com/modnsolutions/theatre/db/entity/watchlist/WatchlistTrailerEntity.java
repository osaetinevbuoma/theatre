package com.modnsolutions.theatre.db.entity.watchlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist_trailer", foreignKeys = @ForeignKey(entity = WatchlistEntity.class,
        parentColumns = "id", childColumns = "watchlistId", onDelete = ForeignKey.CASCADE))
public class WatchlistTrailerEntity {
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "watchlist_id")
    private int watchlistId;

    private String name;
    private String key;

    public WatchlistTrailerEntity(String id, int watchlistId, String name, String key) {
        this.id = id;
        this.watchlistId = watchlistId;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(int watchlistId) {
        this.watchlistId = watchlistId;
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
