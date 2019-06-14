package com.modnsolutions.theatre.db.viewmodel.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;
import com.modnsolutions.theatre.db.repository.movie.MovieReviewRepository;

import java.util.List;

public class MovieReviewViewModel extends AndroidViewModel {
    private MovieReviewRepository repository;

    public MovieReviewViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieReviewRepository(application);
    }

    public void insert(MovieReviewEntity movieReviewEntity) {
        repository.insert(movieReviewEntity);
    }

    public void insertAll(MovieReviewEntity... movieReviewEntities) {
        repository.insertAll(movieReviewEntities);
    }

    public LiveData<List<MovieReviewEntity>> fetchMovieReviews(int movieId, int offset) {
        return repository.fetchMovieReviews(movieId, offset);
    }
}
