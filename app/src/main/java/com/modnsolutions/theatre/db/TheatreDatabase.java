package com.modnsolutions.theatre.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.modnsolutions.theatre.db.converter.DateConverter;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteEpisodeDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteSeasonDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTrailerDao;
import com.modnsolutions.theatre.db.dao.favorite.FavoriteTypeDao;
import com.modnsolutions.theatre.db.dao.movie.MovieNowPlayingDao;
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
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistEpisodeEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistSeasonEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTrailerEntity;
import com.modnsolutions.theatre.db.entity.watchlist.WatchlistTypeEntity;

@Database(entities = {MovieNowPlayingEntity.class, FavoriteEpisodeEntity.class,
        FavoriteSeasonEntity.class, FavoriteTrailerEntity.class, FavoriteEntity.class,
        FavoriteTypeEntity.class, WatchlistEpisodeEntity.class, WatchlistEntity.class,
        WatchlistSeasonEntity.class, WatchlistTrailerEntity.class, WatchlistTypeEntity.class},
        version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class TheatreDatabase extends RoomDatabase {
    private static TheatreDatabase INSTANCE;

    public abstract MovieNowPlayingDao movieNowPlayingDao();
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
        private FavoriteTypeDao favoriteTypeDao;
        private WatchlistTypeDao watchlistTypeDao;

        private String[] types = { "Movies", "TV Shows" };

        PopulateTablesAsyncTask(TheatreDatabase db) {
            favoriteTypeDao = db.favoriteTypeDao();
            watchlistTypeDao = db.watchlistTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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
