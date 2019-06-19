package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTopRatedDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTopRatedEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TVShowTopRatedRepository {
    private TVShowTopRatedDao tvShowDao;

    public TVShowTopRatedRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowTopRatedDao();
    }

    public void insert(TVShowTopRatedEntity tvShowEntity) {
        new DBInsertOperationAsyncTask(tvShowDao).execute(tvShowEntity);
    }

    public void insertAll(TVShowTopRatedEntity... tvShowEntities) {
        new DBInsertAllOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(tvShowDao).execute();
    }

    public void update(TVShowTopRatedEntity... tvShowEntities) {
        new DBUpdateOperationAsyncTask(tvShowDao).execute(tvShowEntities);
    }

    public TVShowTopRatedEntity findTVShowById(int id) throws ExecutionException,
            InterruptedException {
        return new DBFindTVShowByIdOperationAsyncTask(tvShowDao).execute(id).get();
    }

    public List<TVShowTopRatedEntity> findAllTVShows() throws ExecutionException,
            InterruptedException {
        return new DBFindAllTVShowsOperationAsyncTask(tvShowDao).execute().get();
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowTopRatedEntity, Void,
            Void> {
        private TVShowTopRatedDao dao;

        DBInsertOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowTopRatedEntity... tvShowEntities) {
            dao.insert(tvShowEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowTopRatedEntity, Void,
            Void> {
        private TVShowTopRatedDao dao;

        DBInsertAllOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowTopRatedEntity... tvShowEntities) {
            dao.insertAll(tvShowEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVShowTopRatedDao dao;

        DBDeleteAllOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<TVShowTopRatedEntity, Void,
            Void> {
        private TVShowTopRatedDao dao;

        DBUpdateOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowTopRatedEntity... tvShowEntities) {
            dao.update(tvShowEntities);
            return null;
        }
    }

    private static class DBFindTVShowByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            TVShowTopRatedEntity> {
        private TVShowTopRatedDao dao;

        public DBFindTVShowByIdOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected TVShowTopRatedEntity doInBackground(Integer... integers) {
            return dao.findTVShowById(integers[0]);
        }
    }

    private static class DBFindAllTVShowsOperationAsyncTask extends AsyncTask<Void, Void,
            List<TVShowTopRatedEntity>> {
        private TVShowTopRatedDao dao;

        public DBFindAllTVShowsOperationAsyncTask(TVShowTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<TVShowTopRatedEntity> doInBackground(Void... voids) {
            return dao.findAllTVShows();
        }
    }
}
