package com.wxy.appstartfaster.task;

import android.os.Process;

import com.wxy.appstartfaster.base.TaskInterface;
import com.wxy.appstartfaster.executor.TaskExceutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class BaseStarterTask implements TaskInterface {

    /**
     * 当前Task依赖的Task数量（等父亲们执行完了，孩子才能执行），默认没有依赖
     */
    private final CountDownLatch countDownLatch = new CountDownLatch(getDependsTaskList() == null ? 0 : getDependsTaskList().size());

    /**
     * 当前Task等待，让父亲Task先执行
     */
    public void waitToNotify() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    /**
     * 执行任务代码
     */
    public abstract void runTask();

    /**
     * 他的父亲们执行完了一个
     */
    public void notifyNext() {
        countDownLatch.countDown();
    }

    @Override
    public Executor runOnExecutor() {
        return TaskExceutorManager.getInstance().getIOThreadPoolExecutor();
    }

    @Override
    public List<Class<? extends BaseStarterTask>> getDependsTaskList() {
        return null;
    }

    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 是否在主线程执行
     *
     * @return true|false
     */
    public abstract boolean runOnMainThread();

}
