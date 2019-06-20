package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.entity.TheatreEntity;

public class TheatreRepository {
    private TheatreDao theatreDao;

    public TheatreRepository(Application application) {
        theatreDao = TheatreDatabase.getInstance(application).theatreDao();
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

    public LiveData<TheatreEntity> findOneById(int id) {
        return theatreDao.findOneById(id);
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
}
