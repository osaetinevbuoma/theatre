package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.modnsolutions.theatre.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "tv_show_trailer", foreignKeys = @ForeignKey(entity = TVShowEntity.class,
        parentColumns = "id", childColumns = "tv_show_id", onDelete = ForeignKey.CASCADE),
        indices = @Index(name = "tv_show_trailer_tv_show_id_index", value = "tv_show_id"))
public class TVShowTrailerEntity {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "tv_show_id")
    private int tvShowId;

    private String name;
    private String key;

    @ColumnInfo(name = "date_downloaded")
    @TypeConverters({DateConverter.class})
    private Date dateDownloaded;

    public TVShowTrailerEntity(String id, int tvShowId, String name, String key,
                               Date dateDownloaded) {
        this.id = id;
        this.tvShowId = tvShowId;
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

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
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
