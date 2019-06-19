package com.modnsolutions.theatre.db.entity.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "movie_trailer",
        foreignKeys = {
                @ForeignKey(entity = MovieNowPlayingEntity.class, parentColumns = "id",
                        childColumns = "now_playing_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = MoviePopularEntity.class, parentColumns = "id",
                        childColumns = "popular_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = MovieTopRatedEntity.class, parentColumns = "id",
                        childColumns = "top_rated_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = MovieUpcomingEntity.class, parentColumns = "id",
                        childColumns = "upcoming_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(name = "movie_trailer_now_playing_id_index", value = "now_playing_id"),
                @Index(name = "movie_trailer_popular_id_index", value = "popular_id"),
                @Index(name = "movie_trailer_top_rated_id_index", value = "top_rated_id"),
                @Index(name = "movie_trailer_upcoming_id_index", value = "upcoming_id")
        })
public class MovieTrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "now_playing_id")
    private int nowPlayingId;

    @ColumnInfo(name = "popular_id")
    private int popularId;

    @ColumnInfo(name = "top_rated_id")
    private int topRatedId;

    @ColumnInfo(name = "upcoming_id")
    private int upcomingId;

    private String name;
    private String key;

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    public MovieTrailerEntity(@NonNull String id, String name, String key, Date dateDownloaded) {
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

    public int getNowPlayingId() {
        return nowPlayingId;
    }

    public void setNowPlayingId(int nowPlayingId) {
        this.nowPlayingId = nowPlayingId;
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

    public int getUpcomingId() {
        return upcomingId;
    }

    public void setUpcomingId(int upcomingId) {
        this.upcomingId = upcomingId;
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
