package com.modnsolutions.theatre.db.repository.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteEpisodeDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteEpisodeEntity;

import java.util.List;

public class FavoriteEpisodeRepository {
    private FavoriteEpisodeDao favoriteEpisodeDao;

    public FavoriteEpisodeRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        favoriteEpisodeDao = database.favoriteEpisodeDao();
    }

    public void insertAll(FavoriteEpisodeEntity... favoriteEpisodeEntities) {
        new DBOperationAsyncTask(favoriteEpisodeDao).execute(favoriteEpisodeEntities);
    }

    public LiveData<List<FavoriteEpisodeEntity>> fetchFavoriteSeasonEpisodes(int id) {
        return favoriteEpisodeDao.fetchFavoriteSeasonEpisodes(id);
    }


    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private FavoriteEpisodeDao dao;

        public DBOperationAsyncTask(FavoriteEpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.insertAll((FavoriteEpisodeEntity[]) objects);
            return null;
        }
    }
}
