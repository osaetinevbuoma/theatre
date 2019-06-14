package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeHasTVShowEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowTypeHasTVShowRepository;

import java.util.List;

public class TVShowTypeHasTVShowViewModel extends AndroidViewModel {
    private TVShowTypeHasTVShowRepository repository;

    public TVShowTypeHasTVShowViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowTypeHasTVShowRepository(application);
    }

    public void insert(TVShowTypeHasTVShowEntity tvShowTypeHasTVShowEntity) {
        repository.insert(tvShowTypeHasTVShowEntity);
    }

    public LiveData<List<TVShowEntity>> fetchTVShowsOfType(int id, int offset) {
        return repository.fetchTVShowsOfType(id, offset);
    }
}
