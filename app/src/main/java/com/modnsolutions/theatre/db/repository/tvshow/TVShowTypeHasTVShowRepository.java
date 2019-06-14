package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTypeHasTVShowDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeHasTVShowEntity;

import java.util.List;

public class TVShowTypeHasTVShowRepository {
    private TVShowTypeHasTVShowDao tvShowTypeHasTVShowDao;

    public TVShowTypeHasTVShowRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowTypeHasTVShowDao = database.tvShowTypeHasTVShowDao();
    }

    public void insert(TVShowTypeHasTVShowEntity tvShowTypeHasTVShowEntity) {
        new DBOperationAsyncTask(tvShowTypeHasTVShowDao).execute(tvShowTypeHasTVShowEntity);
    }

    public LiveData<List<TVShowEntity>> fetchTVShowsOfType(int id, int offset) {
        return tvShowTypeHasTVShowDao.fetchTVShowsOfType(id, offset);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowTypeHasTVShowDao dao;

        public DBOperationAsyncTask(TVShowTypeHasTVShowDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            dao.insert((TVShowTypeHasTVShowEntity) objects[0]);
            return null;
        }
    }
}
