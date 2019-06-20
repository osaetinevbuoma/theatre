package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieNowPlayingDao;
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieNowPlayingRepository {
    private MovieNowPlayingDao movieNowPlayingDao;

    public MovieNowPlayingRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieNowPlayingDao = database.movieNowPlayingDao();
    }

    public void insert(MovieNowPlayingEntity movieNowPlayingEntity) {
        new DBInsertOperationAsyncTask(movieNowPlayingDao).execute(movieNowPlayingEntity);
    }

    public void insertAll(MovieNowPlayingEntity... nowPlayingEntities) {
        new DBInsertAllOperationAsyncTask(movieNowPlayingDao).execute(nowPlayingEntities);
    }

    public void update(MovieNowPlayingEntity... nowPlayingEntities) {
        new DBUpdateOperationAsyncTask(movieNowPlayingDao).execute(nowPlayingEntities);
    }

    public void deleteAll() {
        new DBDeleteAllOperationAsyncTask(movieNowPlayingDao).execute();
    }

    public LiveData<MovieNowPlayingEntity> findMovieById(int id) {
        return movieNowPlayingDao.findMovieById(id);
        /*return new DBFindMovieByIdOperationAsyncTask(movieNowPlayingDao).execute(id).get();*/
    }

    public MovieNowPlayingEntity findMovieByIdWithoutLiveData(int id) {
        MovieNowPlayingEntity entity = null;
        try {
            entity = new DBFindMovieByIdOperationAsyncTask(movieNowPlayingDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public List<MovieNowPlayingEntity> findAllMovies() throws ExecutionException,
            InterruptedException {
        return new DBFindAllMoviesOperationAsyncTask(movieNowPlayingDao).execute().get();
    }


    private static class DBInsertOperationAsyncTask extends AsyncTask<MovieNowPlayingEntity, Void,
            Void> {
        private MovieNowPlayingDao dao;

        public DBInsertOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieNowPlayingEntity... nowPlayingEntities) {
            dao.insert(nowPlayingEntities[0]);
            return null;
        }
    }

    private static class DBInsertAllOperationAsyncTask extends AsyncTask<MovieNowPlayingEntity, Void,
            Void> {
        private MovieNowPlayingDao dao;

        public DBInsertAllOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieNowPlayingEntity... nowPlayingEntities) {
            dao.insertAll(nowPlayingEntities);
            return null;
        }
    }

    private static class DBUpdateOperationAsyncTask extends AsyncTask<MovieNowPlayingEntity, Void,
            Void> {
        private MovieNowPlayingDao dao;

        public DBUpdateOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieNowPlayingEntity... nowPlayingEntities) {
            dao.update(nowPlayingEntities);
            return null;
        }
    }

    private static class DBDeleteAllOperationAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieNowPlayingDao dao;

        public DBDeleteAllOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DBFindMovieByIdOperationAsyncTask extends AsyncTask<Integer, Void,
            MovieNowPlayingEntity> {
        private MovieNowPlayingDao dao;

        public DBFindMovieByIdOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected MovieNowPlayingEntity doInBackground(Integer... integers) {
            return dao.findMovieByIdWithoutLiveData(integers[0]);
        }
    }

    private static class DBFindAllMoviesOperationAsyncTask extends AsyncTask<Void, Void,
            List<MovieNowPlayingEntity>> {
        private MovieNowPlayingDao dao;

        public DBFindAllMoviesOperationAsyncTask(MovieNowPlayingDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<MovieNowPlayingEntity> doInBackground(Void... voids) {
            return dao.findAllMovies();
        }
    }
}
