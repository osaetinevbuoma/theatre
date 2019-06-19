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

    public LiveData<List<MovieReviewEntity>> fetchNowPlayingReviews(int movieId, int offset) {
        return repository.fetchNowPlayingReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchPopularReviews(int movieId, int offset) {
        return repository.fetchPopularReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchTopRatedReviews(int movieId, int offset) {
        return repository.fetchTopRatedReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchUpcomingReviews(int movieId, int offset) {
        return repository.fetchUpcomingReviews(movieId, offset);
    }

}
