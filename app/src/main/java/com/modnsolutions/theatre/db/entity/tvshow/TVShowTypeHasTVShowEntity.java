package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "tv_show_type_has_tv_show",
        primaryKeys = { "tvShowTypeId", "tvShowId" },
        foreignKeys = {
            @ForeignKey(entity = TVShowTypeEntity.class, parentColumns = "id",
                    childColumns = "tvShowTypeId", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = TVShowEntity.class, parentColumns = "id", childColumns = "tvShowId",
                    onDelete = ForeignKey.CASCADE)
        })
public class TVShowTypeHasTVShowEntity {
    @ColumnInfo(name = "tv_show_type_id")
    private int tvShowTypeId;

    @ColumnInfo(name = "tv_show_id")
    private int tvShowId;

    public TVShowTypeHasTVShowEntity(int tvShowTypeId, int tvShowId) {
        this.tvShowTypeId = tvShowTypeId;
        this.tvShowId = tvShowId;
    }

    public int getTvShowTypeId() {
        return tvShowTypeId;
    }

    public void setTvShowTypeId(int tvShowTypeId) {
        this.tvShowTypeId = tvShowTypeId;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }
}
