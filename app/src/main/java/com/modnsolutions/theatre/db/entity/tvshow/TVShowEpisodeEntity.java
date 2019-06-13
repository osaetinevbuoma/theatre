package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show_episode", foreignKeys = @ForeignKey(entity = TVShowSeasonEntity.class,
        parentColumns = "id", childColumns = "seasonId", onDelete = ForeignKey.CASCADE))
public class TVShowEpisodeEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "season_id")
    private int seasonId;

    @ColumnInfo(name = "air_date")
    private String airDate;

    @ColumnInfo(name = "episode_number")
    private int episodeNumber;

    private String name;
    private String overview;

    @ColumnInfo(name = "still_path")
    private String stillPath;

    private int rating;

    @ColumnInfo(name = "date_downloaded")
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public TVShowEpisodeEntity(int id, int seasonId, String airDate, int episodeNumber, String name,
                               String overview, String stillPath, int rating, Date dateDownloaded) {
        this.id = id;
        this.seasonId = seasonId;
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.overview = overview;
        this.stillPath = stillPath;
        this.rating = rating;
        this.dateDownloaded = dateDownloaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
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

    public Date getDateDownloaded() {
        return dateDownloaded;
    }

    public void setDateDownloaded(Date dateDownloaded) {
        this.dateDownloaded = dateDownloaded;
    }
}
