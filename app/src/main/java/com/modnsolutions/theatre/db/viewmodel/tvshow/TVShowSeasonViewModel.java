package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowSeasonRepository;

import java.util.List;

public class TVShowSeasonViewModel extends AndroidViewModel {
    private TVShowSeasonRepository repository;

    public TVShowSeasonViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowSeasonRepository(application);
    }

    public void insert(TVShowSeasonEntity tvShowSeasonEntity) {
        repository.insert(tvShowSeasonEntity);
    }

    public void insertAll(TVShowSeasonEntity... tvShowSeasonEntities) {
        repository.insertAll(tvShowSeasonEntities);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllPopularSeasons(int id) {
        return repository.fetchAllPopularSeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllTopRatedSeasons(int id) {
        return repository.fetchAllTopRatedSeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllAiringTodaySeasons(int id) {
        return repository.fetchAllAiringTodaySeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllOnTheAirSeasons(int id) {
        return repository.fetchAllOnTheAirSeasons(id);
    }

}
