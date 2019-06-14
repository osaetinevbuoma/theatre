package com.modnsolutions.theatre.db.viewmodel.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteTrailerEntity;
import com.modnsolutions.theatre.db.repository.favorite.FavoriteTrailerRepository;

import java.util.List;

public class FavoriteTrailerViewModel extends AndroidViewModel {
    private FavoriteTrailerRepository repository;

    public FavoriteTrailerViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteTrailerRepository(application);
    }

    public void insertAll(FavoriteTrailerEntity... favoriteTrailerEntities) {
        repository.insertAll(favoriteTrailerEntities);
    }

    public LiveData<List<FavoriteTrailerEntity>> fetchTrailers(int id) {
        return repository.fetchTrailers(id);
    }
}
