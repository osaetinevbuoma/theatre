package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieTopRatedDao;
import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTopRatedEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieTopRatedRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieTopRatedViewModel extends AndroidViewModel {
    private MovieTopRatedRepository movieTopRatedRepository;
    private LiveData<PagedList<MovieTopRatedEntity>> movieList;

    public MovieTopRatedViewModel(@NonNull Application application) {
        super(application);
        movieTopRatedRepository = new MovieTopRatedRepository(application);
        MovieTopRatedDao dao = TheatreDatabase.getInstance(application).movieTopRatedDao();
        movieList = new LivePagedListBuilder<>(dao.fetchMovies(), 20).build();
    }

    public void insert(MovieTopRatedEntity movieTopRatedEntity) {
        movieTopRatedRepository.insert(movieTopRatedEntity);
    }

    public void insertAll(MovieTopRatedEntity... movieTopRatedEntities) {
        movieTopRatedRepository.insertAll(movieTopRatedEntities);
    }

    public void update(MovieTopRatedEntity... movieTopRatedEntities) {
        movieTopRatedRepository.update(movieTopRatedEntities);
    }

    public void deleteAll() {
        movieTopRatedRepository.deleteAll();
    }

    public LiveData<PagedList<MovieTopRatedEntity>> fetchMovies() {
        return movieList;
    }

    public MovieTopRatedEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return movieTopRatedRepository.findMovieById(id);
    }

    public List<MovieTopRatedEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return movieTopRatedRepository.findAllMovies();
    }
}
