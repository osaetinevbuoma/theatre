package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.WatchlistDao;
import com.modnsolutions.theatre.db.entity.WatchlistEntity;

import java.util.concurrent.ExecutionException;

public class WatchlistRepository {
    private WatchlistDao watchlistDao;

    public WatchlistRepository(Application application) {
        watchlistDao = TheatreDatabase.getInstance(application).watchlistDao();
    }

    public void insert(WatchlistEntity... watchlistEntities) {
        new DBOperationAsyncTask(watchlistDao, 1).execute(watchlistEntities);
    }

    public void update(WatchlistEntity... watchlistEntities) {
        new DBOperationAsyncTask(watchlistDao, 2).execute(watchlistEntities);
    }

    public void delete(WatchlistEntity watchlistEntity) {
        new DBOperationAsyncTask(watchlistDao, 3).execute(watchlistEntity);
    }

    public LiveData<WatchlistEntity> findOneById(int id) {
        return watchlistDao.findOneById(id);
    }

    public WatchlistEntity findOneByRemoteId(int remoteId) {
        WatchlistEntity entity = null;

        try {
            entity = new DBFindOneByRemoteIdAsyncTask(watchlistDao).execute(remoteId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<WatchlistEntity, Void, Void> {
        private WatchlistDao dao;
        private int operation;

        public DBOperationAsyncTask(WatchlistDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(WatchlistEntity... watchlistEntities) {
            switch (operation) {
                case 1: dao.insert(watchlistEntities); break;
                case 2: dao.update(watchlistEntities); break;
                case 3: dao.delete(watchlistEntities[0]);
            }
            return null;
        }
    }

    private static class DBFindOneByRemoteIdAsyncTask extends AsyncTask<Integer, Void, WatchlistEntity> {
        private WatchlistDao dao;

        public DBFindOneByRemoteIdAsyncTask(WatchlistDao dao) {
            this.dao = dao;
        }

        @Override
        protected WatchlistEntity doInBackground(Integer... integers) {
            return dao.findOneByRemoteId(integers[0]);
        }
    }
}
