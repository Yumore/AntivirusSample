package com.nathaniel.sample;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.nathaniel.baseui.BaseApplication;
import com.nathaniel.baseui.utility.ActivityCallback;
import com.nathaniel.sample.multidex.MultidexUtils;
import com.nathaniel.sample.starter.StarterTaskFive;
import com.nathaniel.sample.starter.StarterTaskFour;
import com.nathaniel.sample.starter.StarterTaskOne;
import com.nathaniel.sample.starter.StarterTaskThree;
import com.nathaniel.sample.starter.StarterTaskTwo;
import com.nathaniel.sample.utility.AppUtils;
import com.nathaniel.sample.utility.DefaultSingleton;
import com.nathaniel.sample.utility.SecondTask;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PriorityTask;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.ThreadManager;
import com.nathaniel.utility.greendao.DaoManager;
import com.wxy.appstartfaster.dispatcher.StarterTaskDispatcher;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/16/21 - 10:44 AM
 */
public class SampleApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        boolean isMainProcess = MultidexUtils.isMainProcess(base);
        if (isMainProcess && !MultidexUtils.isVMMultidexCapable()) {
            MultidexUtils.loadMultiDex(base);
        } else {
            MultidexUtils.preNewActivity();
        }
    }

    @Override
    public void initialize(@NonNull Context context) {
        super.initialize(context);
        AppUtils.getRunningTasks(getApplicationContext());
        SingletonUtils.getInstance(DaoManager.class).initialize(context);
        if (MultidexUtils.isMainProcess(context)) {
            StarterTaskDispatcher.getInstance()
                .setContext(this)
                .setShowLog(true)
                .setAllTaskWaitTimeOut(1000)
                .addAppStartTask(new StarterTaskTwo())
                .addAppStartTask(new StarterTaskFour())
                .addAppStartTask(new StarterTaskFive())
                .addAppStartTask(new StarterTaskThree())
                .addAppStartTask(new StarterTaskOne())
                .start()
                .await();
        }
        registerActivityLifecycleCallbacks(new ActivityCallback());
        DefaultSingleton.getInstance().initialized();
        Runnable runnable = () -> {
            LoggerUtils.logger("SampleApplication-initialize-72", "任务被执行了");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        ThreadManager.getInstance().executor(runnable, runnable, runnable, runnable);
        PriorityTask policyTask1 = new PriorityTask(100) {
            @Override
            public void run() {
                LoggerUtils.logger("SampleApplication-initialize-100");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        PriorityTask policyTask2 = new PriorityTask(33) {
            @Override
            public void run() {
                LoggerUtils.logger("SampleApplication-initialize-33");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        PriorityTask policyTask3 = new PriorityTask(5) {
            @Override
            public void run() {
                LoggerUtils.logger("SampleApplication-initialize-5");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadManager.getInstance().executor(policyTask1, 100);
        ThreadManager.getInstance().executor(policyTask2, 33);
        ThreadManager.getInstance().executor(policyTask3, 5);

        try {
            testDefault();
            testSamePriority();
            testRandomPriority();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SingletonUtils.getInstance(DaoManager.class).closeConnection();
    }

    public void testDefault() throws InterruptedException, ExecutionException {

        Future[] futures = new Future[20];
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < futures.length; i++) {
            int index = i;
            futures[i] = ThreadManager.getInstance().submit((Callable) () -> {
                Thread.sleep(10);
                buffer.append(index + ", ");
                return null;
            }, i);
        }
        for (Future future : futures) {
            future.get();
        }
        LoggerUtils.logger(buffer);
        LoggerUtils.logger("0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, ".equals(buffer.toString()));
    }


    public void testSamePriority() throws InterruptedException, ExecutionException {
        Future[] futures = new Future[10];
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < futures.length; i++) {
            futures[i] = ThreadManager.getInstance().submit(new SecondTask(i, 1, buffer), 1);
        }
        // 等待所有任务结束
        for (Future future : futures) {
            future.get();
        }
        LoggerUtils.logger(buffer);
        LoggerUtils.logger("01@00, 01@01, 01@02, 01@03, 01@04, 01@05, 01@06, 01@07, 01@08, 01@09, ".equals(buffer.toString()));
    }

    public void testRandomPriority() throws InterruptedException, ExecutionException {
        Future[] futures = new Future[20];
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < futures.length; i++) {
            int r = (int) (Math.random() * 100);
            futures[i] = ThreadManager.getInstance().submit(new SecondTask(i, r, buffer), r);
        }
        // 等待所有任务结束
        for (Future future : futures) {
            future.get();
        }

        buffer.append("01@00");
        LoggerUtils.logger(buffer);
        String[] split = buffer.toString().split(", ");
        // 从 2 开始, 因为前面的任务可能已经开始
        for (int i = 2; i < split.length - 1; i++) {
            String prefixString = split[i].split("@")[0];
            if (TextUtils.isEmpty(prefixString)) {
                prefixString = "0";
            }
            String suffixString = split[i + 1].split("@")[0];
            if (TextUtils.isEmpty(suffixString)) {
                suffixString = "0";
            }
            LoggerUtils.logger(Integer.parseInt(prefixString) >= Integer.parseInt(suffixString));
        }
    }
}
