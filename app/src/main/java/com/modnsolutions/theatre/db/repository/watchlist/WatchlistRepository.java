package com.modnsolutions.theatre.db.repository.watchlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistDao;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEntity;

import java.util.List;

public class WatchlistRepository {
    private WatchlistDao watchlistDao;

    public WatchlistRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        watchlistDao = database.watchlistDao();
    }

    public void insert(WatchlistEntity watchlistEntity) {
        new DBOperationAsyncTask(watchlistDao, 0).execute(watchlistEntity);
    }

    public void delete(WatchlistEntity watchlistEntity) {
        new DBOperationAsyncTask(watchlistDao, 1).execute(watchlistEntity);
    }

    public void deleteAll() {
        new DBOperationAsyncTask(watchlistDao, 2).execute();
    }

    public LiveData<List<WatchlistEntity>> fetchWatchlist(int id, int offset) {
        return watchlistDao.fetchWatchlist(id, offset);
    }


    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private WatchlistDao dao;
        private int operation;

        public DBOperationAsyncTask(WatchlistDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            switch (operation) {
                case 0:
                    dao.insert((WatchlistEntity) objects[0]);
                    break;

                case 1:
                    dao.delete((WatchlistEntity) objects[0]);
                    break;

                case 2:
                    dao.deleteAll();
                    break;
            }

            return null;
        }
    }
}
