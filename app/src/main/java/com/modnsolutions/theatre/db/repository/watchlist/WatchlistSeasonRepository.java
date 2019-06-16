package com.modnsolutions.theatre.db.repository.watchlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistSeasonDao;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistSeasonEntity;

import java.util.List;

public class WatchlistSeasonRepository {
    private WatchlistSeasonDao watchlistSeasonDao;

    public WatchlistSeasonRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        watchlistSeasonDao = database.watchlistSeasonDao();
    }

    public void insertAll(WatchlistSeasonEntity... watchlistSeasonEntities) {
        new DBOperationAsyncTask(watchlistSeasonDao).execute(watchlistSeasonEntities);
    }

    public LiveData<List<WatchlistSeasonEntity>> fetchWatchlistSeasons(int id) {
        return watchlistSeasonDao.fetchWatchlistSeasons(id);
    }


    private static class DBOperationAsyncTask extends AsyncTask<WatchlistSeasonEntity, Void, Void> {
        private WatchlistSeasonDao dao;

        public DBOperationAsyncTask(WatchlistSeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(WatchlistSeasonEntity... watchlistSeasonEntities) {
            dao.insertAll(watchlistSeasonEntities);
            return null;
        }
    }
}
