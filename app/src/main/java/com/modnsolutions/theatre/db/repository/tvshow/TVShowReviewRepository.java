package com.modnsolutions.theatre.db.repository.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowReviewDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;

import java.util.List;

public class TVShowReviewRepository {
    private TVShowReviewDao tvShowReviewDao;

    public TVShowReviewRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        tvShowReviewDao = database.tvShowReviewDao();
    }

    public void insert(TVShowReviewEntity tvShowReviewEntity) {
        new DBOperationAsyncTask(tvShowReviewDao, 0).execute(tvShowReviewEntity);
    }

    public void insertAll(TVShowReviewEntity... tvShowReviewEntities) {
        new DBOperationAsyncTask(tvShowReviewDao, 1).execute(tvShowReviewEntities);
    }

    public LiveData<List<TVShowReviewEntity>> fetchTVShowReviews(int tvShowId, int offset) {
        return tvShowReviewDao.fetchTVShowReviews(tvShowId, offset);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private TVShowReviewDao dao;
        private int operation;

        public DBOperationAsyncTask(TVShowReviewDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if (operation == 0) dao.insert((TVShowReviewEntity) objects[0]);
            else if (operation == 1) dao.insertAll((TVShowReviewEntity[]) objects);
            return null;
        }
    }
}
