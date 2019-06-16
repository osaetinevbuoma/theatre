package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieDao;
import com.modnsolutions.theatre.db.entity.movie.MovieEntity;

import java.util.Date;

public class MovieRepository {
    private MovieDao movieDao;

    public MovieRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieDao = database.movieDao();
    }

    public void insert(MovieEntity movieEntity) {
        new DBInsertOperationAsyncTask(movieDao).execute(movieEntity);
    }

    public void insertAll(MovieEntity... movieEntities) {
        new DBInsertAllOperationAsyncTask(movieDao).execute(movieEntities);
    }

    public void update(MovieEntity... movieEntities) {
        new DBUpdateOperationAsyncTask(movieDao).execute(movieEntities);
    }

    public void deleteAll(Date date) {
        new DBDeleteAllOperationAsyncTask(movieDao).execute(date);
    }



    private static class DBInsertOperationAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao dao;

        public DBInsertOperationAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            dao.insert(movieEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao dao;

        public DBInsertAllOperationAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            dao.insertAll(movieEntities);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao dao;

        public DBUpdateOperationAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            dao.update(movieEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Date, Void, Void> {
        private MovieDao dao;

        public DBDeleteAllOperationAsyncTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Date... dates) {
            dao.deleteAll(dates[0]);
            return null;
        }
    }
}
