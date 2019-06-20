package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.TrailerDao;
import com.modnsolutions.theatre.db.entity.TrailerEntity;

import java.util.List;

public class TrailerRepository {
    private TrailerDao trailerDao;

    public TrailerRepository(Application application) {
        trailerDao = TheatreDatabase.getInstance(application).trailerDao();
    }

    public void insert(TrailerEntity... trailerEntities) {
        new DBOperationAsyncTask(trailerDao).execute(trailerEntities);
    }

    public LiveData<List<TrailerEntity>> findByTheatreId(int theatreId) {
        return trailerDao.findByTheatreId(theatreId);
    }



    private static class DBOperationAsyncTask extends AsyncTask<TrailerEntity, Void, Void> {
        private TrailerDao dao;

        public DBOperationAsyncTask(TrailerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TrailerEntity... trailerEntities) {
            dao.insert(trailerEntities);
            return null;
        }
    }
}
