package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show", foreignKeys = @ForeignKey(entity = TVShowTypeEntity.class,
        parentColumns = "id", childColumns = "type_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "tv_show_type_id_index", value = "type_id"))
public class TVShowEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "type_id")
    private int typeId;

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
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public TVShowEntity(int id, int typeId, String backdropPath, String firstAirDate, String name,
                        String originalName, String overview, String posterPath, int rating,
                        Date dateDownloaded) {
        this.id = id;
        this.typeId = typeId;
        this.backdropPath = backdropPath;
        this.firstAirDate = firstAirDate;
        this.name = name;
        this.originalName = originalName;
        this.overview = overview;
        this.posterPath = posterPath;
        this.rating = rating;
        this.dateDownloaded = dateDownloaded;
    }

    @Ignore
    public TVShowEntity(int id, int typeId, String backdropPath, int episodeRuntime,
                        String firstAirDate, String lastAirDate, String genre, String website,
                        String name, String originalName, int numberOfEpisodes, int numberOfSeasons,
                        String overview, String posterPath, int rating, Date dateDownloaded) {
        this.id = id;
        this.typeId = typeId;
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
}
