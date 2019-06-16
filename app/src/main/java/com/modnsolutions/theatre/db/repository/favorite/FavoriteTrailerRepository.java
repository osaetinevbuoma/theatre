package com.modnsolutions.theatre.db.repository.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTrailerDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteTrailerEntity;

import java.util.List;

public class FavoriteTrailerRepository {
    private FavoriteTrailerDao favoriteTrailerDao;

    public FavoriteTrailerRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        favoriteTrailerDao = database.favoriteTrailerDao();
    }

    public void insertAll(FavoriteTrailerEntity... favoriteTrailerEntities) {
        new DBOperationAsyncTask(favoriteTrailerDao).execute(favoriteTrailerEntities);
    }

    public LiveData<List<FavoriteTrailerEntity>> fetchTrailers(int id) {
        return favoriteTrailerDao.fetchTrailers(id);
    }


    private static class DBOperationAsyncTask extends AsyncTask<FavoriteTrailerEntity, Void, Void> {
        private FavoriteTrailerDao dao;

        public DBOperationAsyncTask(FavoriteTrailerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteTrailerEntity... favoriteTrailerEntities) {
            dao.insertAll(favoriteTrailerEntities);
            return null;
        }
    }
}
