package com.modnsolutions.theatre.db.viewmodel.tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;
import com.modnsolutions.theatre.db.repository.tvshow.TVShowReviewRepository;

import java.util.List;

public class TVShowReviewViewModel extends AndroidViewModel {
    private TVShowReviewRepository repository;

    public TVShowReviewViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowReviewRepository(application);
    }

    public void insert(TVShowReviewEntity tvShowReviewEntity) {
        repository.insert(tvShowReviewEntity);
    }

    public void insertAll(TVShowReviewEntity... tvShowReviewEntities) {
        repository.insertAll(tvShowReviewEntities);
    }

    public LiveData<List<TVShowReviewEntity>> fetchTVShowReviews(int tvShowId, int offset) {
        return repository.fetchTVShowReviews(tvShowId, offset);
    }
}
