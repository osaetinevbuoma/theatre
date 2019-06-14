package com.modnsolutions.theatre.db.viewmodel.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.favorite.FavoriteEpisodeEntity;
import com.modnsolutions.theatre.db.repository.favorite.FavoriteEpisodeRepository;

import java.util.List;

public class FavoriteEpisodeViewModel extends AndroidViewModel {
    private FavoriteEpisodeRepository repository;

    public FavoriteEpisodeViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteEpisodeRepository(application);
    }

    public void insertAll(FavoriteEpisodeEntity... favoriteEpisodeEntities) {
        repository.insertAll(favoriteEpisodeEntities);
    }

    public LiveData<List<FavoriteEpisodeEntity>> fetchFavoriteSeasonEpisdoes(int id) {
        return repository.fetchFavoriteSeasonEpisodes(id);
    }
}
