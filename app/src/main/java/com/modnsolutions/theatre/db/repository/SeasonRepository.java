package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.SeasonDao;
import com.modnsolutions.theatre.db.entity.SeasonEntity;

import java.util.List;

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
}
