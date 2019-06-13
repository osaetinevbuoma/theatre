package com.modnsolutions.theatre.db.entity.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_trailer", foreignKeys = @ForeignKey(entity = FavoriteEntity.class,
        parentColumns = "id", childColumns = "favoriteId", onDelete = ForeignKey.CASCADE))
public class FavoriteTrailerEntity {
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "favorite_id")
    private int favoriteId;

    private String name;
    private String key;

    public FavoriteTrailerEntity(String id, int favoriteId, String name, String key) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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
