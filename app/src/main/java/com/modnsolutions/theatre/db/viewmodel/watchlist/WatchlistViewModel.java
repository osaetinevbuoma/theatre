package com.modnsolutions.theatre.db.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEntity;
import com.modnsolutions.theatre.db.repository.watchlist.WatchlistRepository;

import java.util.List;

public class WatchlistViewModel extends AndroidViewModel {
    private WatchlistRepository repository;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistRepository(application);
    }

    public void insert(WatchlistEntity watchlistEntity) {
        repository.insert(watchlistEntity);
    }

    public void delete(WatchlistEntity watchlistEntity) {
        repository.delete(watchlistEntity);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<WatchlistEntity>> fetchWatchlist(int id, int offset) {
        return repository.fetchWatchlist(id, offset);
    }
}
