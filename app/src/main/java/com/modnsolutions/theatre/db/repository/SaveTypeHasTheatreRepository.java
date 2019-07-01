package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.SaveTypeHasTheatreDao;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;

import java.util.concurrent.ExecutionException;

public class SaveTypeHasTheatreRepository {
    private SaveTypeHasTheatreDao saveTypeHasTheatreDao;

    public SaveTypeHasTheatreRepository(Application application) {
        saveTypeHasTheatreDao = TheatreDatabase.getInstance(application).saveTypeHasTheatreDao();
    }

    public void insert(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities) {
        new DBOperationAsyncTask(saveTypeHasTheatreDao, 1)
                .execute(saveTypeHasTheatreEntities);
    }

    public void delete(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities) {
        new DBOperationAsyncTask(saveTypeHasTheatreDao, 2)
                .execute(saveTypeHasTheatreEntities);
    }

    public SaveTypeHasTheatreEntity[] findByTheatreId(int theatreId) {
        SaveTypeHasTheatreEntity[] entities = null;

        try {
            entities = new DBFindByTheatreIdOperationAsyncTask(saveTypeHasTheatreDao)
                    .execute(theatreId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entities;
    }

    public SaveTypeHasTheatreEntity findByTheatreIdAndSaveTypeId(int theatreId, int saveTypeId) {
        SaveTypeHasTheatreEntity entity = null;

        try {
            entity = new DBFindByTheatreIdAndSaveTypeIdOperationAsyncTask(saveTypeHasTheatreDao)
                    .execute(theatreId, saveTypeId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }

    private static class DBOperationAsyncTask extends AsyncTask<SaveTypeHasTheatreEntity, Void, Void> {
        private SaveTypeHasTheatreDao dao;
        private int operation;

        public DBOperationAsyncTask(SaveTypeHasTheatreDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(SaveTypeHasTheatreEntity... saveTypeHasTheatreEntities) {
            if (operation == 1) dao.insert(saveTypeHasTheatreEntities);
            if (operation == 2) dao.delete(saveTypeHasTheatreEntities);
            return null;
        }
    }

    private static class DBFindByTheatreIdOperationAsyncTask extends AsyncTask<Integer, Void,
            SaveTypeHasTheatreEntity[]> {
        private SaveTypeHasTheatreDao dao;

        public DBFindByTheatreIdOperationAsyncTask(SaveTypeHasTheatreDao dao) {
            this.dao = dao;
        }

        @Override
        protected SaveTypeHasTheatreEntity[] doInBackground(Integer... integers) {
            return dao.findByTheatreId(integers[0]);
        }
    }

    private static class DBFindByTheatreIdAndSaveTypeIdOperationAsyncTask extends AsyncTask<Integer,
            Void, SaveTypeHasTheatreEntity> {
        private SaveTypeHasTheatreDao dao;

        public DBFindByTheatreIdAndSaveTypeIdOperationAsyncTask(SaveTypeHasTheatreDao dao) {
            this.dao = dao;
        }

        @Override
        protected SaveTypeHasTheatreEntity doInBackground(Integer... integers) {
            return dao.findByTheatreIdAndSaveTypeId(integers[0], integers[1]);
        }
    }
}
