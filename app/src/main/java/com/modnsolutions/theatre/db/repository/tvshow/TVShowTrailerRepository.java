package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTrailerDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;

import java.util.List;

public class TVShowTrailerRepository {
    private TVShowTrailerDao tvShowTrailerDao;

    public TVShowTrailerRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowTrailerDao = database.tvShowTrailerDao();
    }

    public void insert(TVShowTrailerEntity tvShowTrailerEntity) {
        new DBOperationAsyncTask(tvShowTrailerDao, 0).execute(tvShowTrailerEntity);
    }

    public void insertAll(TVShowTrailerEntity... tvShowTrailerEntities) {
        new DBOperationAsyncTask(tvShowTrailerDao, 1).execute(tvShowTrailerEntities);
    }

    public LiveData<List<TVShowTrailerEntity>> fetchAllTVShowTrailers(int tvShowId) {
        return tvShowTrailerDao.fetchAllTVShowTrailers(tvShowId);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowTrailerDao dao;
        private int operation;

        public DBOperationAsyncTask(TVShowTrailerDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if (operation == 0) dao.insert((TVShowTrailerEntity) objects[0]);
            else if (operation == 1) dao.insertAll((TVShowTrailerEntity[]) objects);
            return null;
        }
    }
}
