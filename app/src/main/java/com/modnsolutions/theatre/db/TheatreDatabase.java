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
import com.modnsolutions.theatre.db.dao.EpisodeDao;
import com.modnsolutions.theatre.db.dao.TheatreDao;
import com.modnsolutions.theatre.db.dao.SaveTypeHasTheatreDao;
import com.modnsolutions.theatre.db.dao.SeasonDao;
import com.modnsolutions.theatre.db.dao.TheatreSaveTypeDao;
import com.modnsolutions.theatre.db.dao.TheatreTypeDao;
import com.modnsolutions.theatre.db.dao.TrailerDao;
import com.modnsolutions.theatre.db.dao.WatchlistDao;
import com.modnsolutions.theatre.db.dao.movie.MovieNowPlayingDao;
import com.modnsolutions.theatre.db.entity.EpisodeEntity;
import com.modnsolutions.theatre.db.entity.SaveTypeHasTheatreEntity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.db.entity.TheatreSaveTypeEntity;
import com.modnsolutions.theatre.db.entity.TheatreTypeEntity;
import com.modnsolutions.theatre.db.entity.TrailerEntity;
import com.modnsolutions.theatre.db.entity.WatchlistEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;

@Database(entities = {TheatreTypeEntity.class, TheatreSaveTypeEntity.class, TheatreEntity.class,
        SaveTypeHasTheatreEntity.class, TrailerEntity.class, WatchlistEntity.class,
        SeasonEntity.class, EpisodeEntity.class, MovieNowPlayingEntity.class},
        version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class TheatreDatabase extends RoomDatabase {
    private static TheatreDatabase INSTANCE;

    public abstract MovieNowPlayingDao movieNowPlayingDao();
    public abstract TheatreTypeDao theatreTypeDao();
    public abstract TheatreSaveTypeDao theatreSaveTypeDao();
    public abstract SaveTypeHasTheatreDao saveTypeHasTheatreDao();
    public abstract TheatreDao favoriteDao();
    public abstract WatchlistDao watchlistDao();
    public abstract TrailerDao trailerDao();
    public abstract SeasonDao seasonDao();
    public abstract EpisodeDao episodeDao();

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
        private TheatreTypeDao theatreTypeDao;
        private TheatreSaveTypeDao theatreSaveTypeDao;
        private String[] theatreType = { "Movies", "TVShows" };
        private String[] theatreSaveType = {"Favorites", "Watchlist"};

        PopulateTablesAsyncTask(TheatreDatabase db) {
            theatreTypeDao = db.theatreTypeDao();
            theatreSaveTypeDao = db.theatreSaveTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Populate theatre type table
            if (theatreTypeDao.findAll().size() == 0) {
                for (String type : theatreType) {
                    theatreTypeDao.insert(new TheatreTypeEntity(type));
                }
            }

            if (theatreSaveTypeDao.findAll().size() == 0) {
                for (String type : theatreSaveType) {
                    theatreSaveTypeDao.insert(new TheatreSaveTypeEntity(type));
                }
            }

            return null;
        }
    }
}
