package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowEpisodeDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;

import java.util.List;

public class TVShowEpisodeRepository {
    private TVShowEpisodeDao tvShowEpisodeDao;

    public TVShowEpisodeRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowEpisodeDao = database.tvShowEpisodeDao();
    }

    public void insert(TVShowEpisodeEntity tvShowEpisodeEntity) {
        new DBInsertOperationAsyncTask(tvShowEpisodeDao).execute(tvShowEpisodeEntity);
    }

    public void insertAll(TVShowEpisodeEntity... tvShowEpisodeEntities) {
        new DBInsertAllOperationAsyncTask(tvShowEpisodeDao).execute(tvShowEpisodeEntities);
    }

    public LiveData<List<TVShowEpisodeEntity>> fetchAllSeasonEpisodes(int id) {
        return tvShowEpisodeDao.fetchAllSeasonEpisodes(id);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<TVShowEpisodeEntity, Void, Void> {
        private TVShowEpisodeDao dao;

        public DBInsertOperationAsyncTask(TVShowEpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowEpisodeEntity... tvShowEpisodeEntities) {
            dao.insert(tvShowEpisodeEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<TVShowEpisodeEntity, Void, Void> {
        private TVShowEpisodeDao dao;

        public DBInsertAllOperationAsyncTask(TVShowEpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TVShowEpisodeEntity... tvShowEpisodeEntities) {
            dao.insertAll(tvShowEpisodeEntities);
            return null;
        }
    }
}
