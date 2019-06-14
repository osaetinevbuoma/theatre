package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowSeasonDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;

import java.util.List;

public class TVShowSeasonRepository {
    private TVShowSeasonDao tvShowSeasonDao;

    public TVShowSeasonRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowSeasonDao = database.tvShowSeasonDao();
    }

    public void insert(TVShowSeasonEntity tvShowSeasonEntity) {
        new DBOperationAsyncTask(tvShowSeasonDao, 0).execute(tvShowSeasonEntity);
    }

    public void insertAll(TVShowSeasonEntity... tvShowSeasonEntities) {
        new DBOperationAsyncTask(tvShowSeasonDao, 1).execute(tvShowSeasonEntities);
    }

    public LiveData<List<TVShowSeasonEntity>> fetchAllSeasons(int id) {
        return tvShowSeasonDao.fetchAllSeasons(id);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowSeasonDao dao;
        private int operation;

        public DBOperationAsyncTask(TVShowSeasonDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if (operation == 0) dao.insert((TVShowSeasonEntity) objects[0]);
            else if (operation == 1) dao.insertAll((TVShowSeasonEntity[]) objects);
            return null;
        }
    }
}
