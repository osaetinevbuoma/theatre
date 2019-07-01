package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.SeasonDao;
import com.modnsolutions.theatre.db.entity.SeasonEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SeasonRepository {
    private SeasonDao seasonDao;

    public SeasonRepository(Application application) {
        seasonDao = TheatreDatabase.getInstance(application).seasonDao();
    }

    public void insert(SeasonEntity... seasonEntities) {
        new DBOperationAsyncTask(seasonDao).execute(seasonEntities);
    }

    public LiveData<List<SeasonEntity>> findByTheatreId(int theatreId) {
        return seasonDao.findByTheatreId(theatreId);
    }

    public SeasonEntity findByRemoteId(int remoteId) {
        SeasonEntity entity = null;

        try {
            entity = new DBFindByIdAsyncTask(seasonDao).execute(remoteId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }



    private static class DBOperationAsyncTask extends AsyncTask<SeasonEntity, Void, Void> {
        private SeasonDao dao;

        public DBOperationAsyncTask(SeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SeasonEntity... seasonEntities) {
            dao.insert(seasonEntities);
            return null;
        }
    }

    private static class DBFindByIdAsyncTask extends AsyncTask<Integer, Void, SeasonEntity> {
        private SeasonDao dao;

        public DBFindByIdAsyncTask(SeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected SeasonEntity doInBackground(Integer... integers) {
            return dao.findByRemoteId(integers[0]);
        }
    }
}
