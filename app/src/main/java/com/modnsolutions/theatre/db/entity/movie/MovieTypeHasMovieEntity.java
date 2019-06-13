package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "movie_type_has_movies", primaryKeys = { "movieTypeId", "movieId" },
        foreignKeys = {
            @ForeignKey(entity = MovieTypeEntity.class, parentColumns = "id",
                    childColumns = "movieTypeId", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = MovieEntity.class, parentColumns = "id", childColumns = "movieId",
                    onDelete = ForeignKey.CASCADE)
        })
public class MovieTypeHasMovieEntity {
    @ColumnInfo(name = "movie_type_id")
    private int movieTypeId;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    public MovieTypeHasMovieEntity(int movieTypeId, int movieId) {
        this.movieTypeId = movieTypeId;
        this.movieId = movieId;
    }

    public int getMovieTypeId() {
        return movieTypeId;
    }

    public void setMovieTypeId(int movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
