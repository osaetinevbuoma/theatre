package com.modnsolutions.theatre.db.viewmodel.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEntity;
import com.modnsolutions.theatre.db.repository.favorite.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
    }

    public void insert(FavoriteEntity favoriteEntity) {
        repository.insert(favoriteEntity);
    }

    public void delete(FavoriteEntity favoriteEntity) {
        repository.delete(favoriteEntity);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<FavoriteEntity>> fetchFavorites(int id, int offset) {
        return repository.fetchFavorites(id, offset);
    }
}
