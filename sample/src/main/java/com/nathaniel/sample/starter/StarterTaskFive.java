package com.nathaniel.sample.starter;

import android.util.Log;

import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class StarterTaskFive extends BaseStarterTask {

    @Override
    public void runTask() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(300);
        } catch (Exception e) {
        }
        Log.i("Task:", "TestAppStartTaskFive执行耗时: " + (System.currentTimeMillis() - start));
    }

    @Override
    public List<Class<? extends BaseStarterTask>> getDependsTaskList() {
        List<Class<? extends BaseStarterTask>> dependsTaskList = new ArrayList<>();
        dependsTaskList.add(StarterTaskThree.class);
        dependsTaskList.add(StarterTaskTwo.class);
        return dependsTaskList;
    }

    @Override
    public Executor runOnExecutor() {
        return super.runOnExecutor();
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }
}
