package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieTopRatedDao;
import com.modnsolutions.theatre.db.entity.movie.MovieTopRatedEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieTopRatedRepository {
    private MovieTopRatedDao movieTopRatedDao;

    public MovieTopRatedRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieTopRatedDao = database.movieTopRatedDao();
    }

    public void insert(MovieTopRatedEntity movieTopRatedEntity) {
        new DBInsertOperationAsyncTask(movieTopRatedDao).execute(movieTopRatedEntity);
    }

    public void insertAll(MovieTopRatedEntity... movieTopRatedEntities) {
        new DBInsertAllOperationAsyncTask(movieTopRatedDao).execute(movieTopRatedEntities);
    }

    public void update(MovieTopRatedEntity... movieTopRatedEntities) {
        new DBUpdateOperationAsyncTask(movieTopRatedDao).execute(movieTopRatedEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(movieTopRatedDao).execute();
    }

    public MovieTopRatedEntity findMovieById(int id) throws ExecutionException, 
            InterruptedException {
        return new DBFindMovieByIdOperationAsyncTask(movieTopRatedDao).execute(id).get();
    }
    
    public List<MovieTopRatedEntity> findAllMovies() throws ExecutionException, 
            InterruptedException {
        return new DBFindAllMoviesOperationAsyncTask(movieTopRatedDao).execute().get();
    }


    private static class DBInsertOperationAsyncTask extends AsyncTask<MovieTopRatedEntity, Void,
            Void> {
        private MovieTopRatedDao dao;

        public DBInsertOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieTopRatedEntity... movieTopRatedEntities) {
            dao.insert(movieTopRatedEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<MovieTopRatedEntity, Void,
            Void> {
        private MovieTopRatedDao dao;

        public DBInsertAllOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieTopRatedEntity... movieTopRatedEntities) {
            dao.insertAll(movieTopRatedEntities);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<MovieTopRatedEntity, Void, 
            Void> {
        private MovieTopRatedDao dao;

        public DBUpdateOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieTopRatedEntity... movieTopRatedEntities) {
            dao.update(movieTopRatedEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieTopRatedDao dao;

        public DBDeleteAllOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBFindMovieByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            MovieTopRatedEntity> {
        private MovieTopRatedDao dao;

        public DBFindMovieByIdOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected MovieTopRatedEntity doInBackground(Integer... integers) {
            return dao.findMovieById(integers[0]);
        }
    }
    
    private static class DBFindAllMoviesOperationAsyncTask extends AsyncTask<Void, Void, 
            List<MovieTopRatedEntity>> {
        private MovieTopRatedDao dao;

        public DBFindAllMoviesOperationAsyncTask(MovieTopRatedDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<MovieTopRatedEntity> doInBackground(Void... voids) {
            return dao.findAllMovies();
        }
    }
}
