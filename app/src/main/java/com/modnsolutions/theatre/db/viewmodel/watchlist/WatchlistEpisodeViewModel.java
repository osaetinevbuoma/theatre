package com.modnsolutions.theatre.db.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEpisodeEntity;
import com.modnsolutions.theatre.db.repository.watchlist.WatchlistEpisodeRepository;

import java.util.List;

public class WatchlistEpisodeViewModel extends AndroidViewModel {
    private WatchlistEpisodeRepository repository;

    public WatchlistEpisodeViewModel(@NonNull Application application) {
        super(application);
        repository = new WatchlistEpisodeRepository(application);
    }

    public void insertAll(WatchlistEpisodeEntity watchlistEpisodeEntity) {
        repository.insertAll(watchlistEpisodeEntity);
    }

    public LiveData<List<WatchlistEpisodeEntity>> fetchWatchlistSeasonEpisodes(int id) {
        return repository.fetchWatchlistSeasonEpisodes(id);
    }
}
