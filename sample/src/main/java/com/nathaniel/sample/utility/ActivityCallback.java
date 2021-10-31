package com.nathaniel.sample.utility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nathaniel.utility.LoggerUtils;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/24 - 17:28
 */
public class ActivityCallback implements Application.ActivityLifecycleCallbacks {
    private long startTime;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        LoggerUtils.logger("ActivityCallback-onActivityCreated-23-", activity.getClass().getCanonicalName(), savedInstanceState);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        LoggerUtils.logger("ActivityCallback-onActivityStarted-28-", activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        LoggerUtils.logger("ActivityCallback-onActivityResumed-33-", activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        LoggerUtils.logger("ActivityCallback-onActivityPaused-38-", activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        LoggerUtils.logger("ActivityCallback-onActivityStopped-43-", activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        LoggerUtils.logger("ActivityCallback-onActivitySaveInstanceState-48-", activity.getClass().getCanonicalName());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        LoggerUtils.logger("ActivityCallback-onActivityDestroyed-53-", activity.getClass().getCanonicalName());
    }
}
