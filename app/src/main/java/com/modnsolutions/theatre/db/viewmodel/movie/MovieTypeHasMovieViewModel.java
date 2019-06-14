package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeHasMovieEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieTypeHasMovieRepository;

import java.util.List;

public class MovieTypeHasMovieViewModel extends AndroidViewModel {
    private MovieTypeHasMovieRepository repository;

    public MovieTypeHasMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieTypeHasMovieRepository(application);
    }

    public void insert(MovieTypeHasMovieEntity movieTypeHasMovieEntity) {
        repository.insert(movieTypeHasMovieEntity);
    }

    public LiveData<List<MovieEntity>> fetchMovieOfType(int id, int offset) {
        return repository.fetchMovieOfType(id, offset);
    }
}
