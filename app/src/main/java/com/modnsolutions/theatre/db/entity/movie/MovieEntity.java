package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "movie", foreignKeys = @ForeignKey(entity = MovieTypeEntity.class,
        parentColumns = "id", childColumns = "type_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "movie_type_id_index", value = "type_id"))
public class MovieEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "type_id")
    private int typeId;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    private long budget;
    private String genre;
    private String website;
    private String overview;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    private long revenue;
    private int runtime;
    private String title;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    private int rating;
    private String director;
    private String cast;

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    public MovieEntity(int id, int typeId, String backdropPath, String overview, String posterPath,
                       String releaseDate, String title, String originalTitle, int rating,
                       Date dateDownloaded) {
        this.id = id;
        this.typeId = typeId;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.dateDownloaded = dateDownloaded;
    }

    @Ignore
    public MovieEntity(int id, int typeId, String backdropPath, long budget, String genre,
                       String website, String overview, String posterPath, String releaseDate,
                       long revenue, int runtime, String title, String originalTitle, int rating,
                       String director, String cast, Date dateDownloaded) {
        this.id = id;
        this.typeId = typeId;
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.genre = genre;
        this.website = website;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.title = title;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.director = director;
        this.cast = cast;
        this.dateDownloaded = dateDownloaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Date getDateDownloaded() {
        return dateDownloaded;
    }

    public void setDateDownloaded(Date dateDownloaded) {
        this.dateDownloaded = dateDownloaded;
    }
}
