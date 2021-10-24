package com.nathaniel.utility;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Nathaniel
 * @date 2019/12/7 - 17:05
 */
public class ThreadManager {
    private static ThreadManager threadManager;
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadManager(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public static synchronized ThreadManager getInstance() {
        if (threadManager == null) {
            final int corePoolSize = Runtime.getRuntime().availableProcessors();
            final int maxPoolSize = Integer.MAX_VALUE;
            final long keepAliveTime = 5_000L;
            threadManager = new ThreadManager(corePoolSize, maxPoolSize, keepAliveTime);
        }
        return threadManager;
    }

    public void executor(Runnable runnable) {
        if (threadPoolExecutor == null) {
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        }
        threadPoolExecutor.execute(runnable);
    }
}
