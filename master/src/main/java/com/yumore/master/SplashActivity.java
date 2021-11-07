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

import com.nathaniel.baseui.surface.BaseActivity;
import com.nathaniel.sample.surface.NavigateActivity;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PreferencesUtils;
import com.yumore.master.databinding.ActivitySplashBinding;


/**
 * @author nathaniel
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private static final int DELAY_MILLS = 2000;
    private Class<?> clazz;

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
        }
    }

    @Override
    public void loadData() {
        LoggerUtils.logger(LoggerUtils.TAG, "WelcomeActivity-loadData-31-->",
            PreferencesUtils.getInstance(getApplicationContext()).getIntroduceEnable(),
            PreferencesUtils.getInstance(getApplicationContext()).getTractionEnable());

//        clazz = PreferencesUtils.getInstance(getApplicationContext()).getIntroduceEnable() ?
//            PreferencesUtils.getInstance(getApplicationContext()).getTractionEnable() ?
//                NavigateActivity.class : TractionActivity.class : IntroduceActivity.class;
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
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(layoutParams);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, NavigateActivity.class));
            finish();
        }, 3_000);

//        new Handler(getMainLooper()).postDelayed(() -> {
//            Intent intent = new Intent(getApplicationContext(), clazz);
//            startActivity(intent);
//            finish();
//        }, DELAY_MILLS);
    }
}
