package com.modnsolutions.theatre.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.EpisodeDao;
import com.modnsolutions.theatre.db.entity.EpisodeEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EpisodeRepository {
    private EpisodeDao episodeDao;

    public EpisodeRepository(Application application) {
        episodeDao = TheatreDatabase.getInstance(application).episodeDao();
    }

    public void insert(EpisodeEntity... episodeEntities) {
        new DBOperationAsyncTask(episodeDao).execute(episodeEntities);
    }

    public LiveData<List<EpisodeEntity>> findBySeasonId(int seasonId) {
        return episodeDao.findBySeasonId(seasonId);
    }

    public EpisodeEntity[] findAllBySeasonId(int seasonId) {
        EpisodeEntity[] entities = null;

        try {
            entities = new DBFindAllBySeasonIdAsyncTask(episodeDao).execute(seasonId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entities;
    }



    private static class DBOperationAsyncTask extends AsyncTask<EpisodeEntity, Void, Void> {
        private EpisodeDao dao;

        public DBOperationAsyncTask(EpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EpisodeEntity... episodeEntities) {
            dao.insert(episodeEntities);
            return null;
        }
    }

    private static class DBFindAllBySeasonIdAsyncTask extends AsyncTask<Integer, Void,
            EpisodeEntity[]> {
        private EpisodeDao dao;

        public DBFindAllBySeasonIdAsyncTask(EpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected EpisodeEntity[] doInBackground(Integer... integers) {
            return dao.findAllBySeasonId(integers[0]);
        }
    }
}
