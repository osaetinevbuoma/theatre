package com.modnsolutions.theatre.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.modnsolutions.theatre.db.dao.favorite.FavoriteDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteEpisodeDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteSeasonDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTrailerDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTypeDao;
import com.modnsolutions.theatre.db.dao.movie.MovieDao;
import com.modnsolutions.theatre.db.dao.movie.MovieReviewDao;
import com.modnsolutions.theatre.db.dao.movie.MovieTrailerDao;
import com.modnsolutions.theatre.db.dao.movie.MovieTypeDao;
import com.modnsolutions.theatre.db.dao.movie.MovieTypeHasMovieDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowEpisodeDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowReviewDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowSeasonDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTrailerDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTypeDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTypeHasTVShowDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistEpisodeDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistSeasonDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistTrailerDao;
import com.modnsolutions.theatre.db.dao.watchlist.WatchlistTypeDao;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteEntity;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteEpisodeEntity;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteSeasonEntity;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteTrailerEntity;
import com.modnsolutions.theatre.db.entity.favorite.FavoriteTypeEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTypeHasMovieEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTypeHasTVShowEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEpisodeEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistSeasonEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTrailerEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTypeEntity;
import com.modnsolutions.theatre.enums.TVShowType;

@Database(entities = {MovieTypeEntity.class, MovieEntity.class, MovieTypeHasMovieEntity.class,
        MovieTrailerEntity.class, MovieReviewEntity.class, TVShowType.class, TVShowEntity.class,
        TVShowTypeHasTVShowEntity.class, TVShowTrailerEntity.class, TVShowSeasonEntity.class,
        TVShowEpisodeEntity.class, TVShowReviewEntity.class, FavoriteEpisodeEntity.class,
        FavoriteSeasonEntity.class, FavoriteTrailerEntity.class, FavoriteEntity.class,
        FavoriteTypeEntity.class, WatchlistEpisodeEntity.class, WatchlistEntity.class,
        WatchlistSeasonEntity.class, WatchlistTrailerEntity.class, WatchlistTypeEntity.class},
        version = 1, exportSchema = false)
public abstract class TheatreDatabase extends RoomDatabase {
    private static TheatreDatabase INSTANCE;

    public abstract MovieDao movieDao();
    public abstract MovieReviewDao movieReviewDao();
    public abstract MovieTrailerDao movieTrailerDao();
    public abstract MovieTypeDao movieTypeDao();
    public abstract MovieTypeHasMovieDao movieTypeHasMovieDao();
    public abstract TVShowDao tvShowDao();
    public abstract TVShowTypeDao tvShowTypeDao();
    public abstract TVShowTypeHasTVShowDao tvShowTypeHasTVShowDao();
    public abstract TVShowTrailerDao tvShowTrailerDao();
    public abstract TVShowSeasonDao tvShowSeasonDao();
    public abstract TVShowEpisodeDao tvShowEpisodeDao();
    public abstract TVShowReviewDao tvShowReviewDao();
    public abstract FavoriteEpisodeDao favoriteEpisodeDao();
    public abstract FavoriteSeasonDao favoriteSeasonDao();
    public abstract FavoriteTrailerDao favoriteTrailerDao();
    public abstract FavoriteDao favoriteDao();
    public abstract FavoriteTypeDao favoriteTypeDao();
    public abstract WatchlistEpisodeDao watchlistEpisodeDao();
    public abstract WatchlistDao watchlistDao();
    public abstract WatchlistSeasonDao watchlistSeasonDao();
    public abstract WatchlistTrailerDao watchlistTrailerDao();
    public abstract WatchlistTypeDao watchlistTypeDao();

    public static TheatreDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TheatreDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TheatreDatabase.class, "theatre")
                        .addCallback(populateTables)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback populateTables = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateTablesAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateTablesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieTypeDao movieTypeDao;
        private TVShowTypeDao tvShowTypeDao;
        private FavoriteTypeDao favoriteTypeDao;
        private WatchlistTypeDao watchlistTypeDao;

        private String[] movieTypes = { "Now Playing", "Popular", "Top Rated", "Upcoming" };
        private String[] tvShowTypes = { "Popular", "Top Rated", "Airing Today", "On The Air" };
        private String[] types = { "Movies", "TV Shows" };

        PopulateTablesAsyncTask(TheatreDatabase db) {
            movieTypeDao = db.movieTypeDao();
            tvShowTypeDao = db.tvShowTypeDao();
            favoriteTypeDao = db.favoriteTypeDao();
            watchlistTypeDao = db.watchlistTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Populate movie types table
            if (movieTypeDao.fetchAllMovieTypes().size() == 0) {
                for (String type : movieTypes) {
                    movieTypeDao.insert(new MovieTypeEntity(type));
                }
            }

            // Populate tvshow types table
            if (tvShowTypeDao.fetchAllTVShowTypes().size() == 0) {
                for (String type : tvShowTypes) {
                    tvShowTypeDao.insert(new TVShowTypeEntity(type));
                }
            }

            // Populate favorite type table
            if (favoriteTypeDao.fetchAllTypes().size() == 0) {
                for (String type : types) {
                    favoriteTypeDao.insert(new FavoriteTypeEntity(type));
                }
            }

            // Populate watchlist type table
            if (watchlistTypeDao.fetchAllTypes().size() == 0) {
                for (String type : types) {
                    watchlistTypeDao.insert(new WatchlistTypeEntity(type));
                }
            }

            return null;
        }
    }
}
