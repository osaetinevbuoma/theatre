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

public class TheatreViewModel extends AndroidViewModel {
    private TheatreRepository repository;
    private TheatreDao dao;

    public TheatreViewModel(@NonNull Application application) {
        super(application);
        repository = new TheatreRepository(application);
        dao = TheatreDatabase.getInstance(application).theatreDao();
    }

    public void insert(TheatreEntity... theatreEntities) {
        repository.insert(theatreEntities);
    }

    public void update(TheatreEntity... theatreEntities) {
        repository.update(theatreEntities);
    }

    public void delete(TheatreEntity theatreEntity) {
        repository.delete(theatreEntity);
    }

    public LiveData<TheatreEntity> findOneById(int id) {
        return repository.findOneById(id);
    }

    public LiveData<PagedList<TheatreEntity>> findByTheatreTypeAndTheatreSaveType(
            int theatreTypeId, int theatreSaveTypeId) {
        return new LivePagedListBuilder<>(dao.findByTheatreTypeAndTheatreSaveType(
                theatreTypeId, theatreSaveTypeId), 20).build();
    }
}
