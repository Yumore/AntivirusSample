package com.nathaniel.utility;

import androidx.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Nathaniel
 * @date 2019/12/7 - 17:05
 */
public class ThreadManager {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static ThreadManager threadManager;
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private ThreadPoolExecutor normalThreadPoolExecutor;
    private PriorityThreadPoolExecutor policyThreadPoolExecutor;

    private ThreadManager(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public static synchronized ThreadManager getInstance() {
        if (threadManager == null) {
            /*
            如果是CPU密集型应用，则线程池大小设置为N+1
            如果是IO密集型应用，则线程池大小设置为2N+1
             */
            final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
            final int maxPoolSize = Integer.MAX_VALUE / 2;
            final long keepAliveTime = 30_000L;
            threadManager = new ThreadManager(corePoolSize, maxPoolSize, keepAliveTime);
        }
        return threadManager;
    }

    private synchronized ThreadPoolExecutor initNormalExecutor() {
        if (normalThreadPoolExecutor == null || normalThreadPoolExecutor.isShutdown() || normalThreadPoolExecutor.isTerminated()) {
            ThreadFactory threadFactory = new CustomThreadFactory();
            // DiscardOldestPolicy
            RejectedExecutionHandler executionHandler = new ThreadPoolExecutor.DiscardPolicy();
            normalThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), threadFactory, executionHandler);
        }
        return normalThreadPoolExecutor;
    }

    private synchronized PriorityThreadPoolExecutor initPolicyExecutor() {
        if (policyThreadPoolExecutor == null || policyThreadPoolExecutor.isShutdown() || policyThreadPoolExecutor.isShutdown()) {
            ThreadFactory threadFactory = new CustomThreadFactory();
            RejectedExecutionHandler executionHandler = new ThreadPoolExecutor.AbortPolicy();
            policyThreadPoolExecutor = new PriorityThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), threadFactory, executionHandler);
        }
        return policyThreadPoolExecutor;
    }

    public void executor(@NonNull Runnable runnable) {
        initNormalExecutor().execute(runnable);
    }

    public void executor(@NonNull Runnable... runnableArray) {
        for (Runnable runnable : runnableArray) {
            initNormalExecutor().execute(runnable);
        }
    }

    public void submit(@NonNull Runnable runnable) {
        initNormalExecutor().submit(runnable);
    }

    public void remove(Runnable runnable) {
        initNormalExecutor().remove(runnable);
    }

    public void executor(@NonNull Runnable runnable, int priority) {
        initPolicyExecutor().execute(runnable, priority);
    }

    public <T> Future<T> submit(@NonNull Callable<T> callable, int priority) {
        return initPolicyExecutor().submit(callable, priority);
    }

    public void remove(@NonNull Runnable runnable, boolean priority) {
        if (!priority) {
            return;
        }
        initPolicyExecutor().remove(runnable);
    }
}
