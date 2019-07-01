package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.FavoriteDao;
import com.modnsolutions.theatre.db.entity.TheatreEntity;

import java.util.concurrent.ExecutionException;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;

    public FavoriteRepository(Application application) {
        favoriteDao = TheatreDatabase.getInstance(application).favoriteDao();
    }

    public void insert(TheatreEntity... favoriteEntities) {
        new DBOperationAsyncTask(favoriteDao, 1).execute(favoriteEntities);
    }

    public void update(TheatreEntity... favoriteEntities) {
        new DBOperationAsyncTask(favoriteDao, 2).execute(favoriteEntities);
    }

    public void delete(TheatreEntity theatreEntity) {
        new DBOperationAsyncTask(favoriteDao, 3).execute(theatreEntity);
    }

    public LiveData<TheatreEntity> findOneById(int id) {
        return favoriteDao.findOneById(id);
    }

    public TheatreEntity findOneByRemoteId(int remoteId) {
        TheatreEntity entity = null;

        try {
            entity = new DBFindOneByRemoteIdAsyncTask(favoriteDao).execute(remoteId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<TheatreEntity, Void, Void> {
        private FavoriteDao dao;
        private int operation;

        public DBOperationAsyncTask(FavoriteDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(TheatreEntity... favoriteEntities) {
            switch (operation) {
                case 1: dao.insert(favoriteEntities); break;
                case 2: dao.update(favoriteEntities); break;
                case 3: dao.delete(favoriteEntities[0]);
            }
            return null;
        }
    }

    private static class DBFindOneByRemoteIdAsyncTask extends AsyncTask<Integer, Void, TheatreEntity> {
        private FavoriteDao dao;

        public DBFindOneByRemoteIdAsyncTask(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreEntity doInBackground(Integer... integers) {
            return dao.findOneByRemoteId(integers[0]);
        }
    }
}
