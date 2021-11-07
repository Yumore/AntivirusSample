package com.yumore.master;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nathaniel.baseui.surface.BaseActivity;
import com.nathaniel.sample.surface.NavigateActivity;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PreferencesUtils;
import com.nathaniel.utility.RouterConstants;
import com.yumore.master.databinding.ActivitySplashBinding;


/**
 * @author nathaniel
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private static final int DELAY_MILLS = 2000;

    @Override
    protected ActivitySplashBinding initViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    public void beforeInit() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.beforeInit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.systemBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(layoutParams);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void loadData() {
        LoggerUtils.logger("WelcomeActivity-loadData-31-->",
            PreferencesUtils.getInstance(getApplicationContext()).getIntroduceEnable(),
            PreferencesUtils.getInstance(getApplicationContext()).getTractionEnable());
    }

    @Override
    protected boolean hideNavigation() {
        return true;
    }

    @Override
    protected boolean keyboardEnable() {
        return false;
    }

    @Override
    public void bindView() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (PreferencesUtils.getInstance(getApplicationContext()).getIntroduceEnable()) {
                ARouter.getInstance().build(RouterConstants.INTRODUCE_HOME).navigation();
            } else if (PreferencesUtils.getInstance(getApplicationContext()).getTractionEnable()) {
                ARouter.getInstance().build(RouterConstants.TRACTION_HOME).navigation();
            } else {
                startActivity(new Intent(SplashActivity.this, NavigateActivity.class));
            }
            finish();
        }, DELAY_MILLS);
    }
}
