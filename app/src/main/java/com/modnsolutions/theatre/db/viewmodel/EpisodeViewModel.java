package com.modnsolutions.theatre.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.EpisodeEntity;
import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.db.repository.EpisodeRepository;
import com.modnsolutions.theatre.db.repository.SeasonRepository;

import java.util.List;

public class EpisodeViewModel extends AndroidViewModel {
    EpisodeRepository repository;

    public EpisodeViewModel(@NonNull Application application) {
        super(application);
        repository = new EpisodeRepository(application);
    }

    public void insert(EpisodeEntity... episodeEntities) {
        repository.insert(episodeEntities);
    }

    public LiveData<List<EpisodeEntity>> findBySeasonId(int seasonId) {
        return repository.findBySeasonId(seasonId);
    }

    public EpisodeEntity[] findAllBySeasonId(int seasonId) {
        return repository.findAllBySeasonId(seasonId);
    }
}
