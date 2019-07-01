package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.entity.TheatreEntity;

import java.util.concurrent.ExecutionException;

public class TheatreRepository {
    private TheatreDao theatreDao;

    public TheatreRepository(Application application) {
        theatreDao = TheatreDatabase.getInstance(application).favoriteDao();
    }

    public void insert(TheatreEntity... theatreEntities) {
        new DBOperationAsyncTask(theatreDao, 1).execute(theatreEntities);
    }

    public void update(TheatreEntity... theatreEntities) {
        new DBOperationAsyncTask(theatreDao, 2).execute(theatreEntities);
    }

    public void delete(TheatreEntity theatreEntity) {
        new DBOperationAsyncTask(theatreDao, 3).execute(theatreEntity);
    }

    public TheatreEntity findOneById(int id) {
        TheatreEntity entity = null;

        try {
            entity = new DBFindOnByIdAsyncTask(theatreDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public TheatreEntity findOneByRemoteId(int remoteId) {
        TheatreEntity entity = null;

        try {
            entity = new DBFindOneByRemoteIdAsyncTask(theatreDao).execute(remoteId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<TheatreEntity, Void, Void> {
        private TheatreDao dao;
        private int operation;

        public DBOperationAsyncTask(TheatreDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(TheatreEntity... theatreEntities) {
            switch (operation) {
                case 1: dao.insert(theatreEntities); break;
                case 2: dao.update(theatreEntities); break;
                case 3: dao.delete(theatreEntities[0]);
            }
            return null;
        }
    }

    private static class DBFindOnByIdAsyncTask extends AsyncTask<Integer, Void, TheatreEntity> {
        private TheatreDao dao;

        public DBFindOnByIdAsyncTask(TheatreDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreEntity doInBackground(Integer... integers) {
            return dao.findOneById(integers[0]);
        }
    }

    private static class DBFindOneByRemoteIdAsyncTask extends AsyncTask<Integer, Void, TheatreEntity> {
        private TheatreDao dao;

        public DBFindOneByRemoteIdAsyncTask(TheatreDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreEntity doInBackground(Integer... integers) {
            return dao.findOneByRemoteId(integers[0]);
        }
    }
}
