package com.modnsolutions.theatre.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "theatre", foreignKeys = {
            @ForeignKey(entity = TheatreTypeEntity.class, parentColumns = "id",
                    childColumns = "theatre_type_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(name = "theatre_type_id_index", value = "theatre_type_id")
        })
public class TheatreEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "theatre_type_id")
    private Integer theatreTypeId;

    @ColumnInfo(name = "remote_id")
    private int remoteId;

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

    private double rating;
    private String director;
    private String cast;

    @ColumnInfo(name = "episode_runtime")
    private int episodeRuntime;

    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = "last_air_date")
    private String lastAirDate;

    @ColumnInfo(name = "number_of_episodes")
    private int numberOfEpisodes;

    @ColumnInfo(name = "number_of_seasons")
    private int numberOfSeasons;

    public TheatreEntity() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTheatreTypeId() {
        return theatreTypeId;
    }

    public void setTheatreTypeId(Integer theatreTypeId) {
        this.theatreTypeId = theatreTypeId;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    public int getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(int episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TheatreEntity that = (TheatreEntity) o;
        return id == that.id &&
                theatreTypeId == that.theatreTypeId &&
                remoteId == that.remoteId &&
                budget == that.budget &&
                revenue == that.revenue &&
                runtime == that.runtime &&
                rating == that.rating &&
                episodeRuntime == that.episodeRuntime &&
                numberOfEpisodes == that.numberOfEpisodes &&
                numberOfSeasons == that.numberOfSeasons &&
                Objects.equals(backdropPath, that.backdropPath) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(website, that.website) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(originalTitle, that.originalTitle) &&
                Objects.equals(director, that.director) &&
                Objects.equals(cast, that.cast) &&
                Objects.equals(firstAirDate, that.firstAirDate) &&
                Objects.equals(lastAirDate, that.lastAirDate);
    }
}
