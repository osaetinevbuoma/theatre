package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowTrailerRepository;

import java.util.List;

public class TVShowTrailerViewModel extends AndroidViewModel {
    private TVShowTrailerRepository repository;

    public TVShowTrailerViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowTrailerRepository(application);
    }

    public void insert(TVShowTrailerEntity tvShowTrailerEntity) {
        repository.insert(tvShowTrailerEntity);
    }

    public void insertAll(TVShowTrailerEntity... tvShowTrailerEntities) {
        repository.insertAll(tvShowTrailerEntities);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllPopularTVShowTrailers(int tvShowId) {
        return repository.fetchAllPopularTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllTopRatedTVShowTrailers(int tvShowId) {
        return repository.fetchAllTopRatedTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllAiringTodayTVShowTrailers(int tvShowId) {
        return repository.fetchAllAiringTodayTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllOnTheAirTVShowTrailers(int tvShowId) {
        return repository.fetchAllOnTheAirTVShowTrailers(tvShowId);
    }
}
