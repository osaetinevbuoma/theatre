package com.modnsolutions.theatre.utils;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.modnsolutions.JsonHTTPService;
import com.modnsolutions.theatre.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    /**
     * Make http requests to Google Book API.
     * @param uri
     * @return
     * @throws JSONException
     */
    private static JSONObject httpService(@NonNull Uri uri) throws JSONException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Use a StringBuilder to hold the incoming response.
        StringBuilder builder = new StringBuilder();

        try {
            URL requestURL = new URL(uri.toString());

            // Open Connection
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get InputStream
            InputStream inputStream = urlConnection.getInputStream();

            // Create BufferedReader from the input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) return null; // Builder was empty.
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new JSONObject(builder.toString());
    }

    /**
     * Get now playing movies.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray moviesNowPlaying(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get popular movies.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray moviesPopular(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get top rated movies.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray moviesTopRated(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get upcoming movies.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray moviesUpcoming(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath("upcoming")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get details of movie
     *
     * @param movieId
     * @return
     */
    public static JSONObject movieDetails(int movieId) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        //return JsonHTTPService.get(builtUri.toString(), null, true);
        return httpService(builtUri);
    }

    /**
     * Get movie credits
     *
     * @param movieId
     * @return
     * @throws JSONException
     */
    public static JSONObject movieCredits(int movieId) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("credits")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get trailers of movies
     *
     * @param movieId
     * @return
     * @throws JSONException
     */
    public static JSONObject movieVideos(int movieId) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get recommended movies based on movie ID
     *
     * @param movieId
     * @return
     * @throws JSONException
     */
    public static JSONObject moviesRecommended(int movieId) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("recommendations")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get similar movies to movie ID
     *
     * @param movieId
     * @return
     * @throws JSONException
     */
    public static JSONObject moviesSimilar(int movieId) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("similar")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get tv shows airing today.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray tvShowsAiringToday(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath("airing_today")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get tv shows on the air.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray tvShowsOnTheAir(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath("on_the_air")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get popular tv shows.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray tvShowsPopular(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath("popular")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get top rated tv shows.
     *
     * @param page
     * @return
     * @throws JSONException
     */
    public static JSONArray tvShowsTopRated(int page) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath("top_rated")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        /*JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);*/
        JSONObject responseObj = httpService(builtUri);

        return responseObj != null && responseObj.has("results") ?
                responseObj.getJSONArray("results") : null;
    }

    /**
     * Get details of TV show
     *
     * @param id
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsDetails(int id) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get TV show cast and crew
     * @param id
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsCredits(int id) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("credits")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get first page of recommendations based on tv show
     *
     * @param id
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsRecommendations(int id) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("recommendations")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get first page of similar tv shows
     *
     * @param id
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsSimilar(int id) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("similar")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get trailers for tv show
     *
     * @param id
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsVideos(int id) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get details of season of tv show
     *
     * @param id
     * @param seasonNum
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsSeasonDetails(int id, int seasonNum) throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("season")
                .appendPath(String.valueOf(seasonNum))
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get details of episode of season of tv show
     *
     * @param id
     * @param seasonNum
     * @param episodeNum
     * @return
     * @throws JSONException
     */
    public static JSONObject tvShowsSeasonEpisodeDetails(int id, int seasonNum, int episodeNum)
            throws JSONException {
        Uri builtUri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("season")
                .appendPath(String.valueOf(seasonNum))
                .appendPath("episode")
                .appendPath(String.valueOf(episodeNum))
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        return httpService(builtUri);
    }

    /**
     * Get reviews of movies or tv shows
     *
     * @param id
     * @param page
     * @param isMovie
     * @return
     * @throws JSONException
     */
    public static JSONObject reviews(int id, int page, boolean isMovie) throws JSONException {
        Uri uri;

        if (isMovie) {
            uri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                    .appendPath("movie")
                    .appendPath(String.valueOf(id))
                    .appendPath("reviews")
                    .appendQueryParameter("api_key", BuildConfig.API_KEY)
                    .appendQueryParameter("page", String.valueOf(page))
                    .build();
        } else {
            uri = Uri.parse(BuildConfig.MOVIE_BASE_URL).buildUpon()
                    .appendPath("tv")
                    .appendPath(String.valueOf(id))
                    .appendPath("reviews")
                    .appendQueryParameter("api_key", BuildConfig.API_KEY)
                    .appendQueryParameter("page", String.valueOf(page))
                    .build();
        }

        return httpService(uri);
    }

}
