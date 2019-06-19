package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieUpcomingDao;
import com.modnsolutions.theatre.db.entity.movie.MovieUpcomingEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieUpcomingRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieUpcomingViewModel extends AndroidViewModel {
    private MovieUpcomingRepository movieUpcomingRepository;
    private LiveData<PagedList<MovieUpcomingEntity>> movieList;

    public MovieUpcomingViewModel(@NonNull Application application) {
        super(application);
        movieUpcomingRepository = new MovieUpcomingRepository(application);
        MovieUpcomingDao dao = TheatreDatabase.getInstance(application).movieUpcomingDao();
        movieList = new LivePagedListBuilder<>(dao.fetchMovies(), 20).build();
    }

    public void insert(MovieUpcomingEntity movieUpcomingEntity) {
        movieUpcomingRepository.insert(movieUpcomingEntity);
    }

    public void insertAll(MovieUpcomingEntity... movieUpcomingEntities) {
        movieUpcomingRepository.insertAll(movieUpcomingEntities);
    }

    public void update(MovieUpcomingEntity... movieUpcomingEntities) {
        movieUpcomingRepository.update(movieUpcomingEntities);
    }

    public void deleteAll() {
        movieUpcomingRepository.deleteAll();
    }

    public LiveData<PagedList<MovieUpcomingEntity>> fetchMovies() {
        return movieList;
    }

    public MovieUpcomingEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return movieUpcomingRepository.findMovieById(id);
    }

    public List<MovieUpcomingEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return movieUpcomingRepository.findAllMovies();
    }
}
