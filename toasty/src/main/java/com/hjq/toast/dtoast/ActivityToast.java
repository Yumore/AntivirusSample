package com.hjq.toast.dtoast;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * 只展示在当前Activity的弹窗，不具有跨页面功能, 销毁Activity时需要主动清除相应的ActivityToast, 否则会造成窗口泄漏：/has leaked window/。
 * <p>
 * 注意：在Activity.onCreate()时展示ActivityToast依然会抛出Unable to add window -- token null is not valid; is your activity running，因为此时Activity尚未进入running状态
 *
 * @author Nathaniel
 */
public class ActivityToast extends CustomToast {

    /**
     * context非Activity时会抛出异常:Unable to add window -- token null is not valid; is your activity running?
     */
    public ActivityToast(@NonNull Context context) {
        super(context);
    }

    @Override
    public WindowManager.LayoutParams getWindowManagerParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.windowAnimations = android.R.style.Animation_Toast;
        // TODO:
        //      2018/11/20 考虑是否需要引入windowToken
        // layoutParams.token=((Activity)getContext()).getWindow().getDecorView().getWindowToken();
        layoutParams.gravity = getGravity();
        layoutParams.x = getXOffset();
        layoutParams.y = getYOffset();
        return layoutParams;
    }

    @Override
    public WindowManager getWindowManager() {
        //context非Activity时会抛出异常:Unable to add window -- token null is not valid; is your activity running?
        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager();
        } else {
            return null;
        }
    }
}
