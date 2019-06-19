package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowOnTheAirDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowOnTheAirEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowOnTheAirRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowOnTheAirViewModel extends AndroidViewModel {
    private TVShowOnTheAirRepository repository;
    private LiveData<PagedList<TVShowOnTheAirEntity>> tvShowList;

    public TVShowOnTheAirViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowOnTheAirRepository(application);
        TVShowOnTheAirDao dao = TheatreDatabase.getInstance(application).tvShowOnTheAirDao();
        tvShowList = new LivePagedListBuilder<>(dao.fetchTVShows(), 20).build();
    }

    public void insert(TVShowOnTheAirEntity tvShowEntity) {
        repository.insert(tvShowEntity);
    }

    public void insertAll(TVShowOnTheAirEntity... tvShowEntities) {
        repository.insertAll(tvShowEntities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(TVShowOnTheAirEntity... tvShowEntities) {
        repository.update(tvShowEntities);
    }

    public LiveData<PagedList<TVShowOnTheAirEntity>> fetchTVShows() {
        return tvShowList;
    }

    public TVShowOnTheAirEntity findTVShowById(int id) throws ExecutionException, 
            InterruptedException {
        return repository.findTVShowById(id);
    }

    public List<TVShowOnTheAirEntity> findAllTVShows() throws ExecutionException, 
            InterruptedException {
        return repository.findAllTVShows();
    }
}
