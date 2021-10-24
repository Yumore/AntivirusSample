package com.nathaniel.sample;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.nathaniel.sample.utility.AppUtils;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.helper.InitializeHelper;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/16/21 - 10:44 AM
 */
public class SampleApplication extends Application implements InitializeHelper {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(getApplicationContext());
    }

    @Override
    public void initialize(@NonNull Context context) {
        ContextHelper.getInstance().initialize(context);
        AppUtils.getRunningTasks(getApplicationContext());
    }
}
