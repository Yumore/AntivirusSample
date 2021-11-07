package com.nathaniel.baseui.surface;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.nathaniel.baseui.R;
import com.nathaniel.baseui.callback.IViewBinding;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;


/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.binding
 * @datetime 2021/3/31 - 19:50
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements IViewBinding {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected boolean firstTime = true;
    protected VB viewBinding;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeInit();
        super.onCreate(savedInstanceState);
        viewBinding = initViewBinding();
        setContentView(viewBinding.getRoot());
        loadData();
        bindView();
    }

    /**
     * ViewBinding 初始化
     *
     * @return VB extends ViewBinding
     */
    protected abstract VB initViewBinding();

    @Override
    public void beforeInit() {
        LoggerUtils.logger(Thread.currentThread().getStackTrace()[1].getMethodName());
        if (immersionEnable()) {
            initImmersion();
        }
    }

    @Override
    public void initImmersion() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .transparentNavigationBar()
            .transparentBar()
            .statusBarColor(statusBarColor())
            .navigationBarColor(navigationColor())
            .statusBarDarkFont(darkModeEnable())
            .navigationBarDarkIcon(darkModeEnable())
            .autoDarkModeEnable(darkModeEnable())
            .flymeOSStatusBarFontColor(statusFontColor())
            .fullScreen(fitsSystemWindows())
            .hideBar(getBarHide())
            .fitsSystemWindows(fitsSystemWindows())
            .navigationBarEnable(navigationEnable())
            .navigationBarWithKitkatEnable(navigationEnable())
            .navigationBarWithEMUI3Enable(navigationEnable())
            .keyboardEnable(keyboardEnable())
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            .setOnKeyboardListener((popupEnable, keyboardHeight) -> LoggerUtils.logger(TAG, popupEnable, keyboardHeight))
            .setOnNavigationBarListener(show -> LoggerUtils.logger(TAG, show))
            .setOnBarListener(barProperties -> LoggerUtils.logger(TAG, barProperties))
            .init();
    }

    @Override
    public boolean immersionEnable() {
        return true;
    }

    protected int statusBarColor() {
        return R.color.common_color_theme;
    }

    protected int navigationColor() {
        return R.color.common_color_transparent;
    }

    protected int statusFontColor() {
        return R.color.common_color_black_light;
    }

    protected boolean darkModeEnable() {
        return true;
    }

    protected boolean fitsSystemWindows() {
        return true;
    }

    protected boolean keyboardEnable() {
        return true;
    }

    private BarHide getBarHide() {
        return hideNavigation() ? BarHide.FLAG_HIDE_NAVIGATION_BAR : BarHide.FLAG_SHOW_BAR;
    }

    protected boolean hideNavigation() {
        return false;
    }

    protected boolean navigationEnable() {
        return true;
    }

    protected final FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void showLoading(CharSequence message) {
        dismissLoading();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_dialog_loading, null);
        TextView textView = view.findViewById(R.id.loading_dialog_message);
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        alertDialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialog)
            .setCancelable(false)
            .setView(view)
            .create();
        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = .35f;
            dialogWindow.setAttributes(layoutParams);
        }
    }

    protected void showLoading(CharSequence message, boolean enable) {
        if (!enable) {
            return;
        }
        showLoading(message);
    }

    @Override
    public void dismissLoading() {
        if (!EmptyUtils.isEmpty(alertDialog) && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!EmptyUtils.isEmpty(viewBinding)) {
            viewBinding = null;
        }
        System.gc();
    }

    protected <V extends View> V obtainView(View parent, @IdRes int viewId) {
        return parent.findViewById(viewId);
    }
}
