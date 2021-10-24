package com.wxy.appstartfaster.util;

import android.util.Log;

import com.wxy.appstartfaster.dispatcher.StarterTaskDispatcher;

public class AppStartTaskLogUtil {
    private static final String TAG = "AppStartTask: ";

    public static void showLog(String log) {
        if (StarterTaskDispatcher.getInstance().isShowLog()) {
            Log.e(TAG, log);
        }
    }
}
