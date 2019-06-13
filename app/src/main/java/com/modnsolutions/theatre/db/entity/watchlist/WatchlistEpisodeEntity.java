package com.modnsolutions.theatre.db.entity.watchlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEntity;

@Entity(tableName = "watchlist_episode", foreignKeys = @ForeignKey(entity = WatchlistEntity.class,
        parentColumns = "id", childColumns = "watchlistId", onDelete = ForeignKey.CASCADE))
public class WatchlistEpisodeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "watchlist_id")
    private int watchlistId;

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

    public WatchlistEpisodeEntity(int id, int watchlistId, int remoteId, String airDate,
                                  int episodeNumber, String name, String overview, String stillPath,
                                  int rating) {
        this.id = id;
        this.watchlistId = watchlistId;
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

    public int getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(int watchlistId) {
        this.watchlistId = watchlistId;
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
