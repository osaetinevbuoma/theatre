package com.modnsolutions.theatre.db.entity.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_season", foreignKeys = @ForeignKey(entity = FavoriteEntity.class,
        parentColumns = "id", childColumns = "favorite_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "favorite_season_favorite_id_index", value = "favorite_id"))
public class FavoriteSeasonEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "favorite_id")
    private int favoriteId;

    @ColumnInfo(name = "remote_id")
    private int remoteId;

    @ColumnInfo(name = "air_date")
    private String airDate;

    @ColumnInfo(name = "episode_count")
    private int episodeCount;

    private String name;
    private String overview;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "season_number")
    private int seasonNumber;

    public FavoriteSeasonEntity(int id, int favoriteId, int remoteId, String airDate, int episodeCount,
                                String name, String overview, String posterPath, int seasonNumber) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.remoteId = remoteId;
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
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

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
