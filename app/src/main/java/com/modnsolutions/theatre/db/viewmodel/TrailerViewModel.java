package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.TrailerEntity;
import com.modnsolutions.theatre.db.repository.TrailerRepository;

import java.util.List;

public class TrailerViewModel extends AndroidViewModel {
    private TrailerRepository repository;

    public TrailerViewModel(@NonNull Application application) {
        super(application);
        repository = new TrailerRepository(application);
    }

    public void insert(TrailerEntity... trailerEntities) {
        repository.insert(trailerEntities);
    }

    public LiveData<List<TrailerEntity>> findByTheatreId(int theatreId) {
        return repository.findByTheatreId(theatreId);
    }
}
