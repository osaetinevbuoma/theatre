package com.modnsolutions.theatre;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.movie.MovieReviewDao;
import com.modnsolutions.theatre.db.dao.movie.MovieTrailerDao;
import com.modnsolutions.theatre.db.entity.movie.MovieReviewEntity;
import com.modnsolutions.theatre.db.entity.movie.MovieTrailerEntity;
import com.modnsolutions.theatre.utils.TestUtils;
import com.modnsolutions.theatre.utils.Utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MovieDBOperationsTest {
    private MovieDao movieDao;
    private MovieReviewDao movieReviewDao;
    private MovieTrailerDao movieTrailerDao;
    private MovieTypeHasMovieDao movieTypeHasMovieDao;
    private TheatreDatabase database;
    private MovieTypeEntity movieTypeEntity;

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TheatreDatabase.class).build();
        MovieTypeDao movieTypeDao = database.movieTypeDao();
        movieDao = database.movieDao();
        movieReviewDao = database.movieReviewDao();
        movieTrailerDao = database.movieTrailerDao();
        movieTypeHasMovieDao = database.movieTypeHasMovieDao();

        String[] movieTypes = { "Now Playing", "Popular", "Top Rated", "Upcoming" };
        for (String type : movieTypes) {
            movieTypeDao.insert(new MovieTypeEntity(type));
        }
        movieTypeEntity = movieTypeDao.findMovieTypeById(1);
    }

    @After
    public void closeDB() {
        database.close();
    }

    @Test
    public void testMovieCreationAndInsertion() {
        MovieEntity movieEntity = TestUtils.createMovie(movieTypeEntity.getId());
        movieDao.insert(movieEntity);
        assertThat(movieDao.findMovieById(movieEntity.getId()).getTitle(),
                is(equalTo(movieEntity.getTitle())));
    }

    @Test
    public void testMultipleMovieCreationAndInsertion() {
        MovieEntity[] movieEntities = TestUtils.createMovies(movieTypeEntity.getId());
        movieDao.insertAll(movieEntities);

        List<MovieEntity> movieEntityList = movieDao.findAllMovies();
        assertThat(movieEntityList.size(), is(equalTo(movieEntities.length)));
    }

    @Test
    public void testMovieUpdate() {
        Random random = new Random();
        long randomLong = random.nextLong();
        int randomInt = random.nextInt();

        MovieEntity movieEntity = TestUtils.createMovie(movieTypeEntity.getId());
        movieDao.insert(movieEntity);

        movieEntity.setBudget(randomLong);
        movieEntity.setGenre("genre_" + randomInt);
        movieEntity.setWebsite("website_" + randomInt);
        movieEntity.setRevenue(randomLong);
        movieEntity.setRuntime(randomInt);
        movieEntity.setDirector("director_" + randomInt);
        movieEntity.setCast("cast_" + randomInt);

        movieDao.update(movieEntity);

        MovieEntity movie = movieDao.findMovieById(movieEntity.getId());
        assertThat(movie.getBudget(), is(equalTo(movieEntity.getBudget())));
        assertThat(movie.getGenre(), is(equalTo(movieEntity.getGenre())));
        assertThat(movie.getWebsite(), is(equalTo(movieEntity.getWebsite())));
        assertThat(movie.getRevenue(), is(equalTo(movieEntity.getRevenue())));
        assertThat(movie.getRuntime(), is(equalTo(movieEntity.getRuntime())));
        assertThat(movie.getDirector(), is(equalTo(movieEntity.getDirector())));
        assertThat(movie.getCast(), is(equalTo(movieEntity.getCast())));
    }

    @Test
    public void testJoinBetweenMovieTypeAndMovie() {
        MovieEntity movieEntity = TestUtils.createMovie(movieTypeEntity.getId());
        movieDao.insert(movieEntity);
        MovieTypeHasMovieEntity movieTypeHasMovieEntity = new MovieTypeHasMovieEntity(
                movieTypeEntity.getId(), movieEntity.getId());
        movieTypeHasMovieDao.insert(movieTypeHasMovieEntity);

        List<MovieEntity> joins = movieTypeHasMovieDao.fetchTestMoviesOfType(movieTypeEntity.getId(),
                0);
        assertThat(movieEntity.getTitle(), is(equalTo(joins.get(0).getTitle())));
    }

    @Test
    public void testMovieTrailerDao() {
        MovieEntity movieEntity = TestUtils.createMovie(movieTypeEntity.getId());
        movieDao.insert(movieEntity);

        MovieTrailerEntity movieTrailerEntity = TestUtils.createMovieTrailer(movieEntity.getId());
        movieTrailerDao.insert(movieTrailerEntity);
        List<MovieTrailerEntity> retrievedTrailer = movieTrailerDao.fetchTestMovieTrailers(
                movieEntity.getId());
        assertThat(retrievedTrailer.size(), is(equalTo(1)));
        assertThat(movieTrailerEntity.getName(), is(equalTo(retrievedTrailer.get(0).getName())));

        MovieTrailerEntity[] movieTrailerEntities = TestUtils.createMovieTrailers(movieEntity.
                getId());
        movieTrailerDao.insertAll(movieTrailerEntities);
        List<MovieTrailerEntity> retrievedTrailers = movieTrailerDao.fetchTestMovieTrailers(
                movieEntity.getId());
        assertThat(retrievedTrailers.size(), is(equalTo(retrievedTrailer.size() +
                movieTrailerEntities.length)));
    }

    @Test
    public void testMovieReviewDao() {
        MovieEntity movieEntity = TestUtils.createMovie(movieTypeEntity.getId());
        movieDao.insert(movieEntity);

        MovieReviewEntity movieReviewEntity = TestUtils.createMovieReview(movieEntity.getId());
        movieReviewDao.insert(movieReviewEntity);
        List<MovieReviewEntity> retrievedReview = movieReviewDao.fetchTestMovieReviews(movieEntity
                .getId());
        assertThat(retrievedReview.size(), is(equalTo(1)));
        assertThat(movieReviewEntity.getAuthor(), is(equalTo(retrievedReview.get(0).getAuthor())));

        MovieReviewEntity[] movieReviewEntities = TestUtils.createMovieReviews(movieEntity.getId());
        movieReviewDao.insertAll(movieReviewEntities);
        List<MovieReviewEntity> retrievedReviews = movieReviewDao.fetchTestMovieReviews(movieEntity
                .getId());
        assertThat(retrievedReviews.size(), is(equalTo(retrievedReview.size() +
                movieReviewEntities.length)));
    }

    @Test
    public void testDeleteAllMovies() {
        movieDao.deleteAll(Utilities.expiryDate());
        assertThat(movieDao.findAllMovies().size(), is(equalTo(0)));
    }
}
