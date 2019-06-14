package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowRepository;

import java.util.Date;

public class TVShowViewModel extends AndroidViewModel {
    private TVShowRepository repository;

    public TVShowViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowRepository(application);
    }

    public void insert(TVShowEntity tvShowEntity) {
        repository.insert(tvShowEntity);
    }

    public void insertAll(TVShowEntity... tvShowEntities) {
        repository.insertAll(tvShowEntities);
    }

    public void deleteAll(Date date) {
        repository.deleteAll(date);
    }
}
