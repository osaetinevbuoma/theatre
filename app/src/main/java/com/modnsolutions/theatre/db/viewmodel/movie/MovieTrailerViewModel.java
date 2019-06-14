package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieTrailerRepository;

import java.util.List;

public class MovieTrailerViewModel extends AndroidViewModel {
    private MovieTrailerRepository repository;

    public MovieTrailerViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieTrailerRepository(application);
    }

    public void insert(MovieTrailerEntity movieTrailerEntity) {
        repository.insert(movieTrailerEntity);
    }

    public void insertAll(MovieTrailerEntity... movieTrailerEntities) {
        repository.insertAll(movieTrailerEntities);
    }

    public LiveData<List<MovieTrailerEntity>> fetchAllMovieTrailers(int movieId) {
        return repository.fetchAllMovieTrailers(movieId);
    }
}
