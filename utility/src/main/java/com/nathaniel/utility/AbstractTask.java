package com.nathaniel.utility;


import android.os.Handler;
import android.os.Looper;

/**
 * @author Nathaniel
 */
public abstract class AbstractTask<T> implements Runnable, OnRunnableCallback<T> {

    public AbstractTask() {
        prepareRunnable();
    }

    @Override
    public void run() {
        final T t = doRunnableCode();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                runnableCallback(t);
            }
        });
    }

    /**
     * θζΆζδ½
     *
     * @return T data
     */
    protected abstract T doRunnableCode();

    @Override
    public void prepareRunnable() {
        LoggerUtils.logger("prepareRunnable() has been called");
    }
}
