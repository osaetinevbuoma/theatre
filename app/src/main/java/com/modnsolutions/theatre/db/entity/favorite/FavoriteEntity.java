package com.modnsolutions.theatre.db.entity.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite", foreignKeys = @ForeignKey(entity = FavoriteTypeEntity.class,
        parentColumns = "id", childColumns = "typeId", onDelete = ForeignKey.CASCADE))
public class FavoriteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type_id")
    private int typeId;

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

    private int rating;
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

    public FavoriteEntity(int id, int typeId, int remoteId) {
        this.id = id;
        this.typeId = typeId;
        this.remoteId = remoteId;
    }

    @Ignore
    public FavoriteEntity(int id, int typeId, int remoteId, String backdropPath, long budget,
                          String genre, String website, String overview, String posterPath,
                          String releaseDate, long revenue, int runtime, String title,
                          String originalTitle, int rating, String director, String cast) {
        this.id = id;
        this.typeId = typeId;
        this.remoteId = remoteId;
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
    }

    @Ignore
    public FavoriteEntity(int id, int typeId, int remoteId, String backdropPath, String genre,
                          String website, String overview, String posterPath, String title,
                          String originalTitle, int rating, int episodeRuntime, String firstAirDate,
                          String lastAirDate, int numberOfEpisodes, int numberOfSeasons) {
        this.id = id;
        this.typeId = typeId;
        this.remoteId = remoteId;
        this.backdropPath = backdropPath;
        this.genre = genre;
        this.website = website;
        this.overview = overview;
        this.posterPath = posterPath;
        this.title = title;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.episodeRuntime = episodeRuntime;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
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
}
