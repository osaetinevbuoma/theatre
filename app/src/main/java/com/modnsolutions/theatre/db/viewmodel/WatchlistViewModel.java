package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.entity.FavoriteEntity;
import com.modnsolutions.theatre.db.repository.TheatreRepository;

public class TheatreViewModel extends AndroidViewModel {
    private TheatreRepository repository;
    private TheatreDao dao;

    public TheatreViewModel(@NonNull Application application) {
        super(application);
        repository = new TheatreRepository(application);
        dao = TheatreDatabase.getInstance(application).theatreDao();
    }

    public void insert(FavoriteEntity... theatreEntities) {
        repository.insert(theatreEntities);
    }

    public void update(FavoriteEntity... theatreEntities) {
        repository.update(theatreEntities);
    }

    public void delete(FavoriteEntity favoriteEntity) {
        repository.delete(favoriteEntity);
    }

    public LiveData<FavoriteEntity> findOneById(int id) {
        return repository.findOneById(id);
    }

    public LiveData<PagedList<FavoriteEntity>> findByTheatreTypeAndTheatreSaveType(
            int theatreTypeId, int theatreSaveTypeId) {
        return new LivePagedListBuilder<>(dao.findByTheatreTypeAndTheatreSaveType(
                theatreTypeId, theatreSaveTypeId), 20).build();
    }

    public FavoriteEntity findOneByRemoteId(int remoteId) {
        return repository.findOneByRemoteId(remoteId);
    }
}
