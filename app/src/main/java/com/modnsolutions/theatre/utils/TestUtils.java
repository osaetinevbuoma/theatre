package com.modnsolutions.theatre.utils;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;

import java.util.Date;
import java.util.Random;

public class TestUtils {
    public static final int ARRAY_LENGTH = 10;

    /**
     * Create a random movie
     * @return a random movie of type MovieEntity
     */
    public static MovieEntity createMovie(int movieTypeId) {
        Random random = new Random();

        MovieEntity movieEntity = new MovieEntity(random.nextInt(1001), movieTypeId,
                "backdropPath_" + random.nextInt(), "overview_" + random.nextInt(),
                "posterPath_" + random.nextInt(), "releaseDate_" + random.nextInt(),
                "title_" + random.nextInt(), "originalTitle_" + random.nextInt(),
                random.nextInt(6), new Date());

        return movieEntity;
    }

    /**
     * Create an array of random movies of size ARRAY_LENGTH
     * @return an array of random movies
     */
    public static MovieEntity[] createMovies(int movieTypeId) {
        MovieEntity[] movieEntities = new MovieEntity[ARRAY_LENGTH];
        int countDown = 1;
        while (countDown <= ARRAY_LENGTH) {
            movieEntities[countDown - 1] = createMovie(movieTypeId);
            countDown++;
        }

        return movieEntities;
    }
}
