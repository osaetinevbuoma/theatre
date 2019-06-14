package com.modnsolutions.theatre.db.repository.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteSeasonDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteSeasonEntity;

import java.util.List;

public class FavoriteSeasonRepository {
    private FavoriteSeasonDao favoriteSeasonDao;

    public FavoriteSeasonRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        favoriteSeasonDao = database.favoriteSeasonDao();
    }

    public void insertAll(FavoriteSeasonEntity... favoriteSeasonEntities) {
        new DBOperationAsyncTask(favoriteSeasonDao).execute(favoriteSeasonEntities);
    }

    public LiveData<List<FavoriteSeasonEntity>> fetchFavoriteSeasons(int id) {
        return favoriteSeasonDao.fetchFavoriteSeasons(id);
    }


    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private FavoriteSeasonDao dao;

        public DBOperationAsyncTask(FavoriteSeasonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.insertAll((FavoriteSeasonEntity[]) objects);
            return null;
        }
    }
}
