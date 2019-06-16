package com.modnsolutions.theatre.utils;

import com.modnsolutions.theatre.db.entity.movie.MovieEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;

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

    /**
     * Create test movie trailers
     * @param movieId
     * @return
     */
    public static MovieTrailerEntity createMovieTrailer(int movieId) {
        Random random = new Random();
        MovieTrailerEntity trailerEntity = new MovieTrailerEntity("id_" + random.nextLong(),
                movieId, "name_" + random.nextInt(), "key_" + random.nextInt(),
                new Date());
        return trailerEntity;
    }

    /**
     * Create test movie trailer of size ARRAY_LENGTH
     * @param movieId
     * @return
     */
    public static MovieTrailerEntity[] createMovieTrailers(int movieId) {
        MovieTrailerEntity[] movieTrailerEntities = new MovieTrailerEntity[ARRAY_LENGTH];
        int countdown = 1;
        while (countdown <= ARRAY_LENGTH) {
            movieTrailerEntities[countdown - 1] = createMovieTrailer(movieId);
            countdown++;
        }
        return movieTrailerEntities;
    }

    /**
     * Create a movie review
     * @param movieId
     * @return
     */
    public static MovieReviewEntity createMovieReview(int movieId) {
        Random random = new Random();
        return new MovieReviewEntity("id_"  + random.nextLong(), movieId, "author_" +
                random.nextInt(), "content_" + random.nextInt(), new Date());
    }

    /**
     * Create test movie reviews.
     * @param movieId
     * @return
     */
    public static MovieReviewEntity[] createMovieReviews(int movieId) {
        MovieReviewEntity[] movieReviewEntities = new MovieReviewEntity[ARRAY_LENGTH];
        int countdown = 1;
        while (countdown <= ARRAY_LENGTH) {
            movieReviewEntities[countdown - 1] = createMovieReview(movieId);
            countdown++;
        }
        return movieReviewEntities;
    }

    public static TVShowEntity createTVShowEntity(int tvShowTypeId) {
        Random random = new Random();

        return new TVShowEntity(random.nextInt(1001), tvShowTypeId, "backdropPath_" +
                random.nextInt(), "firstAirDate_" + random.nextInt(), "name_" +
                random.nextInt(), "originalName_" + random.nextInt(),
                "overview_" + random.nextInt(), "posterPath_" + random.nextInt(),
                random.nextInt(), new Date());
    }

    public static TVShowEntity[] createMultipleTVShowEntities(int tvShowTypeId) {
        TVShowEntity[] tvShowEntities = new TVShowEntity[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            tvShowEntities[i] = createTVShowEntity(tvShowTypeId);
        }
        return tvShowEntities;
    }

    public static TVShowTrailerEntity createTVShowTrailer(int tvShowId) {
        Random random = new Random();

        return new TVShowTrailerEntity("id_" + random.nextLong(), tvShowId, "name_" +
                random.nextInt(), "key_" + random.nextLong(), new Date());
    }

    public static TVShowTrailerEntity[] createMultipleTVShowTrailers(int tvShowId) {
        TVShowTrailerEntity[] tvShowTrailerEntities = new TVShowTrailerEntity[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            tvShowTrailerEntities[i] = createTVShowTrailer(tvShowId);
        }
        return tvShowTrailerEntities;
    }

    public static TVShowReviewEntity createTVShowReview(int tvShowId) {
        Random random = new Random();

        return new TVShowReviewEntity("id_" + random.nextLong(), tvShowId, "author_" +
                random.nextLong(), "content_" + random.nextLong(), new Date());
    }

    public static TVShowReviewEntity[] createMultipleTVShowReviews(int tvShowId) {
        TVShowReviewEntity[] tvShowReviewEntities = new TVShowReviewEntity[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            tvShowReviewEntities[i] = createTVShowReview(tvShowId);
        }
        return tvShowReviewEntities;
    }

    public static TVShowSeasonEntity createTVShowSeason(int tvShowId) {
        Random random = new Random();

        return new TVShowSeasonEntity(random.nextInt(1001), tvShowId, "airDate_" +
                random.nextLong(), random.nextInt(), "name_" + random.nextLong(),
                "overview_" + random.nextLong(), " posterPath_" +
                random.nextLong(), random.nextInt(), new Date());
    }

    public static TVShowSeasonEntity[] createMultipleTVShowSeasons(int tvShowId) {
        TVShowSeasonEntity[] tvShowSeasonEntities = new TVShowSeasonEntity[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            tvShowSeasonEntities[i] = createTVShowSeason(tvShowId);
        }
        return tvShowSeasonEntities;
    }

    public static TVShowEpisodeEntity createTVShowEpisode(int tvShowId, int tvShowSeasonId) {
        Random random = new Random();

        return new TVShowEpisodeEntity(random.nextInt(), tvShowSeasonId,
                "airDate_" + random.nextLong(), random.nextInt(), "name_" +
                random.nextLong(), "overview_" + random.nextLong(), "stillPath_" +
                random.nextLong(), random.nextInt(), new Date());
    }

    public static TVShowEpisodeEntity[] createMultipleTVShowEpisodes(int tvShowId, int tvShowSeasonId) {
        TVShowEpisodeEntity[] tvShowEpisodeEntities = new TVShowEpisodeEntity[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            tvShowEpisodeEntities[i] = createTVShowEpisode(tvShowId, tvShowSeasonId);
        }
        return tvShowEpisodeEntities;
    }
}
