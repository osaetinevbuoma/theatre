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
        foreignKeys = @ForeignKey(entity = MovieEntity.class, parentColumns = "id",
                childColumns = "movie_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "movie_trailer_movie_id_index", value = "movie_id"))
public class MovieTrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    private String name;
    private String key;

    @ColumnInfo(name = "date_downloaded")
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public MovieTrailerEntity(String id, int movieId, String name, String key, Date dateDownloaded) {
        this.id = id;
        this.movieId = movieId;
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
