package com.nathaniel.utility;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

/**
 * @author Nathaniel
 * @date 2018/5/29-10:17
 */
public final class LoggerUtils {
    public static final String TAG = LoggerUtils.class.getSimpleName();

    public static <T> void logger(T t) {
        logger(Level.DEBUG, t);
    }

    @SafeVarargs
    public static <T> void logger(T... ts) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ts == null || ts.length == 0) {
            stringBuilder.append("logger message is empty in ").append(TAG);
        } else {
            for (int i = 0; i < ts.length; i++) {
                stringBuilder.append(new Gson().toJson(ts[i]));
                if (i < ts.length - 1) {
                    stringBuilder.append(" - ");
                }
            }
        }
        logger(stringBuilder.toString());
    }

    public static <T> void logger(Level level, T t) {
        dealLogger(level, new Gson().toJson(t));
    }

    private static void dealLogger(@NonNull Level level, String message) {
        if (EmptyUtils.isEmpty(message)) {
            message = "logger message is empty in " + LoggerUtils.class.getSimpleName();
        }
        int length = message.length();
        int started = 0;
        int maxLength = 2000;
        int ending = maxLength;
        int passage = (message.length() + maxLength) / maxLength;
        for (int index = 0; index < passage; index++) {
            if (length > ending) {
                realLogged(TAG, level, message.substring(started, ending));
                started = ending;
                ending = ending + maxLength;
            } else {
                realLogged(TAG, level, message.substring(started, length));
                break;
            }
        }
    }

    private static void realLogged(@NonNull String tag, @NonNull Level level, @NonNull String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        switch (level) {
            case DEBUG:
                Log.d(tag, message);
                break;
            case WARING:
                Log.w(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case ERROR:
                Log.e(tag, message);
                break;
            case ASSERT:
            case VERBOSE:
            default:
                Log.v(tag, message);
                break;
        }
    }

    public enum Level {
        /**
         * 日志级别
         */
        INFO,
        DEBUG,
        WARING,
        ERROR,
        ASSERT,
        VERBOSE
    }
}
