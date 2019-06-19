package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MoviePopularDao;
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;
import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieNowPlayingRepository;
import com.modnsolutions.theatre.db.repository.movie.MoviePopularRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviePopularViewModel extends AndroidViewModel {
    private MoviePopularRepository moviePopularRepository;
    private LiveData<PagedList<MoviePopularEntity>> movieList;

    public MoviePopularViewModel(@NonNull Application application) {
        super(application);
        moviePopularRepository = new MoviePopularRepository(application);
        MoviePopularDao dao = TheatreDatabase.getInstance(application).moviePopularDao();
        movieList = new LivePagedListBuilder<>(dao.fetchMovies(), 20).build();
    }

    public void insert(MoviePopularEntity moviePopularEntity) {
        moviePopularRepository.insert(moviePopularEntity);
    }

    public void insertAll(MoviePopularEntity... moviePopularEntities) {
        moviePopularRepository.insertAll(moviePopularEntities);
    }

    public void update(MoviePopularEntity... moviePopularEntities) {
        moviePopularRepository.update(moviePopularEntities);
    }

    public void deleteAll() {
        moviePopularRepository.deleteAll();
    }

    public LiveData<PagedList<MoviePopularEntity>> fetchMovies() {
        return movieList;
    }

    public MoviePopularEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return moviePopularRepository.findMovieById(id);
    }

    public List<MoviePopularEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return moviePopularRepository.findAllMovies();
    }
}
