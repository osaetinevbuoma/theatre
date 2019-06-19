package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowReviewDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;

import java.util.List;

public class TVShowReviewRepository {
    private TVShowReviewDao tvShowReviewDao;

    public TVShowReviewRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowReviewDao = database.tvShowReviewDao();
    }

    public void insert(TVShowReviewEntity tvShowReviewEntity) {
        new DBInsertOperationAsyncTask(tvShowReviewDao).execute(tvShowReviewEntity);
    }

    public void insertAll(TVShowReviewEntity... tvShowReviewEntities) {
        new DBInsertAllOperationAsyncTask(tvShowReviewDao).execute(tvShowReviewEntities);
    }

    public LiveData<List<TVShowReviewEntity>> fetchPopularTVShowReviews(int tvShowId, int offset) {
        return tvShowReviewDao.fetchPopularTVShowReviews(tvShowId, offset);
    }

    public LiveData<List<TVShowReviewEntity>> fetchTopRatedTVShowReviews(int tvShowId, int offset) {
        return tvShowReviewDao.fetchTopRatedTVShowReviews(tvShowId, offset);
    }

    public LiveData<List<TVShowReviewEntity>> fetchAiringTodayTVShowReviews(int tvShowId, int offset) {
        return tvShowReviewDao.fetchAiringTodayTVShowReviews(tvShowId, offset);
    }

    public LiveData<List<TVShowReviewEntity>> fetchOnTheAirTVShowReviews(int tvShowId, int offset) {
        return tvShowReviewDao.fetchOnTheAirTVShowReviews(tvShowId, offset);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowReviewEntity, Void, Void> {
        private TVShowReviewDao dao;

        public DBInsertOperationAsyncTask(TVShowReviewDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowReviewEntity... tvShowReviewEntities) {
            dao.insert(tvShowReviewEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowReviewEntity, Void, Void> {
        private TVShowReviewDao dao;

        public DBInsertAllOperationAsyncTask(TVShowReviewDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowReviewEntity... tvShowReviewEntities) {
            dao.insertAll(tvShowReviewEntities);
            return null;
        }
    }
}
