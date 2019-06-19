package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieReviewDao;
import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;

import java.util.List;

public class MovieReviewRepository {
    private MovieReviewDao movieReviewDao;

    public MovieReviewRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieReviewDao = database.movieReviewDao();
    }

    public void insert(MovieReviewEntity movieReviewEntity) {
        new InsertAsyncTask(movieReviewDao, 0).execute(movieReviewEntity);
    }

    public void insertAll(MovieReviewEntity... movieReviewEntities) {
        new InsertAsyncTask(movieReviewDao, 1).execute(movieReviewEntities);
    }

    public LiveData<List<MovieReviewEntity>> fetchNowPlayingReviews(int movieId, int offset) {
        return movieReviewDao.fetchNowPlayingReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchPopularReviews(int movieId, int offset) {
        return movieReviewDao.fetchPopularReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchTopRatedReviews(int movieId, int offset) {
        return movieReviewDao.fetchTopRatedReviews(movieId, offset);
    }

    public LiveData<List<MovieReviewEntity>> fetchUpcomingReviews(int movieId, int offset) {
        return movieReviewDao.fetchUpcomingReviews(movieId, offset);
    }



    private static class InsertAsyncTask extends AsyncTask<MovieReviewEntity, Void, Void> {
        private MovieReviewDao dao;
        private int insertTask;

        InsertAsyncTask(MovieReviewDao dao, int insertTask) {
            this.dao = dao;
            this.insertTask = insertTask;
        }

        @Override
        protected Void doInBackground(MovieReviewEntity... movieReviewEntities) {
            if (insertTask == 0) dao.insert(movieReviewEntities[0]);
            else if (insertTask == 1) dao.insertAll(movieReviewEntities);
            return null;
        }
    }
}
