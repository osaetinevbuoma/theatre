package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show_review", foreignKeys = @ForeignKey(entity = TVShowEntity.class,
        parentColumns = "id", childColumns = "tvShowId", onDelete = ForeignKey.CASCADE))
public class TVShowReviewEntity {
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "tv_show_id")
    private int tvShowId;

    private String author;
    private String content;

    @ColumnInfo(name = "date_downloaded")
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public TVShowReviewEntity(String id, int tvShowId, String author, String content,
                              Date dateDownloaded) {
        this.id = id;
        this.tvShowId = tvShowId;
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

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
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