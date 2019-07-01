package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.repository.TheatreSaveTypeRepository;

public class TheatreSaveTypeViewModel extends AndroidViewModel {
    private TheatreSaveTypeRepository repository;

    public TheatreSaveTypeViewModel(@NonNull Application application) {
        super(application);
        repository = new TheatreSaveTypeRepository(application);
    }

    public TheatreSaveTypeEntity findOneById(int id) {
        return repository.findOneById(id);
    }

    public TheatreSaveTypeEntity findOneByType(String type) {
        return repository.findOneByType(type);
    }
}
