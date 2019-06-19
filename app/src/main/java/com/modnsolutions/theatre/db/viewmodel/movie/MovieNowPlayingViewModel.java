package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieNowPlayingDao;
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieNowPlayingRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieNowPlayingViewModel extends AndroidViewModel {
    private MovieNowPlayingRepository movieNowPlayingRepository;
    private LiveData<PagedList<MovieNowPlayingEntity>> movieList;

    public MovieNowPlayingViewModel(@NonNull Application application) {
        super(application);
        movieNowPlayingRepository = new MovieNowPlayingRepository(application);
        MovieNowPlayingDao dao = TheatreDatabase.getInstance(application).movieNowPlayingDao();
        movieList = new LivePagedListBuilder<>(dao.fetchMovies(), 20).build();
    }

    public void insert(MovieNowPlayingEntity movieNowPlayingEntity) {
        movieNowPlayingRepository.insert(movieNowPlayingEntity);
    }

    public void insertAll(MovieNowPlayingEntity... movieNowPlayingEntities) {
        movieNowPlayingRepository.insertAll(movieNowPlayingEntities);
    }

    public void update(MovieNowPlayingEntity... movieNowPlayingEntities) {
        movieNowPlayingRepository.update(movieNowPlayingEntities);
    }

    public void deleteAll() {
        movieNowPlayingRepository.deleteAll();
    }

    public LiveData<PagedList<MovieNowPlayingEntity>> fetchMovies() {
        return movieList;
    }

    public MovieNowPlayingEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return movieNowPlayingRepository.findMovieById(id);
    }

    public List<MovieNowPlayingEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return movieNowPlayingRepository.findAllMovies();
    }
}
