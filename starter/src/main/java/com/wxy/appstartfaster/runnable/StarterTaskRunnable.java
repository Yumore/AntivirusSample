package com.wxy.appstartfaster.runnable;

import android.os.Process;

import com.wxy.appstartfaster.dispatcher.StarterTaskDispatcher;
import com.wxy.appstartfaster.task.BaseStarterTask;


public class StarterTaskRunnable implements Runnable {
    private final BaseStarterTask appStartTask;
    private final StarterTaskDispatcher appStartTaskDispatcher;

    public StarterTaskRunnable(BaseStarterTask appStartTask, StarterTaskDispatcher appStartTaskDispatcher) {
        this.appStartTask = appStartTask;
        this.appStartTaskDispatcher = appStartTaskDispatcher;
    }

    @Override
    public void run() {
        Process.setThreadPriority(appStartTask.priority());
        appStartTask.waitToNotify();
        appStartTask.runTask();
        appStartTaskDispatcher.setNotifyChildren(appStartTask);
        appStartTaskDispatcher.markAppStartTaskFinish(appStartTask);
    }
}
