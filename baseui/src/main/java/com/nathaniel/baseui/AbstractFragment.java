package com.nathaniel.baseui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.nathaniel.baseui.utility.FragmentCallback;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.binding
 * @datetime 2021/3/31 - 19:57
 */
public abstract class AbstractFragment<VB extends ViewBinding> extends Fragment implements IViewBinding {
    private static final String TAG = AbstractFragment.class.getSimpleName();
    protected VB viewBinding;
    private AlertDialog alertDialog;
    private Context context;
    private FragmentActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentActivity = (FragmentActivity) context;
        requireFragmentManager().registerFragmentLifecycleCallbacks(new FragmentCallback(), true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = initViewBinding(inflater, container, false);
        loadData();
        bindView();
        return viewBinding.getRoot();
    }

    protected abstract VB initViewBinding(LayoutInflater inflater, ViewGroup viewGroup, boolean attachToRoot);

    @Override
    public boolean immersionEnable() {
        return true;
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
    public void beforeInit() {
        LoggerUtils.logger(TAG, "beforeUI()");
        if (immersionEnable()) {
            initImmersion();
        }
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

    protected final Fragment getFragment() {
        return this;
    }


    @Override
    public void showLoading(CharSequence message) {
        dismissLoading();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_dialog_loading, null);
        TextView textView = view.findViewById(R.id.loading_dialog_message);
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
        } else {
            textView.setVisibility(View.GONE);
        }
        alertDialog = new AlertDialog.Builder(context, R.style.CustomDialog)
            .setCancelable(false)
            .setView(view)
            .create();
        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = .35f;
            dialogWindow.setAttributes(layoutParams);
        }
    }

    @Override
    public void dismissLoading() {
        if (!EmptyUtils.isEmpty(alertDialog) && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EmptyUtils.isEmpty(viewBinding)) {
            viewBinding = null;
        }
    }
}
