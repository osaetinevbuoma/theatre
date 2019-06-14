package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;

import java.util.Date;

public class TVShowRepository {
    private TVShowDao tvShowDao;

    public TVShowRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowDao = database.tvShowDao();
    }

    public void insert(TVShowEntity tvShowEntity) {
        new DBOperationAsyncTask(tvShowDao, 0).execute(tvShowEntity);
    }

    public void insertAll(TVShowEntity... tvShowEntities) {
        new DBOperationAsyncTask(tvShowDao, 1).execute(tvShowEntities);
    }

    public void deleteAll(Date date) {
        new DBOperationAsyncTask(tvShowDao, 2).execute(date);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowDao dao;
        private int operation;

        DBOperationAsyncTask(TVShowDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            switch (operation) {
                case 0:
                    dao.insert((TVShowEntity) objects[0]);
                    break;

                case 1:
                    dao.insertAll((TVShowEntity[]) objects);
                    break;

                case 2:
                    dao.deleteAll((Date) objects[0]);
                    break;
            }
            return null;
        }
    }
}
