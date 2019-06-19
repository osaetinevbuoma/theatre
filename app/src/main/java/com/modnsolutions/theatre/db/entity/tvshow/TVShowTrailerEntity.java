package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show_trailer",
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
                @Index(name = "tv_show_trailer_popular_id_index", value = "popular_id"),
                @Index(name = "tv_show_trailer_top_rated_id_index", value = "top_rated_id"),
                @Index(name = "tv_show_trailer_airing_today_id_index", value = "airing_today_id"),
                @Index(name = "tv_show_trailer_on_the_air_id_index", value = "on_the_air_id")
        })
public class TVShowTrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "popular_id")
    private int popularId;

    @ColumnInfo(name = "top_rated_id")
    private int topRatedId;

    @ColumnInfo(name = "airing_today_id")
    private int airingTodayId;

    @ColumnInfo(name = "on_the_air_id")
    private int onTheAirId;

    private String name;
    private String key;

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    public TVShowTrailerEntity(@NonNull String id, String name, String key,
                               Date dateDownloaded) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.dateDownloaded = dateDownloaded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getDateDownloaded() {
        return dateDownloaded;
    }

    public void setDateDownloaded(Date dateDownloaded) {
        this.dateDownloaded = dateDownloaded;
    }
}
