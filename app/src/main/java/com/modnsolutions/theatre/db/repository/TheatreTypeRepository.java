package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreTypeDao;
import com.modnsolutions.theatre.db.entity.TheatreTypeEntity;

import java.util.concurrent.ExecutionException;

public class TheatreTypeRepository {
    private TheatreTypeDao theatreTypeDao;

    public TheatreTypeRepository(Application application) {
        theatreTypeDao = TheatreDatabase.getInstance(application).theatreTypeDao();
    }

    public TheatreTypeEntity findOneById(int id) {
        TheatreTypeEntity entity = null;

        try {
            entity = new DBOperationAsyncTask(theatreTypeDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public TheatreTypeEntity findOneByType(String type) {
        TheatreTypeEntity entity = null;

        try {
            entity = new DBOperation2AsyncTask(theatreTypeDao).execute(type).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<Integer, Void, TheatreTypeEntity> {
        private TheatreTypeDao dao;

        public DBOperationAsyncTask(TheatreTypeDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreTypeEntity doInBackground(Integer... integers) {
            return dao.findOneById(integers[0]);
        }
    }

    private static class DBOperation2AsyncTask extends AsyncTask<String, Void, TheatreTypeEntity> {
        private TheatreTypeDao dao;

        public DBOperation2AsyncTask(TheatreTypeDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreTypeEntity doInBackground(String... strings) {
            return dao.findOneByType(strings[0]);
        }
    }
}
