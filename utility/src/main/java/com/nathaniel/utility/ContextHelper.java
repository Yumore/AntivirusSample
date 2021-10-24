package com.nathaniel.utility;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author Nathaniel
 * @datetime 2018/10/17-23:18
 */
public class ContextHelper {
    @SuppressLint("StaticFieldLeak")
    private static ContextHelper instance;
    private Context context;

    public static ContextHelper getInstance() {
        if (instance == null) {
            instance = new ContextHelper();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void initialize(Context context) {
        this.context = context;
    }
}
