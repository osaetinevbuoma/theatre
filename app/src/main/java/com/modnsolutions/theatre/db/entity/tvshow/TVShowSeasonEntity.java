package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show_season",
        foreignKeys = {
                @ForeignKey(entity = TVShowPopularEntity.class, parentColumns = "id",
                        childColumns = "popular_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = TVShowTopRatedEntity.class, parentColumns = "id",
                        childColumns = "top_rated_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = TVShowAiringTodayEntity.class, parentColumns = "id",
                        childColumns = "airing_today_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = TVShowOnTheAirEntity.class, parentColumns = "id",
                        childColumns = "on_the_air_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(name = "tv_show_season_popular_id_index", value = "popular_id"),
                @Index(name = "tv_show_season_top_rated_id_index", value = "top_rated_id"),
                @Index(name = "tv_show_season_airing_today_id_index", value = "airing_today_id"),
                @Index(name = "tv_show_season_on_the_air_id_index", value = "on_the_air_id")
        })
public class TVShowSeasonEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "popular_id")
    private int popularId;

    @ColumnInfo(name = "top_rated_id")
    private int topRatedId;

    @ColumnInfo(name = "airing_today_id")
    private int airingTodayId;

    @ColumnInfo(name = "on_the_air_id")
    private int onTheAirId;

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

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    public TVShowSeasonEntity(int id, String airDate, int episodeCount, String name,
                              String overview, String posterPath, int seasonNumber,
                              Date dateDownloaded) {
        this.id = id;
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.dateDownloaded = dateDownloaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopularId() {
        return popularId;
    }

    public void setPopularId(int popularId) {
        this.popularId = popularId;
    }

    public int getTopRatedId() {
        return topRatedId;
    }

    public void setTopRatedId(int topRatedId) {
        this.topRatedId = topRatedId;
    }

    public int getAiringTodayId() {
        return airingTodayId;
    }

    public void setAiringTodayId(int airingTodayId) {
        this.airingTodayId = airingTodayId;
    }

    public int getOnTheAirId() {
        return onTheAirId;
    }

    public void setOnTheAirId(int onTheAirId) {
        this.onTheAirId = onTheAirId;
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

    public Date getDateDownloaded() {
        return dateDownloaded;
    }

    public void setDateDownloaded(Date dateDownloaded) {
        this.dateDownloaded = dateDownloaded;
    }
}
