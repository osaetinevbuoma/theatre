package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowOnTheAirDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowOnTheAirEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowOnTheAirRepository {
    private TVShowOnTheAirDao tvShowDao;

    public TVShowOnTheAirRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowOnTheAirDao();
    }

    public void insert(TVShowOnTheAirEntity tvShowEntity) {
        new DBInsertOperationAsyncTask(tvShowDao).execute(tvShowEntity);
    }

    public void insertAll(TVShowOnTheAirEntity... tvShowEntities) {
        new DBInsertAllOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(tvShowDao).execute();
    }

    public void update(TVShowOnTheAirEntity... tvShowEntities) {
        new DBUpdateOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public TVShowOnTheAirEntity findTVShowById(int id) throws ExecutionException, 
            InterruptedException {
        return new DBFindTVShowByIdOperationAsyncTask(tvShowDao).execute(id).get();
    }
    
    public List<TVShowOnTheAirEntity> findAllTVShows() throws ExecutionException, 
            InterruptedException {
        return new DBFindAllTVShowsOperationAsyncTask(tvShowDao).execute().get();
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowOnTheAirEntity, Void,
            Void> {
        private TVShowOnTheAirDao dao;

        DBInsertOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowOnTheAirEntity... tvShowEntities) {
            dao.insert(tvShowEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowOnTheAirEntity, Void,
            Void> {
        private TVShowOnTheAirDao dao;

        DBInsertAllOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowOnTheAirEntity... tvShowEntities) {
            dao.insertAll(tvShowEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVShowOnTheAirDao dao;

        DBDeleteAllOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<TVShowOnTheAirEntity, Void,
            Void> {
        private TVShowOnTheAirDao dao;

        DBUpdateOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowOnTheAirEntity... tvShowEntities) {
            dao.update(tvShowEntities);
            return null;
        }
    }

    private static class DBFindTVShowByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            TVShowOnTheAirEntity> {
        private TVShowOnTheAirDao dao;

        public DBFindTVShowByIdOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected TVShowOnTheAirEntity doInBackground(Integer... integers) {
            return dao.findTVShowById(integers[0]);
        }
    }

    private static class DBFindAllTVShowsOperationAsyncTask extends AsyncTask<Void, Void, 
            List<TVShowOnTheAirEntity>> {
        private TVShowOnTheAirDao dao;

        public DBFindAllTVShowsOperationAsyncTask(TVShowOnTheAirDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<TVShowOnTheAirEntity> doInBackground(Void... voids) {
            return dao.findAllTVShows();
        }
    }
}
