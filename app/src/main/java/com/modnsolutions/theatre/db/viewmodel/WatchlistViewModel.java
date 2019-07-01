package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.WatchlistDao;
import com.modnsolutions.theatre.db.entity.WatchlistEntity;
import com.modnsolutions.theatre.db.repository.WatchlistRepository;

public class WatchlistViewModel extends AndroidViewModel {
    private WatchlistRepository repository;
    private WatchlistDao dao;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistRepository(application);
        dao = TheatreDatabase.getInstance(application).watchlistDao();
    }

    public void insert(WatchlistEntity... watchlistEntities) {
        repository.insert(watchlistEntities);
    }

    public void update(WatchlistEntity... watchlistEntities) {
        repository.update(watchlistEntities);
    }

    public void delete(WatchlistEntity favoriteEntity) {
        repository.delete(favoriteEntity);
    }

    public LiveData<WatchlistEntity> findOneById(int id) {
        return repository.findOneById(id);
    }

    public LiveData<PagedList<WatchlistEntity>> findWatchlistByTheatreType(int theatreTypeId) {
        return new LivePagedListBuilder<>(dao.findWatchlistByTheatreType(theatreTypeId),
                20).build();
    }

    public WatchlistEntity findOneByRemoteId(int remoteId) {
        return repository.findOneByRemoteId(remoteId);
    }
}
