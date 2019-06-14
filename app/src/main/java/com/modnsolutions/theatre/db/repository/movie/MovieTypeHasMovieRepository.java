package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieTypeHasMovieDao;
import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeHasMovieEntity;

import java.util.List;

public class MovieTypeHasMovieRepository {
    private MovieTypeHasMovieDao movieTypeHasMovieDao;

    public MovieTypeHasMovieRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieTypeHasMovieDao = database.movieTypeHasMovieDao();
    }

    public void insert(MovieTypeHasMovieEntity movieTypeHasMovieEntity) {
        new InsertAsyncTask(movieTypeHasMovieDao).execute(movieTypeHasMovieEntity);
    }

    public LiveData<List<MovieEntity>> fetchMovieOfType(int id, int offset) {
        return movieTypeHasMovieDao.fetchMoviesOfType(id, offset);
    }



    private static class InsertAsyncTask extends AsyncTask<MovieTypeHasMovieEntity, Void, Void> {
        private MovieTypeHasMovieDao dao;

        InsertAsyncTask(MovieTypeHasMovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieTypeHasMovieEntity... movieTypeHasMovieEntities) {
            dao.insert(movieTypeHasMovieEntities[0]);
            return null;
        }
    }
}
