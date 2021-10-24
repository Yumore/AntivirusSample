package com.nathaniel.sample.surface;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.sample.R;


/**
 * @author nathaniel
 */
public class SplashActivity extends AbstractActivity {

    @Override
    public void beforeInit() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.beforeInit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void loadData() {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, NavigateActivity.class));
            finish();
        }, 2_000);
    }
}
