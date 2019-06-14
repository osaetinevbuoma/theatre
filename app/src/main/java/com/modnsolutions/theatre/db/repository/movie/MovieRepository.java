package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieDao;
import com.modnsolutions.theatre.db.entity.movie.MovieEntity;

public class MovieRepository {
    private MovieDao movieDao;

    public MovieRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieDao = database.movieDao();
    }

    public void insert(MovieEntity movieEntity) {
        new InsertAsyncTask(movieDao).execute(movieEntity);
    }

    public void insertAll(MovieEntity... movieEntities) {
        new InsertAllAsyncTask(movieDao).execute(movieEntities);
    }

    public void deleteAll(String date) {
        new DeleteAllAsyncTask(movieDao).execute(date);
    }


    private static class InsertAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao dao;

        InsertAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            dao.insert(movieEntities[0]);
            return null;
        }
    }

    private static class InsertAllAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao dao;

        InsertAllAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            dao.insertAll(movieEntities);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<String, Void, Void> {
        private MovieDao dao;

        DeleteAllAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(String... dates) {
            dao.deleteAll(dates[0]);
            return null;
        }
    }
}
