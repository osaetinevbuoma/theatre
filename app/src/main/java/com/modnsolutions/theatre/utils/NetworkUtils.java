package com.modnsolutions.theatre.utils;

import android.net.Uri;
import android.util.Log;

import com.modnsolutions.JsonHTTPService;
import com.modnsolutions.theatre.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
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

        JSONObject responseObj = JsonHTTPService.get(builtUri.toString(), null,
                false);

        return responseObj.getJSONArray("results");
    }

}
