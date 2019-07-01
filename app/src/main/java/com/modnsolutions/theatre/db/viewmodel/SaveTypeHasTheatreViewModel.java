package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.SaveTypeHasTheatreDao;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.repository.SaveTypeHasTheatreRepository;

public class SaveTypeHasTheatreViewModel extends AndroidViewModel {
    private SaveTypeHasTheatreRepository repository;
    private SaveTypeHasTheatreDao dao;

    public SaveTypeHasTheatreViewModel(@NonNull Application application) {
        super(application);
        repository = new SaveTypeHasTheatreRepository(application);
        dao = TheatreDatabase.getInstance(application).saveTypeHasTheatreDao();
    }

    public void insert(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities) {
        repository.insert(saveTypeHasTheatreEntities);
    }

    public void delete(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities) {
        repository.delete(saveTypeHasTheatreEntities);
    }

    public SaveTypeHasTheatreEntity[] findByTheatreId(int theatreId) {
        return repository.findByTheatreId(theatreId);
    }

    public SaveTypeHasTheatreEntity findByTheatreIdAndSaveTypeId(int theatreId, int saveTypeId) {
        return repository.findByTheatreIdAndSaveTypeId(theatreId, saveTypeId);
    }

    public LiveData<PagedList<TheatreEntity>> findByTheatreType(int typeId, int theatreTypeId) {
        return new LivePagedListBuilder<>(dao.findByTheatreType(typeId, theatreTypeId), 10)
                .build();
    }
}
