package com.modnsolutions.theatre.db.repository.favorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteEntity;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;

    public FavoriteRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
    }

    public void insert(FavoriteEntity favoriteEntity) {
        new DBOperationAsyncTask(favoriteDao, 0).execute(favoriteEntity);
    }

    public void delete(FavoriteEntity favoriteEntity) {
        new DBOperationAsyncTask(favoriteDao, 1).execute(favoriteEntity);
    }

    public void deleteAll() {
        new DBOperationAsyncTask(favoriteDao, 2).execute();
    }

    public LiveData<List<FavoriteEntity>> fetchFavorites(int id, int offset) {
        return favoriteDao.fetchFavorites(id, offset);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private FavoriteDao dao;
        private int operation;

        public DBOperationAsyncTask(FavoriteDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            switch (operation) {
                case 0:
                    dao.insert((FavoriteEntity) objects[0]);
                    break;

                case 1:
                    dao.delete((FavoriteEntity) objects[0]);
                    break;

                case 2:
                    dao.deleteAll();
                    break;
            }

            return null;
        }
    }
}
