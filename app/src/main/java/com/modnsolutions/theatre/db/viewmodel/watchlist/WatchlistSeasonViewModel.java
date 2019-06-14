package com.modnsolutions.theatre.db.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistSeasonEntity;
import com.modnsolutions.theatre.db.repository.watchlist.WatchlistSeasonRepository;

import java.util.List;

public class WatchlistSeasonViewModel extends AndroidViewModel {
    private WatchlistSeasonRepository repository;

    public WatchlistSeasonViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistSeasonRepository(application);
    }

    public void insertAll(WatchlistSeasonEntity... watchlistSeasonEntities) {
        repository.insertAll(watchlistSeasonEntities);
    }

    public LiveData<List<WatchlistSeasonEntity>> fetchWatchlistSeasons(int id) {
        return repository.fetchWatchlistSeasons(id);
    }
}
