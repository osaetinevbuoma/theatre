package com.modnsolutions.theatre;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.modnsolutions.theatre.db.TheatreDatabase;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowEpisodeDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowReviewDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowSeasonDao;
import com.modnsolutions.theatre.db.dao.tvshow.TVShowTrailerDao;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowEpisodeEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowReviewEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowSeasonEntity;
import com.modnsolutions.theatre.db.entity.tvshow.TVShowTrailerEntity;
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
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TVShowDBOperationsTest {
    private TVShowDao tvShowDao;
    private TVShowEpisodeDao tvShowEpisodeDao;
    private TVShowSeasonDao tvShowSeasonDao;
    private TVShowTrailerDao tvShowTrailerDao;
    private TVShowReviewDao tvShowReviewDao;
    private TVShowTypeHasTVShowDao tvShowTypeHasTVShowDao;
    private TheatreDatabase database;
    private TVShowTypeEntity tvShowTypeEntity;

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TheatreDatabase.class).build();
        TVShowTypeDao tvShowTypeDao = database.tvShowTypeDao();
        tvShowDao = database.tvShowDao();
        tvShowEpisodeDao = database.tvShowEpisodeDao();
        tvShowSeasonDao = database.tvShowSeasonDao();
        tvShowTrailerDao = database.tvShowTrailerDao();
        tvShowTypeHasTVShowDao = database.tvShowTypeHasTVShowDao();
        tvShowReviewDao = database.tvShowReviewDao();

        String[] tvShowTypes = { "Popular", "Top Rated", "Airing Today", "On The Air" };
        for (String type : tvShowTypes) {
            tvShowTypeDao.insert(new TVShowTypeEntity(type));
        }
        tvShowTypeEntity = tvShowTypeDao.findTVShowTypeById(1);
    }

    @After
    public void closedDB() {
        database.close();
    }

    @Test
    public void testTVShowCreationAndInsertion() {
        TVShowEntity tvShowEntity = TestUtils.createTVShowEntity(tvShowTypeEntity.getId());
        tvShowDao.insert(tvShowEntity);
        assertThat(tvShowDao.findTVShowById(tvShowEntity.getId()).getName(), is(equalTo(tvShowEntity
                .getName())));
    }

    @Test
    public void testCreateMultipleTVShows() {
        TVShowEntity[] tvShowEntities = TestUtils.createMultipleTVShowEntities(tvShowTypeEntity.
                getId());
        tvShowDao.insertAll(tvShowEntities);

        List<TVShowEntity> tvShowEntityList = tvShowDao.findAllTVShows();
        assertThat(tvShowEntityList.size(), is(equalTo(tvShowEntities.length)));
    }

    @Test
    public void testUpdateTVShow() {
        TVShowEntity tvShowEntity = TestUtils.createTVShowEntity(tvShowTypeEntity.getId());
        tvShowDao.insert(tvShowEntity);

        Random random = new Random();
        tvShowEntity.setEpisodeRuntime(random.nextInt());
        tvShowEntity.setLastAirDate("lastAirDate_" + random.nextLong());
        tvShowEntity.setGenre("genre_" + random.nextLong());
        tvShowEntity.setWebsite("website_" + random.nextLong());
        tvShowEntity.setNumberOfEpisodes(random.nextInt());
        tvShowEntity.setNumberOfSeasons(random.nextInt());
        tvShowDao.update(tvShowEntity);

        assertThat(tvShowDao.findTVShowById(tvShowEntity.getId()).getEpisodeRuntime(), is(equalTo(
                tvShowEntity.getEpisodeRuntime())));
    }

    @Test
    public void testDeleteAllTVShows() {
        TVShowEntity[] tvShowEntities = TestUtils.createMultipleTVShowEntities(tvShowTypeEntity.
                getId());
        tvShowDao.insertAll(tvShowEntities);
        TVShowTypeHasTVShowEntity tvShowTypeHasTVShowEntity = new TVShowTypeHasTVShowEntity(
                tvShowTypeEntity.getId(), tvShowEntities[0].getId());
        tvShowTypeHasTVShowDao.insert(tvShowTypeHasTVShowEntity);
        TVShowTrailerEntity[] tvShowTrailerEntities = TestUtils.createMultipleTVShowTrailers(
                tvShowEntities[0].getId());
        tvShowTrailerDao.insertAll(tvShowTrailerEntities);
        TVShowReviewEntity[] tvShowReviewEntities = TestUtils.createMultipleTVShowReviews(
                tvShowEntities[0].getId());
        tvShowReviewDao.insertAll(tvShowReviewEntities);
        TVShowSeasonEntity[] tvShowSeasonEntities = TestUtils.createMultipleTVShowSeasons(
                tvShowEntities[0].getId());
        tvShowSeasonDao.insertAll(tvShowSeasonEntities);
        TVShowEpisodeEntity[] tvShowEpisodeEntities = TestUtils.createMultipleTVShowEpisodes(
                tvShowEntities[0].getId(), tvShowSeasonEntities[0].getId());
        tvShowEpisodeDao.insertAll(tvShowEpisodeEntities);


        tvShowDao.deleteAll(Utilities.expiryDate());
        assertThat(tvShowDao.findAllTVShows().size(), is(equalTo(0)));
        assertThat(tvShowTrailerDao.findAllTVShowTrailers(tvShowEntities[0].getId()).size(), is(
                equalTo(0)));
        assertThat(tvShowReviewDao.findAllTVShowReviews(tvShowEntities[0].getId()).size(), is(
                equalTo(0)));
        assertThat(tvShowSeasonDao.findAllTVShowSeasons(tvShowEntities[0].getId()).size(), is(
                equalTo(0)));
        assertThat(tvShowEpisodeDao.findAllSeasonEpisodes(tvShowEntities[0].getId()).size(), is(
                equalTo(0)));
    }
}
