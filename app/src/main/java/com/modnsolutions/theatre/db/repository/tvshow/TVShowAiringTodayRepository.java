package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowAiringTodayDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowAiringTodayEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowAiringTodayRepository {
    private TVShowAiringTodayDao tvShowDao;

    public TVShowAiringTodayRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowAiringTodayDao();
    }

    public void insert(TVShowAiringTodayEntity tvShowEntity) {
        new DBInsertOperationAsyncTask(tvShowDao).execute(tvShowEntity);
    }

    public void insertAll(TVShowAiringTodayEntity... tvShowEntities) {
        new DBInsertAllOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(tvShowDao).execute();
    }

    public void update(TVShowAiringTodayEntity... tvShowEntities) {
        new DBUpdateOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public TVShowAiringTodayEntity findTVShowById(int id) throws ExecutionException,
            InterruptedException {
        return new DBFindTVShowByIdOperationAsyncTask(tvShowDao).execute(id).get();
    }

    public List<TVShowAiringTodayEntity> findAllTVShows() throws ExecutionException,
            InterruptedException {
        return new DBFindAllTVShowsOperationAsyncTask(tvShowDao).execute().get();
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowAiringTodayEntity, Void,
            Void> {
        private TVShowAiringTodayDao dao;

        DBInsertOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowAiringTodayEntity... tvShowEntities) {
            dao.insert(tvShowEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowAiringTodayEntity, Void,
            Void> {
        private TVShowAiringTodayDao dao;

        DBInsertAllOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowAiringTodayEntity... tvShowEntities) {
            dao.insertAll(tvShowEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVShowAiringTodayDao dao;

        DBDeleteAllOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<TVShowAiringTodayEntity, Void,
            Void> {
        private TVShowAiringTodayDao dao;

        DBUpdateOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowAiringTodayEntity... tvShowEntities) {
            dao.update(tvShowEntities);
            return null;
        }
    }

    private static class DBFindTVShowByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            TVShowAiringTodayEntity> {
        private TVShowAiringTodayDao dao;

        public DBFindTVShowByIdOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected TVShowAiringTodayEntity doInBackground(Integer... integers) {
            return dao.findTVShowById(integers[0]);
        }
    }

    private static class DBFindAllTVShowsOperationAsyncTask extends AsyncTask<Void, Void,
            List<TVShowAiringTodayEntity>> {
        private TVShowAiringTodayDao dao;

        public DBFindAllTVShowsOperationAsyncTask(TVShowAiringTodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<TVShowAiringTodayEntity> doInBackground(Void... voids) {
            return dao.findAllTVShows();
        }
    }
}
