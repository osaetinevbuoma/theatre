package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.repository.TheatreRepository;

public class FavoriteViewModel extends AndroidViewModel {
    private TheatreRepository repository;
    private TheatreDao dao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new TheatreRepository(application);
        dao = TheatreDatabase.getInstance(application).favoriteDao();
    }

    public void insert(TheatreEntity... favoriteEntities) {
        repository.insert(favoriteEntities);
    }

    public void update(TheatreEntity... favoriteEntities) {
        repository.update(favoriteEntities);
    }

    public void delete(TheatreEntity theatreEntity) {
        repository.delete(theatreEntity);
    }

    public LiveData<TheatreEntity> findOneById(int id) {
        return repository.findOneById(id);
    }

    public LiveData<PagedList<TheatreEntity>> findFavoriteByTheatreType(int theatreTypeId) {
        return new LivePagedListBuilder<>(dao.findFavoriteByTheatreType(theatreTypeId),
                20).build();
    }

    public TheatreEntity findOneByRemoteId(int remoteId) {
        return repository.findOneByRemoteId(remoteId);
    }
}
