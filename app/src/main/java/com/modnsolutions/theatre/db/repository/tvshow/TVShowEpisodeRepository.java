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
        new DBOperationAsyncTask(tvShowEpisodeDao, 0).execute(tvShowEpisodeEntity);
    }

    public void insertAll(TVShowEpisodeEntity... tvShowEpisodeEntities) {
        new DBOperationAsyncTask(tvShowEpisodeDao, 1).execute(tvShowEpisodeEntities);
    }

    public LiveData<List<TVShowEpisodeEntity>> fetchAllSeasonEpisodes(int id) {
        return tvShowEpisodeDao.fetchAllSeasonEpisodes(id);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowEpisodeDao dao;
        private int operation;

        public DBOperationAsyncTask(TVShowEpisodeDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if (operation == 0) dao.insert((TVShowEpisodeEntity) objects[0]);
            else if (operation == 1) dao.insertAll((TVShowEpisodeEntity[]) objects);
            return null;
        }
    }
}
