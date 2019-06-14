package com.modnsolutions.theatre.db.viewmodel.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteSeasonEntity;
import com.modnsolutions.theatre.db.repository.favorite.FavoriteSeasonRepository;

import java.util.List;

public class FavoriteSeasonViewModel extends AndroidViewModel {
    private FavoriteSeasonRepository repository;

    public FavoriteSeasonViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteSeasonRepository(application);
    }

    public void insertAll(FavoriteSeasonEntity... favoriteSeasonEntities) {
        repository.insertAll(favoriteSeasonEntities);
    }

    public LiveData<List<FavoriteSeasonEntity>> fetchFavoriteSeasons(int id) {
        return repository.fetchFavoriteSeasons(id);
    }
}
