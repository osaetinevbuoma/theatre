package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "movie_type_has_movies", primaryKeys = { "movie_type_id", "movie_id" },
        foreignKeys = {
            @ForeignKey(entity = MovieTypeEntity.class, parentColumns = "id",
                    childColumns = "movie_type_id", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = MovieEntity.class, parentColumns = "id", childColumns = "movie_id",
                    onDelete = ForeignKey.CASCADE)
        }, indices = {
            @Index(name = "movie_type_has_movies_movie_type_id_index", value = "movie_type_id"),
            @Index(name = "movie_type_has_movies_movie_id_index", value = "movie_id")
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
