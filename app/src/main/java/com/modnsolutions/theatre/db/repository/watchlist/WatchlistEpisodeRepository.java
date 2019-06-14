package com.modnsolutions.theatre.db.repository.watchlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistEpisodeDao;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEpisodeEntity;

import java.util.List;

public class WatchlistEpisodeRepository {
    private WatchlistEpisodeDao watchlistEpisodeDao;

    public WatchlistEpisodeRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        watchlistEpisodeDao = database.watchlistEpisodeDao();
    }

    public void insertAll(WatchlistEpisodeEntity watchlistEpisodeEntity) {
        new DBOperationAsyncTask(watchlistEpisodeDao).execute(watchlistEpisodeEntity);
    }

    public LiveData<List<WatchlistEpisodeEntity>> fetchWatchlistSeasonEpisodes(int id) {
        return watchlistEpisodeDao.fetchWatchlistSesaonEpisodes(id);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private WatchlistEpisodeDao dao;

        public DBOperationAsyncTask(WatchlistEpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.insertAll((WatchlistEpisodeEntity[]) objects);
            return null;
        }
    }
}
