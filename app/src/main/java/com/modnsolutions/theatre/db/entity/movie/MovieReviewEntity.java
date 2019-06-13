package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "movie_review",
        foreignKeys = @ForeignKey(entity = MovieEntity.class, parentColumns = "id",
                childColumns = "movieId", onDelete = ForeignKey.CASCADE))
public class MovieReviewEntity {
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    private String author;
    private String content;

    @ColumnInfo(name = "date_downloaded")
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public MovieReviewEntity(String id, int movieId, String author, String content,
                             Date dateDownloaded) {
        this.id = id;
        this.movieId = movieId;
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
