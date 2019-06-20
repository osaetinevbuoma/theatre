package com.modnsolutions.theatre.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Utilities {
    private static WeakReference<RecyclerView> mRecyclerView;
    private static WeakReference<ProgressBar> mLoading;
    private static int mCurrentPage;

    /**
     * Utilities function to start an activity
     *
     * @param context
     * @param cls
     */
    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * To avoid duplication of code in movie fragments, this method creates the views in the
     * inflated layout and loads movies from server. Scrolling down to bottom of Recycler View loads
     * more movies.
     *
     * @param rootView Inflated fragment layout.
     */
    /*public static void onMoviesCreateView(View rootView, final MovieType movieType) {
        int page = 1;
        mCurrentPage = page;
        mLoading = new WeakReference<>((ProgressBar) rootView.findViewById(R.id.loading));
        mRecyclerView = new WeakReference<>((RecyclerView) rootView.findViewById(R.id.recyclerview));
        mAdapter = new WeakReference<>(new MovieAdapter(rootView.getContext()));
        mRecyclerView.get().setAdapter(mAdapter.get());
        mRecyclerView.get().setLayoutManager(new GridLayoutManager(rootView.getContext(),
                3));

        // TODO: Check internet connectivity if fetching from remote server.
        new FetchMoviesAsyncTask(mLoading.get(), mAdapter.get(), movieType).execute(mCurrentPage);

        mRecyclerView.get().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(recyclerView, movieType);
            }
        });
    }*/

    /**
     * Load more movies from DB/remote server
     * @param recyclerView
     */
    /*private static void loadMore(RecyclerView recyclerView, MovieType movieType) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mAdapter.get().getItemCount() - 1) {
            mLoading.get().setVisibility(View.VISIBLE);
            mCurrentPage += 1;
            new FetchMoviesAsyncTask(mLoading.get(), mAdapter.get(), movieType)
                    .execute(mCurrentPage);
            mRecyclerView.get().scrollToPosition(lastPosition + 1);
        }
    }*/

    /**
     * Check network connection.
     *
     * @param context
     * @return
     */
    public static boolean checkInternetConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Display toast message
     *
     * @param context
     * @param message
     */
    public static void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Extract director name from array
     * @param array
     * @return
     * @throws JSONException
     */
    public static String extractDirectorNameFromArray(JSONArray array) throws JSONException {
        // Get director from crew array
        String directorName = null;
        for (int i = 0; i < array.length(); i++) {
            JSONObject crew = (JSONObject) array.get(i);
            if (crew.getString("job").equals("Director")) {
                directorName = crew.getString("name");
                break;
            }
        }

        return directorName;
    }

    /**
     * Extra movie genre and cast from array
     *
     * @param array JSONArray of authors.
     * @return String of authors.
     */
    public static String extractNamesFromArray(@NonNull JSONArray array) throws JSONException {
        // Format authors into a continuous string value.
        StringBuilder arrayBuilder = new StringBuilder();
        for (int j = 0; j < array.length(); j++) {
            JSONObject jsonObject = array.getJSONObject(j);

            if (array.length() > 1 && j != array.length() - 1) {
                arrayBuilder.append(jsonObject.getString("name") + ", ");
            }

            if (array.length() == 1 || j == array.length() - 1) {
                arrayBuilder.append(jsonObject.getString("name"));
            }
        }

        return arrayBuilder.toString();
    }

    /**
     * Extra year from date string
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static String formatDate(String dateString) throws ParseException {
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
                "Nov", "Dec" };

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
    }

    /**
     * Convert minutes to string time.
     *
     * @param minutes
     * @return
     */
    public static String convertMinutesToStringTime(int minutes) {
        StringBuilder runtimeString = new StringBuilder();

        int hrs = (int) Math.floor(minutes / 60f);
        int mins = minutes - (hrs * 60);

        if (hrs > 1) runtimeString.append(hrs + " hrs ");
        else if (hrs == 1) runtimeString.append(hrs + " hr ");

        if (mins > 1) runtimeString.append(mins + " mins");
        else if (mins == 1) runtimeString.append(mins + " min");

        return runtimeString.toString();
    }

    /**
     * Format numbers
     *
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.format(number);
    }

    /**
     * Return expiry date to re-download movies and tv shows which is 24 hours
     * from download date.
     *
     * @return
     */
    public static Date expiryDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 24);
        return calendar.getTime();
    }

    /**
     * Convert json array to list of json objects.
     * @param array
     * @return
     */
    public static List<JSONObject> convertJSONArrayToList(JSONArray array) {
        List<JSONObject> list = new LinkedList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

}
