package com.modnsolutions.theatre.db.repository.watchlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistTrailerDao;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTrailerEntity;

import java.util.List;

public class WatchlistTrailerRepository {
    private WatchlistTrailerDao watchlistTrailerDao;

    public WatchlistTrailerRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        watchlistTrailerDao = database.watchlistTrailerDao();
    }

    public void insertAll(WatchlistTrailerEntity... watchlistTrailerEntities) {
        new DBOperationAsyncTask(watchlistTrailerDao).execute(watchlistTrailerEntities);
    }

    public LiveData<List<WatchlistTrailerEntity>> fetchTrailers(int id) {
        return watchlistTrailerDao.fetchTrailers(id);
    }


    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private WatchlistTrailerDao dao;

        public DBOperationAsyncTask(WatchlistTrailerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.insertAll((WatchlistTrailerEntity[]) objects);
            return null;
        }
    }
}
