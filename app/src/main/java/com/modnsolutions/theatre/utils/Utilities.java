package com.modnsolutions.theatre.utils;

import android.content.Context;
import android.content.Intent;

public class Utilities {

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

}
