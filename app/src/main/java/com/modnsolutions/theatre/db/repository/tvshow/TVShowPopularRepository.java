package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowPopularDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowPopularEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowPopularRepository {
    private TVShowPopularDao tvShowDao;

    public TVShowPopularRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowPopularDao();
    }

    public void insert(TVShowPopularEntity tvShowEntity) {
        new DBInsertOperationAsyncTask(tvShowDao).execute(tvShowEntity);
    }

    public void insertAll(TVShowPopularEntity... tvShowEntities) {
        new DBInsertAllOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(tvShowDao).execute();
    }

    public void update(TVShowPopularEntity... tvShowEntities) {
        new DBUpdateOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public TVShowPopularEntity findTVShowById(int id) throws ExecutionException,
            InterruptedException {
        return new DBFindTVShowByIdOperationAsyncTask(tvShowDao).execute(id).get();
    }

    public List<TVShowPopularEntity> findAllTVShows() throws ExecutionException,
            InterruptedException {
        return new DBFindAllTVShowsOperationAsyncTask(tvShowDao).execute().get();
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowPopularEntity, Void,
            Void> {
        private TVShowPopularDao dao;

        DBInsertOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowPopularEntity... tvShowEntities) {
            dao.insert(tvShowEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowPopularEntity, Void,
            Void> {
        private TVShowPopularDao dao;

        DBInsertAllOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowPopularEntity... tvShowEntities) {
            dao.insertAll(tvShowEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVShowPopularDao dao;

        DBDeleteAllOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<TVShowPopularEntity, Void,
            Void> {
        private TVShowPopularDao dao;

        DBUpdateOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowPopularEntity... tvShowEntities) {
            dao.update(tvShowEntities);
            return null;
        }
    }

    private static class DBFindTVShowByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            TVShowPopularEntity> {
        private TVShowPopularDao dao;

        public DBFindTVShowByIdOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected TVShowPopularEntity doInBackground(Integer... integers) {
            return dao.findTVShowById(integers[0]);
        }
    }

    private static class DBFindAllTVShowsOperationAsyncTask extends AsyncTask<Void, Void,
            List<TVShowPopularEntity>> {
        private TVShowPopularDao dao;

        public DBFindAllTVShowsOperationAsyncTask(TVShowPopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<TVShowPopularEntity> doInBackground(Void... voids) {
            return dao.findAllTVShows();
        }
    }
}
