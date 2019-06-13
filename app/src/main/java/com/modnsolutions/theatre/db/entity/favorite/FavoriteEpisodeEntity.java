package com.modnsolutions.theatre.db.entity.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_episode", foreignKeys = @ForeignKey(entity = FavoriteEntity.class,
        parentColumns = "id", childColumns = "favoriteId", onDelete = ForeignKey.CASCADE))
public class FavoriteEpisodeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "favorite_id")
    private int favoriteId;

    @ColumnInfo(name = "remote_id")
    private int remoteId;

    @ColumnInfo(name = "air_date")
    private String airDate;

    @ColumnInfo(name = "episode_number")
    private int episodeNumber;

    private String name;
    private String overview;

    @ColumnInfo(name = "still_path")
    private String stillPath;

    private int rating;

    public FavoriteEpisodeEntity(int id, int favoriteId, int remoteId, String airDate,
                                 int episodeNumber, String name, String overview, String stillPath,
                                 int rating) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.remoteId = remoteId;
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.overview = overview;
        this.stillPath = stillPath;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
