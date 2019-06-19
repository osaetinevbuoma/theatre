package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieTopRatedDao;
import com.modnsolutions.theatre.db.dao.movie.MovieUpcomingDao;
import com.modnsolutions.theatre.db.entity.movie.MovieUpcomingEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieUpcomingRepository {
    private MovieUpcomingDao movieUpcomingDao;

    public MovieUpcomingRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieUpcomingDao = database.movieUpcomingDao();
    }

    public void insert(MovieUpcomingEntity movieUpcomingEntity) {
        new DBInsertOperationAsyncTask(movieUpcomingDao).execute(movieUpcomingEntity);
    }

    public void insertAll(MovieUpcomingEntity... movieUpcomingEntities) {
        new DBInsertAllOperationAsyncTask(movieUpcomingDao).execute(movieUpcomingEntities);
    }

    public void update(MovieUpcomingEntity... movieUpcomingEntities) {
        new DBUpdateOperationAsyncTask(movieUpcomingDao).execute(movieUpcomingEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(movieUpcomingDao).execute();
    }

    public MovieUpcomingEntity findMovieById(int id) throws ExecutionException,
            InterruptedException {
        return new DBFindMovieByIdOperationAsyncTask(movieUpcomingDao).execute(id).get();
    }
    
    public List<MovieUpcomingEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return new DBFindAllMoviesOperationAsyncTask(movieUpcomingDao).execute().get();
    }


    private static class DBInsertOperationAsyncTask extends AsyncTask<MovieUpcomingEntity, Void,
            Void> {
        private MovieUpcomingDao dao;

        public DBInsertOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieUpcomingEntity... movieUpcomingEntities) {
            dao.insert(movieUpcomingEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<MovieUpcomingEntity, Void,
            Void> {
        private MovieUpcomingDao dao;

        public DBInsertAllOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieUpcomingEntity... movieUpcomingEntities) {
            dao.insertAll(movieUpcomingEntities);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<MovieUpcomingEntity, Void,
            Void> {
        private MovieUpcomingDao dao;

        public DBUpdateOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieUpcomingEntity... movieUpcomingEntities) {
            dao.update(movieUpcomingEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieUpcomingDao dao;

        public DBDeleteAllOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBFindMovieByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            MovieUpcomingEntity> {
        private MovieUpcomingDao dao;

        public DBFindMovieByIdOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected MovieUpcomingEntity doInBackground(Integer... integers) {
            return dao.findMovieById(integers[0]);
        }
    }
    
    private static class DBFindAllMoviesOperationAsyncTask extends AsyncTask<Void, Void, 
            List<MovieUpcomingEntity>> {
        private MovieUpcomingDao dao;

        public DBFindAllMoviesOperationAsyncTask(MovieUpcomingDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<MovieUpcomingEntity> doInBackground(Void... voids) {
            return dao.findAllMovies();
        }
    }
}
