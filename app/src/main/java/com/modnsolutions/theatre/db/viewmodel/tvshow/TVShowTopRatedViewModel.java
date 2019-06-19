package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTopRatedDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTopRatedEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowTopRatedRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowTopRatedViewModel extends AndroidViewModel {
    private TVShowTopRatedRepository repository;
    private LiveData<PagedList<TVShowTopRatedEntity>> tvShowList;

    public TVShowTopRatedViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowTopRatedRepository(application);
        TVShowTopRatedDao dao = TheatreDatabase.getInstance(application).tvShowTopRatedDao();
        tvShowList = new LivePagedListBuilder<>(dao.fetchTVShows(), 20).build();
    }

    public void insert(TVShowTopRatedEntity tvShowEntity) {
        repository.insert(tvShowEntity);
    }

    public void insertAll(TVShowTopRatedEntity... tvShowEntities) {
        repository.insertAll(tvShowEntities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(TVShowTopRatedEntity... tvShowEntities) {
        repository.update(tvShowEntities);
    }

    public LiveData<PagedList<TVShowTopRatedEntity>> fetchTVShows() {
        return tvShowList;
    }

    public TVShowTopRatedEntity findTVShowById(int id) throws ExecutionException, 
            InterruptedException {
        return repository.findTVShowById(id);
    }

    public List<TVShowTopRatedEntity> findAllTVShows() throws ExecutionException, 
            InterruptedException {
        return repository.findAllTVShows();
    }
}
