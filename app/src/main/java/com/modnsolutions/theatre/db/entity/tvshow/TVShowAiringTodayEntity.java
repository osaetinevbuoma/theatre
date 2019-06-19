package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "tv_show_airing_today")
public class TVShowAiringTodayEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "episode_runtime")
    private int episodeRuntime;

    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = "last_air_date")
    private String lastAirDate;

    private String genre;
    private String website;
    private String name;

    @ColumnInfo(name = "original_name")
    private String originalName;

    @ColumnInfo(name = "number_of_episodes")
    private int numberOfEpisodes;

    @ColumnInfo(name = "number_of_seasons")
    private int numberOfSeasons;

    private String overview;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    private int rating;

    @ColumnInfo(name = "date_downloaded")
    private Date dateDownloaded;

    @ColumnInfo(name = "expiry_date")
    private Date expiryDate;

    public TVShowAiringTodayEntity(int id, String backdropPath, String firstAirDate, String name,
                                   String originalName, String overview, String posterPath, int rating,
                                   Date dateDownloaded, Date expiryDate) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.firstAirDate = firstAirDate;
        this.name = name;
        this.originalName = originalName;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
        this.dateDownloaded = dateDownloaded;
        this.expiryDate = expiryDate;
    }

    @Ignore
    public TVShowAiringTodayEntity(int id, String backdropPath, int episodeRuntime,
                                   String firstAirDate, String lastAirDate, String genre, String website,
                                   String name, String originalName, int numberOfEpisodes, int numberOfSeasons,
                                   String overview, String posterPath, int rating, Date dateDownloaded,
                                   Date expiryDate) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.episodeRuntime = episodeRuntime;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.genre = genre;
        this.website = website;
        this.name = name;
        this.originalName = originalName;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
        TVShowAiringTodayEntity that = (TVShowAiringTodayEntity) o;
        return id == that.id &&
                /*episodeRuntime == that.episodeRuntime &&
                numberOfEpisodes == that.numberOfEpisodes &&
                numberOfSeasons == that.numberOfSeasons &&*/
                rating == that.rating &&
                Objects.equals(backdropPath, that.backdropPath) &&
                Objects.equals(firstAirDate, that.firstAirDate) &&
                /*Objects.equals(lastAirDate, that.lastAirDate) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(website, that.website) &&*/
                Objects.equals(name, that.name) &&
                Objects.equals(originalName, that.originalName) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(dateDownloaded, that.dateDownloaded) &&
                Objects.equals(expiryDate, that.expiryDate);
    }
}
