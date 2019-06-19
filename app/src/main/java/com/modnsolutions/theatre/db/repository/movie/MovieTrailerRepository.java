package com.modnsolutions.theatre.db.repository.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieTrailerDao;
import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;

import java.util.List;

public class MovieTrailerRepository {
    private MovieTrailerDao movieTrailerDao;

    public MovieTrailerRepository(Application application) {
        TheatreDatabase database = TheatreDatabase.getInstance(application);
        movieTrailerDao = database.movieTrailerDao();
    }

    public void insert(MovieTrailerEntity movieTrailerEntity) {
        new InsertAsyncTask(movieTrailerDao, 0).execute(movieTrailerEntity);
    }

    public void insertAll(MovieTrailerEntity... movieTrailerEntities) {
        new InsertAsyncTask(movieTrailerDao, 1).execute(movieTrailerEntities);
    }

    public LiveData<List<MovieTrailerEntity>> fetchAllNowPlayingTrailers(int movieId) {
        return movieTrailerDao.fetchAllNowPlayingTrailers(movieId);
    }

    public LiveData<List<MovieTrailerEntity>> fetchAllPopularTrailers(int movieId) {
        return movieTrailerDao.fetchAllPopularTrailers(movieId);
    }

    public LiveData<List<MovieTrailerEntity>> fetchAllTopRatedTrailers(int movieId) {
        return movieTrailerDao.fetchAllTopRatedTrailers(movieId);
    }

    public LiveData<List<MovieTrailerEntity>> fetchAllUpcomingTrailers(int movieId) {
        return movieTrailerDao.fetchAllUpcomingTrailers(movieId);
    }




    private static class InsertAsyncTask extends AsyncTask<MovieTrailerEntity, Void, Void> {
        private MovieTrailerDao dao;
        private int insertTask;

        InsertAsyncTask(MovieTrailerDao dao, int insertTask) {
            this.dao = dao;
            this.insertTask = insertTask;
        }

        @Override
        protected Void doInBackground(MovieTrailerEntity... movieTrailerEntities) {
            if (insertTask == 0)
                dao.insert(movieTrailerEntities[0]);
            else if (insertTask == 1)
                dao.insertAll(movieTrailerEntities);
            return null;
        }
    }
}
