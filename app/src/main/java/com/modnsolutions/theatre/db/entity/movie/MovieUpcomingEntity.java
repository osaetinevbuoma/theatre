package com.modnsolutions.theatre.db.entity.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "movie_upcoming")
public class MovieUpcomingEntity {
    @PrimaryKey
    private int id;

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

    @ColumnInfo(name = "expiry_date")
    private Date expiryDate;

    public MovieUpcomingEntity(int id, String backdropPath, String overview, String posterPath,
                               String releaseDate, String title, String originalTitle, int rating,
                               Date dateDownloaded, Date expiryDate) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.dateDownloaded = dateDownloaded;
        this.expiryDate = expiryDate;
    }

    @Ignore
    public MovieUpcomingEntity(int id, String backdropPath, long budget, String genre,
                               String website, String overview, String posterPath, String releaseDate,
                               long revenue, int runtime, String title, String originalTitle, int rating,
                               String director, String cast, Date dateDownloaded, Date expiryDate) {
        this.id = id;
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
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieUpcomingEntity that = (MovieUpcomingEntity) o;
        return id == that.id &&
                /*budget == that.budget &&
                revenue == that.revenue &&
                runtime == that.runtime &&*/
                rating == that.rating &&
                Objects.equals(backdropPath, that.backdropPath) &&
                /*Objects.equals(genre, that.genre) &&
                Objects.equals(website, that.website) &&*/
                Objects.equals(overview, that.overview) &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(originalTitle, that.originalTitle) &&
                /*Objects.equals(director, that.director) &&
                Objects.equals(cast, that.cast) &&*/
                Objects.equals(dateDownloaded, that.dateDownloaded) &&
                Objects.equals(expiryDate, that.expiryDate);
    }
}
