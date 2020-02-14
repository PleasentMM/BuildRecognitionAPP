package com.imurluck.net.common;

import android.util.Log;
import com.imurluck.net.BuildConfig;

public class Logger {

    private static final boolean isDebug = true;

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG && isDebug) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG && isDebug) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG && isDebug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG && isDebug) {
            Log.e(tag, message);
        }
    }

}
