package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreSaveTypeDao;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;

import java.util.concurrent.ExecutionException;

public class TheatreSaveTypeRepository {
    private TheatreSaveTypeDao theatreSaveTypeDao;

    public TheatreSaveTypeRepository(Application application) {
        theatreSaveTypeDao = TheatreDatabase.getInstance(application).theatreSaveTypeDao();
    }

    public TheatreSaveTypeEntity findOneById(int id) {
        TheatreSaveTypeEntity entity = null;

        try {
            entity = new DBOperationAsyncTask(theatreSaveTypeDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public TheatreSaveTypeEntity findOneByType(String type) {
        TheatreSaveTypeEntity entity = null;

        try {
            entity = new DBOperation2AsyncTask(theatreSaveTypeDao).execute(type).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<Integer, Void, TheatreSaveTypeEntity> {
        private TheatreSaveTypeDao dao;

        public DBOperationAsyncTask(TheatreSaveTypeDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreSaveTypeEntity doInBackground(Integer... integers) {
            return dao.findOneById(integers[0]);
        }
    }

    private static class DBOperation2AsyncTask extends AsyncTask<String, Void, TheatreSaveTypeEntity> {
        private TheatreSaveTypeDao dao;

        public DBOperation2AsyncTask(TheatreSaveTypeDao dao) {
            this.dao = dao;
        }

        @Override
        protected TheatreSaveTypeEntity doInBackground(String... strings) {
            return dao.findOneByType(strings[0]);
        }
    }
}
