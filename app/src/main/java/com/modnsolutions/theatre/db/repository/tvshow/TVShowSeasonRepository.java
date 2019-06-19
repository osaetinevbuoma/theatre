package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowSeasonDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;

import java.util.List;

public class TVShowSeasonRepository {
    private TVShowSeasonDao tvShowSeasonDao;

    public TVShowSeasonRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowSeasonDao = database.tvShowSeasonDao();
    }

    public void insert(TVShowSeasonEntity tvShowSeasonEntity) {
        new DBInsertOperationAsyncTask(tvShowSeasonDao).execute(tvShowSeasonEntity);
    }

    public void insertAll(TVShowSeasonEntity... tvShowSeasonEntities) {
        new DBInsertAllOperationAsyncTask(tvShowSeasonDao).execute(tvShowSeasonEntities);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllPopularSeasons(int id) {
        return tvShowSeasonDao.fetchAllPopularSeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllTopRatedSeasons(int id) {
        return tvShowSeasonDao.fetchAllTopRatedSeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllAiringTodaySeasons(int id) {
        return tvShowSeasonDao.fetchAllAiringTodaySeasons(id);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllOnTheAirSeasons(int id) {
        return tvShowSeasonDao.fetchAllOnTheAirSeasons(id);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowSeasonEntity, Void, Void> {
        private TVShowSeasonDao dao;

        public DBInsertOperationAsyncTask(TVShowSeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowSeasonEntity... tvShowSeasonEntities) {
            dao.insert(tvShowSeasonEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowSeasonEntity, Void, Void> {
        private TVShowSeasonDao dao;

        public DBInsertAllOperationAsyncTask(TVShowSeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowSeasonEntity... tvShowSeasonEntities) {
            dao.insertAll(tvShowSeasonEntities);
            return null;
        }
    }
}
