package com.nathaniel.utility;

import android.os.SystemClock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/8/15 - 20:38
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final Condition condition = reentrantLock.newCondition();
    private boolean hasPaused = false;

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    @Override
    public void beforeExecute(Thread thread, Runnable runnable) {
        reentrantLock.lock();
        super.beforeExecute(thread, runnable);
        try {
            while (hasPaused) {
                long milliseconds = 10L;
                LoggerUtils.logger(String.format("pausing, %s", thread.getName()));
                SystemClock.sleep(milliseconds);
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        reentrantLock.lock();
        LoggerUtils.logger("exe pause");
        hasPaused = true;
        reentrantLock.unlock();
    }

    /**
     * 继续执行
     */
    public void resume() {
        reentrantLock.lock();
        LoggerUtils.logger("un pause");
        hasPaused = false;
        condition.signalAll();
        reentrantLock.unlock();
    }
} 