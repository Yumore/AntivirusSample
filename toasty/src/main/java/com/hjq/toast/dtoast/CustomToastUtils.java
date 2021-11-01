package com.hjq.toast.dtoast;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.core.app.NotificationManagerCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Nathaniel
 */
public class CustomToastUtils {

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 6000;

    public static void enableLog(boolean enable) {
        RomUtils.enableLog = enable;
    }

    public static IToast make(Context context) {
        if (context == null) {
            return null;
        }
        //如果有通知权限，直接使用系统Toast
        //白名单中的机型没有通知权限时系统Toast也能正常展示
        if (NotificationManagerCompat.from(context).areNotificationsEnabled() || SystemToast.isValid4HookINotificationManager() || RomUtils.isWhiteList()) {
            Log.w("CustomToastUtils-make-31-", "Toast不能正常显示");
            return new SystemToast(context);
        } else {
            //否则使用自定义Toast
            if (context instanceof Activity && CustomToast.isBadChoice()) {
                //检测到DovaToast连续多次抛出token null is not valid异常时，直接启用ActivityToast
                return new ActivityToast(context);
            }
            return new CustomToast(context);
        }
    }

    /**
     * 终止并清除所有弹窗
     */
    public static void cancel() {
        CustomToast.cancelAll();
        SystemToast.cancelAll();
    }

    /**
     * 清除与{@param mActivity}关联的ActivityToast，避免窗口泄漏
     */
    public static void cancelActivityToast(Activity mActivity) {
        CustomToast.cancelActivityToast(mActivity);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DURATION_SHORT, DURATION_LONG})
    public @interface Duration {
    }
}
