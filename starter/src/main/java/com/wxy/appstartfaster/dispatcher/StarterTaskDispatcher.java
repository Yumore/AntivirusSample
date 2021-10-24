package com.wxy.appstartfaster.dispatcher;

import android.content.Context;
import android.os.Looper;

import com.wxy.appstartfaster.runnable.StarterTaskRunnable;
import com.wxy.appstartfaster.task.BaseStarterTask;
import com.wxy.appstartfaster.util.AppStartTaskLogUtil;
import com.wxy.appstartfaster.util.AppStartTaskSortUtil;
import com.wxy.appstartfaster.util.ProcessUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StarterTaskDispatcher {
    //所有任务需要等待的时间
    private static final int WAITING_TIME = 10000;
    private static volatile StarterTaskDispatcher sAppStartTaskDispatcher;
    //存放每个Task  （key= Class < ? extends AppStartTask>）
    private final HashMap<Class<? extends BaseStarterTask>, BaseStarterTask> mTaskHashMap;
    //每个Task的孩子 （key= Class < ? extends AppStartTask>）
    private final HashMap<Class<? extends BaseStarterTask>, List<Class<? extends BaseStarterTask>>> mTaskChildHashMap;
    //通过Add添加进来的所有任务
    private final List<BaseStarterTask> mStartTaskList;
    //拓扑排序后的主线程的任务
    private final List<BaseStarterTask> mSortMainThreadTaskList;
    //拓扑排序后的子线程的任务
    private final List<BaseStarterTask> mSortThreadPoolTaskList;
    //需要等待的任务总数，用于CountDownLatch
    private final AtomicInteger mNeedWaitCount;
    private Context mContext;
    //是否在主进程
    private boolean isInMainProgress;
    //拓扑排序后的所有任务
    private List<BaseStarterTask> mSortTaskList;
    //需要等待的任务总数，用于阻塞
    private CountDownLatch mCountDownLatch;
    //所有的任务开始时间，结束时间
    private long mStartTime, mFinishTime;
    //所有阻塞任务的总超时时间
    private long mAllTaskWaitTimeOut;
    private boolean isShowLog;

    private StarterTaskDispatcher() {
        mTaskHashMap = new HashMap<>();
        mTaskChildHashMap = new HashMap<>();
        mStartTaskList = new ArrayList<>();
        mNeedWaitCount = new AtomicInteger();
        mSortMainThreadTaskList = new ArrayList<>();
        mSortThreadPoolTaskList = new ArrayList<>();
    }

    public static StarterTaskDispatcher getInstance() {
        if (sAppStartTaskDispatcher == null) {
            synchronized (StarterTaskDispatcher.class) {
                if (sAppStartTaskDispatcher == null) {
                    sAppStartTaskDispatcher = new StarterTaskDispatcher();
                }
            }
        }
        return sAppStartTaskDispatcher;
    }

    public StarterTaskDispatcher setAllTaskWaitTimeOut(long allTaskWaitTimeOut) {
        mAllTaskWaitTimeOut = allTaskWaitTimeOut;
        return this;
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public StarterTaskDispatcher setShowLog(boolean showLog) {
        isShowLog = showLog;
        return this;
    }

    public StarterTaskDispatcher setContext(Context context) {
        mContext = context;
        isInMainProgress = ProcessUtil.isMainProcess(mContext);
        return this;
    }

    public StarterTaskDispatcher addAppStartTask(BaseStarterTask appStartTask) {
        if (appStartTask == null) {
            throw new RuntimeException("addAppStartTask() 传入的appStartTask为null");
        }
        mStartTaskList.add(appStartTask);
        if (ifNeedWait(appStartTask)) {
            mNeedWaitCount.getAndIncrement();
        }
        return this;
    }

    public StarterTaskDispatcher start() {
        if (mContext == null) {
            throw new RuntimeException("context为null，调用start()方法前必须调用setContext()方法");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("start方法必须在主线程调用");
        }
        if (!isInMainProgress) {
            AppStartTaskLogUtil.showLog("当前进程非主进程");
            return this;
        }
        mStartTime = System.currentTimeMillis();
        //拓扑排序，拿到排好序之后的任务队列
        mSortTaskList = AppStartTaskSortUtil.getSortResult(mStartTaskList, mTaskHashMap, mTaskChildHashMap);
        initRealSortTask();
        printSortTask();
        mCountDownLatch = new CountDownLatch(mNeedWaitCount.get());
        dispatchAppStartTask();
        return this;
    }

    //分别处理主线程和子线程的任务
    private void initRealSortTask() {
        for (BaseStarterTask appStartTask : mSortTaskList) {
            if (appStartTask.runOnMainThread()) {
                mSortMainThreadTaskList.add(appStartTask);
            } else {
                mSortThreadPoolTaskList.add(appStartTask);
            }
        }
    }

    //输出排好序的Task
    private void printSortTask() {
        StringBuilder sb = new StringBuilder();
        sb.append("当前所有任务排好的顺序为：");
        for (int i = 0; i < mSortTaskList.size(); i++) {
            String taskName = mSortTaskList.get(i).getClass().getSimpleName();
            if (i == 0) {
                sb.append(taskName);
            } else {
                sb.append("---＞");
                sb.append(taskName);
            }
        }
        AppStartTaskLogUtil.showLog(sb.toString());
    }

    //发送任务
    private void dispatchAppStartTask() {
        //再发送非主线程的任务
        for (BaseStarterTask appStartTask : mSortThreadPoolTaskList) {
            if (!appStartTask.runOnMainThread()) {
                appStartTask.runOnExecutor().execute(new StarterTaskRunnable(appStartTask, this));
            }
        }
        //再发送主线程的任务，防止主线程任务阻塞，导致子线程任务不能立刻执行
        for (BaseStarterTask appStartTask : mSortMainThreadTaskList) {
            if (appStartTask.runOnMainThread()) {
                new StarterTaskRunnable(appStartTask, this).run();
            }
        }
    }

    //通知Children一个前置任务已完成
    public void setNotifyChildren(BaseStarterTask appStartTask) {
        List<Class<? extends BaseStarterTask>> arrayList = mTaskChildHashMap.get(appStartTask.getClass());
        if (arrayList != null && arrayList.size() > 0) {
            for (Class<? extends BaseStarterTask> aclass : arrayList) {
                mTaskHashMap.get(aclass).notifyNext();
            }
        }
    }

    //标记已经完成的Task
    public void markAppStartTaskFinish(BaseStarterTask appStartTask) {
        AppStartTaskLogUtil.showLog("任务完成了：" + appStartTask.getClass().getSimpleName());
        if (ifNeedWait(appStartTask)) {
            mCountDownLatch.countDown();
            mNeedWaitCount.getAndDecrement();
        }
    }

    //是否需要等待，主线程的任务本来就是阻塞的，所以不用管
    private boolean ifNeedWait(BaseStarterTask task) {
        return !task.runOnMainThread() && task.needWait();
    }

    //等待，阻塞主线程
    public void await() {
        try {
            if (mCountDownLatch == null) {
                throw new RuntimeException("在调用await()之前，必须先调用start()");
            }
            if (mAllTaskWaitTimeOut == 0) {
                mAllTaskWaitTimeOut = WAITING_TIME;
            }
            mCountDownLatch.await(mAllTaskWaitTimeOut, TimeUnit.MILLISECONDS);
            mFinishTime = System.currentTimeMillis() - mStartTime;
            AppStartTaskLogUtil.showLog("启动耗时：" + mFinishTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
