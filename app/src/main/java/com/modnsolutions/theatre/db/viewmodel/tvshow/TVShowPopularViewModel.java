package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowPopularDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowPopularEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowPopularRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowPopularViewModel extends AndroidViewModel {
    private TVShowPopularRepository repository;
    private LiveData<PagedList<TVShowPopularEntity>> tvShowList;

    public TVShowPopularViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowPopularRepository(application);
        TVShowPopularDao dao = TheatreDatabase.getInstance(application).tvShowPopularDao();
        tvShowList = new LivePagedListBuilder<>(dao.fetchTVShows(), 20).build();
    }

    public void insert(TVShowPopularEntity tvShowEntity) {
        repository.insert(tvShowEntity);
    }

    public void insertAll(TVShowPopularEntity... tvShowEntities) {
        repository.insertAll(tvShowEntities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(TVShowPopularEntity... tvShowEntities) {
        repository.update(tvShowEntities);
    }

    public LiveData<PagedList<TVShowPopularEntity>> fetchTVShows() {
        return tvShowList;
    }

    public TVShowPopularEntity findTVShowById(int id) throws ExecutionException,
            InterruptedException {
        return repository.findTVShowById(id);
    }

    public List<TVShowPopularEntity> findAllTVShows() throws ExecutionException,
            InterruptedException {
        return repository.findAllTVShows();
    }
}
