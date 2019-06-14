package com.modnsolutions.theatre.db.entity.tvshow;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "tv_show_type_has_tv_show",
        primaryKeys = { "tv_show_type_id", "tv_show_id" },
        foreignKeys = {
            @ForeignKey(entity = TVShowTypeEntity.class, parentColumns = "id",
                    childColumns = "tv_show_type_id", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = TVShowEntity.class, parentColumns = "id", childColumns = "tv_show_id",
                    onDelete = ForeignKey.CASCADE)
        }, indices = {
            @Index(name = "tv_show_type_has_tv_show_tv_show_type_id_index",
                    value = "tv_show_type_id"),
            @Index(name = "tv_show_type_has_tv_show_tv_show_id_index", value = "tv_show_id")
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
