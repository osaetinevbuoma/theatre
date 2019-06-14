package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieRepository;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void insert(MovieEntity movieEntity) {
        movieRepository.insert(movieEntity);
    }

    public void insertAll(MovieEntity... movieEntities) {
        movieRepository.insertAll(movieEntities);
    }

    public void update(MovieEntity... movieEntities) {
        movieRepository.update(movieEntities);
    }

    public void deleteAll(String date) {
        movieRepository.deleteAll(date);
    }
}
