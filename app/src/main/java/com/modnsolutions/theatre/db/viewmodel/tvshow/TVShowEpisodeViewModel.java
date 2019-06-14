package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowEpisodeRepository;

import java.util.List;

public class TVShowEpisodeViewModel extends AndroidViewModel {
    private TVShowEpisodeRepository repository;

    public TVShowEpisodeViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowEpisodeRepository(application);
    }

    public void insert(TVShowEpisodeEntity tvShowEpisodeEntity) {
        repository.insert(tvShowEpisodeEntity);
    }

    public void insertAll(TVShowEpisodeEntity... tvShowEpisodeEntities) {
        repository.insertAll(tvShowEpisodeEntities);
    }

    public LiveData<List<TVShowEpisodeEntity>> fetchAllSeasonEpisodes(int id) {
        return repository.fetchAllSeasonEpisodes(id);
    }
}
