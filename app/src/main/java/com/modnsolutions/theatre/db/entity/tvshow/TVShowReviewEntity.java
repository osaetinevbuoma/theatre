package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tv_show_review",
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
                @Index(name = "tv_show_review_popular_id_index", value = "popular_id"),
                @Index(name = "tv_show_review_top_rated_id_index", value = "top_rated_id"),
                @Index(name = "tv_show_review_airing_today_id_index", value = "airing_today_id"),
                @Index(name = "tv_show_review_on_the_air_id_index", value = "on_the_air_id")
        })
public class TVShowReviewEntity {
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

    private String author;
    private String content;

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    public TVShowReviewEntity(@NonNull String id, String author, String content,
                              Date dateDownloaded) {
        this.id = id;
        this.author = author;
        this.content = content;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateDownloaded() {
        return dateDownloaded;
    }

    public void setDateDownloaded(Date dateDownloaded) {
        this.dateDownloaded = dateDownloaded;
    }
}
