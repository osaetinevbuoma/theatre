package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowAiringTodayDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowAiringTodayEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowAiringTodayRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowAiringTodayViewModel extends AndroidViewModel {
    private TVShowAiringTodayRepository repository;
    private LiveData<PagedList<TVShowAiringTodayEntity>> tvShowList;

    public TVShowAiringTodayViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowAiringTodayRepository(application);
        TVShowAiringTodayDao dao = TheatreDatabase.getInstance(application).tvShowAiringTodayDao();
        tvShowList = new LivePagedListBuilder<>(dao.fetchTVShows(), 20).build();
    }

    public void insert(TVShowAiringTodayEntity tvShowEntity) {
        repository.insert(tvShowEntity);
    }

    public void insertAll(TVShowAiringTodayEntity... tvShowEntities) {
        repository.insertAll(tvShowEntities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(TVShowAiringTodayEntity... tvShowEntities) {
        repository.update(tvShowEntities);
    }

    public LiveData<PagedList<TVShowAiringTodayEntity>> fetchTVShows() {
        return tvShowList;
    }

    public TVShowAiringTodayEntity findTVShowById(int id) throws ExecutionException, 
            InterruptedException {
        return repository.findTVShowById(id);
    }

    public List<TVShowAiringTodayEntity> findAllTVShows() throws ExecutionException, 
            InterruptedException {
        return repository.findAllTVShows();
    }
}
