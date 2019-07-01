package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.db.repository.SeasonRepository;

import java.util.List;

public class SeasonViewModel extends AndroidViewModel {
    private SeasonRepository repository;

    public SeasonViewModel(@NonNull Application application) {
        super(application);
        repository = new SeasonRepository(application);
    }

    public void insert(SeasonEntity... seasonEntities) {
        repository.insert(seasonEntities);
    }

    public LiveData<List<SeasonEntity>> findByTheatreId(int theatreId) {
        return repository.findByTheatreId(theatreId);
    }

    public SeasonEntity findByRemoteId(int remoteId) {
        return repository.findByRemoteId(remoteId);
    }
}
