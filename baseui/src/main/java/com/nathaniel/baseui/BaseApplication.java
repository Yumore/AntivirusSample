package com.nathaniel.baseui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.helper.InitializeHelper;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.baseui
 * @datetime 2021/10/28 - 12:49
 */
public class BaseApplication extends Application implements InitializeHelper {
    protected Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initialize(application);
    }

    @Override
    public void initialize(@NonNull Context context) {
        ContextHelper.getInstance().initialize(context);
        ToastUtils.init(application);
    }
}