package com.nathaniel.sample;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.nathaniel.baseui.utility.ActivityCallback;
import com.nathaniel.sample.multidex.MultidexUtils;
import com.nathaniel.sample.starter.StarterTaskFive;
import com.nathaniel.sample.starter.StarterTaskFour;
import com.nathaniel.sample.starter.StarterTaskOne;
import com.nathaniel.sample.starter.StarterTaskThree;
import com.nathaniel.sample.starter.StarterTaskTwo;
import com.nathaniel.sample.utility.AppUtils;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.greendao.DaoManager;
import com.nathaniel.utility.helper.InitializeHelper;
import com.wxy.appstartfaster.dispatcher.StarterTaskDispatcher;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/16/21 - 10:44 AM
 */
public class SampleApplication extends Application implements InitializeHelper {
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
    public void onCreate() {
        super.onCreate();
        initialize(getApplicationContext());
    }

    @Override
    public void initialize(@NonNull Context context) {
        ContextHelper.getInstance().initialize(context);
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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SingletonUtils.getInstance(DaoManager.class).closeConnection();
    }
}
