package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;

import java.util.Date;

public class TVShowRepository {
    private TVShowDao tvShowDao;

    public TVShowRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowDao();
    }

    public void insert(TVShowEntity tvShowEntity) {
        new DBInsertOperationAsyncTask(tvShowDao).execute(tvShowEntity);
    }

    public void insertAll(TVShowEntity... tvShowEntities) {
        new DBInsertAllOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public void deleteAll(Date date) {
        new DBDeleteAllOperationAsyncTask(tvShowDao).execute(date);
    }

    public void update(TVShowEntity... tvShowEntities) {
        new DBUpdateOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowEntity, Void, Void> {
        private TVShowDao dao;

        DBInsertOperationAsyncTask(TVShowDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowEntity... tvShowEntities) {
            dao.insert(tvShowEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowEntity, Void, Void> {
        private TVShowDao dao;

        DBInsertAllOperationAsyncTask(TVShowDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowEntity... tvShowEntities) {
            dao.insertAll(tvShowEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Date, Void, Void> {
        private TVShowDao dao;

        DBDeleteAllOperationAsyncTask(TVShowDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Date... dates) {
            dao.deleteAll(dates[0]);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<TVShowEntity, Void, Void> {
        private TVShowDao dao;

        DBUpdateOperationAsyncTask(TVShowDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowEntity... tvShowEntities) {
            dao.update(tvShowEntities);
            return null;
        }
    }
}
