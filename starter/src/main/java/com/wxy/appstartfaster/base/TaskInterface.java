package com.wxy.appstartfaster.base;

import android.os.Process;

import androidx.annotation.IntRange;

import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.List;
import java.util.concurrent.Executor;


public interface TaskInterface {
    /**
     * 线程的优先级
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND, to = Process.THREAD_PRIORITY_LOWEST)
    int priority();

    /**
     * 执行任务所在的线程池
     */
    Executor runOnExecutor();

    /**
     * 所依赖的父亲们,父亲们执行完了，孩子才能执行
     */
    List<Class<? extends BaseStarterTask>> getDependsTaskList();

    /**
     * 在非主线程执行的Task是否需要在被调用await的时候等待，默认不需要，返回true即在Application的onCreate中阻塞，直到该任务执行完
     */
    boolean needWait();

}
