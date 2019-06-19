package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTrailerDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;

import java.util.List;

public class TVShowTrailerRepository {
    private TVShowTrailerDao tvShowTrailerDao;

    public TVShowTrailerRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowTrailerDao = database.tvShowTrailerDao();
    }

    public void insert(TVShowTrailerEntity tvShowTrailerEntity) {
        new DBInsertOperationAsyncTask(tvShowTrailerDao).execute(tvShowTrailerEntity);
    }

    public void insertAll(TVShowTrailerEntity... tvShowTrailerEntities) {
        new DBInsertAllOperationAsyncTask(tvShowTrailerDao).execute(tvShowTrailerEntities);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllPopularTVShowTrailers(int tvShowId) {
        return tvShowTrailerDao.fetchAllPopularTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllTopRatedTVShowTrailers(int tvShowId) {
        return tvShowTrailerDao.fetchAllTopRatedTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllAiringTodayTVShowTrailers(int tvShowId) {
        return tvShowTrailerDao.fetchAllAiringTodayTVShowTrailers(tvShowId);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllOnTheAirTVShowTrailers(int tvShowId) {
        return tvShowTrailerDao.fetchAllOnTheAirTVShowTrailers(tvShowId);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowTrailerEntity, Void, Void> {
        private TVShowTrailerDao dao;

        public DBInsertOperationAsyncTask(TVShowTrailerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowTrailerEntity... tvShowTrailerEntities) {
            dao.insert(tvShowTrailerEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowTrailerEntity, Void, Void> {
        private TVShowTrailerDao dao;

        public DBInsertAllOperationAsyncTask(TVShowTrailerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowTrailerEntity... tvShowTrailerEntities) {
            dao.insertAll(tvShowTrailerEntities);
            return null;
        }
    }
}
