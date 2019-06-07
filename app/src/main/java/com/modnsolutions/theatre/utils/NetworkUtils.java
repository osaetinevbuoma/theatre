package com.modnsolutions.theatre.utils;

import android.net.Uri;
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

}
