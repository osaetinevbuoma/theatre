package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.entity.FavoriteEntity;

import java.util.concurrent.ExecutionException;

public class TheatreRepository {
    private TheatreDao theatreDao;

    public TheatreRepository(Application application) {
        theatreDao = TheatreDatabase.getInstance(application).theatreDao();
    }

    public void insert(FavoriteEntity... theatreEntities) {
        new DBOperationAsyncTask(theatreDao, 1).execute(theatreEntities);
    }

    public void update(FavoriteEntity... theatreEntities) {
        new DBOperationAsyncTask(theatreDao, 2).execute(theatreEntities);
    }

    public void delete(FavoriteEntity favoriteEntity) {
        new DBOperationAsyncTask(theatreDao, 3).execute(favoriteEntity);
    }

    public LiveData<FavoriteEntity> findOneById(int id) {
        return theatreDao.findOneById(id);
    }

    public FavoriteEntity findOneByRemoteId(int remoteId) {
        FavoriteEntity entity = null;

        try {
            entity = new DBFindOneByRemoteIdAsyncTask(theatreDao).execute(remoteId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<FavoriteEntity, Void, Void> {
        private TheatreDao dao;
        private int operation;

        public DBOperationAsyncTask(TheatreDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(FavoriteEntity... theatreEntities) {
            switch (operation) {
                case 1: dao.insert(theatreEntities); break;
                case 2: dao.update(theatreEntities); break;
                case 3: dao.delete(theatreEntities[0]);
            }
            return null;
        }
    }

    private static class DBFindOneByRemoteIdAsyncTask extends AsyncTask<Integer, Void, FavoriteEntity> {
        private TheatreDao dao;

        public DBFindOneByRemoteIdAsyncTask(TheatreDao dao) {
            this.dao = dao;
        }

        @Override
        protected FavoriteEntity doInBackground(Integer... integers) {
            return dao.findOneByRemoteId(integers[0]);
        }
    }
}
