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
        new DBOperationAsyncTask(movieDao, 0).execute(movieEntity);
    }

    public void insertAll(MovieEntity... movieEntities) {
        new DBOperationAsyncTask(movieDao, 1).execute(movieEntities);
    }

    public void update(MovieEntity... movieEntities) {
        new DBOperationAsyncTask(movieDao, 2).execute(movieEntities);
    }

    public void deleteAll(String date) {
        new DBOperationAsyncTask(movieDao, 3).execute(date);
    }



    private static class DBOperationAsyncTask extends AsyncTask<Object, Void, Void> {
        private MovieDao dao;
        private int operation;

        public DBOperationAsyncTask(MovieDao dao, int operation) {
            this.dao = dao;
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            switch (operation) {
                case 0:
                    dao.insert((MovieEntity) objects[0]);
                    break;

                case 1:
                    dao.insertAll((MovieEntity[]) objects);
                    break;

                case 2:
                    dao.update((MovieEntity[]) objects);
                    break;

                case 3:
                    dao.deleteAll((String) objects[0]);
                    break;
            }

            return null;
        }
    }
}
