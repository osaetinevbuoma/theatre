package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.modnsolutions.theatre.db.entity.TheatreTypeEntity;
import com.modnsolutions.theatre.db.repository.TheatreTypeRepository;

public class TheatreTypeViewModel extends AndroidViewModel {
    private TheatreTypeRepository repository;

    public TheatreTypeViewModel(@NonNull Application application) {
        super(application);
        repository = new TheatreTypeRepository(application);
    }

    public TheatreTypeEntity findOneById(int id) {
        return repository.findOneById(id);
    }
}
