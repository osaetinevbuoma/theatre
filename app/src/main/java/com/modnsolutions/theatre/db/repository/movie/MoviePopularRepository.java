package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MoviePopularDao;
import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviePopularRepository {
    private MoviePopularDao moviePopularDao;

    public MoviePopularRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        moviePopularDao = database.moviePopularDao();
    }

    public void insert(MoviePopularEntity moviePopularEntity) {
        new DBInsertOperationAsyncTask(moviePopularDao).execute(moviePopularEntity);
    }

    public void insertAll(MoviePopularEntity... moviePopularEntities) {
        new DBInsertAllOperationAsyncTask(moviePopularDao).execute(moviePopularEntities);
    }

    public void update(MoviePopularEntity... moviePopularEntities) {
        new DBUpdateOperationAsyncTask(moviePopularDao).execute(moviePopularEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(moviePopularDao).execute();
    }

    public MoviePopularEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return new DBFindMovieByIdOperationAsyncTask(moviePopularDao).execute(id).get();
    }
    
    public List<MoviePopularEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return new DBFindAllMoviesOperationAsyncTask(moviePopularDao).execute().get();
    }


    private static class DBInsertOperationAsyncTask extends AsyncTask<MoviePopularEntity, Void,
            Void> {
        private MoviePopularDao dao;

        public DBInsertOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MoviePopularEntity... moviePopularEntities) {
            dao.insert(moviePopularEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<MoviePopularEntity, Void,
            Void> {
        private MoviePopularDao dao;

        public DBInsertAllOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MoviePopularEntity... moviePopularEntities) {
            dao.insertAll(moviePopularEntities);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<MoviePopularEntity, Void,
            Void> {
        private MoviePopularDao dao;

        public DBUpdateOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MoviePopularEntity... moviePopularEntities) {
            dao.update(moviePopularEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private MoviePopularDao dao;

        public DBDeleteAllOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBFindMovieByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            MoviePopularEntity> {
        private MoviePopularDao dao;

        public DBFindMovieByIdOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected MoviePopularEntity doInBackground(Integer... integers) {
            return dao.findMovieById(integers[0]);
        }
    }
    
    private static class DBFindAllMoviesOperationAsyncTask extends AsyncTask<Void, Void, 
            List<MoviePopularEntity>> {
        private MoviePopularDao dao;

        public DBFindAllMoviesOperationAsyncTask(MoviePopularDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<MoviePopularEntity> doInBackground(Void... voids) {
            return dao.findAllMovies();
        }
    }
}
