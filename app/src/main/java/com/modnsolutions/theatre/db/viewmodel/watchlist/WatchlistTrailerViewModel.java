package com.modnsolutions.theatre.db.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTrailerEntity;
import com.modnsolutions.theatre.db.repository.watchlist.WatchlistTrailerRepository;

import java.util.List;

public class WatchlistTrailerViewModel extends AndroidViewModel {
    private WatchlistTrailerRepository repository;

    public WatchlistTrailerViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistTrailerRepository(application);
    }

    public void insertAll(WatchlistTrailerEntity... watchlistTrailerEntities) {
        repository.insertAll(watchlistTrailerEntities);
    }

    public LiveData<List<WatchlistTrailerEntity>> fetchTrailers(int id) {
        return repository.fetchTrailers(id);
    }
}
