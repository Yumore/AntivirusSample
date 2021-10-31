package com.hjq.toast.dtoast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.hjq.toast.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SystemToast implements IToast, Cloneable {
    private static Object iNotificationManagerProxy;
    /**
     * mToast在SystemToastHandler#displayToast(SystemToast)}中才被初始化
     */
    private Toast toast;
    private Context context;
    private View contentView;
    private int priority;
    private int animation = android.R.style.Animation_Toast;
    private int gravity = Gravity.BOTTOM | Gravity.CENTER;
    private int xOffset;
    private int yOffset;
    private int duration = CustomToastUtils.DURATION_SHORT;

    public SystemToast(@NonNull Context context) {
        this.context = context;
    }

    public static void cancelAll() {
        SystemToastHandler.instance().cancelAll();
    }

    /**
     * 捕获8.0之前Toast的BadTokenException，Google在Android 8.0的代码提交中修复了这个问题
     */
    private static void hookHandler(Toast toast) {
        if (toast == null || Build.VERSION.SDK_INT >= 26) {
            return;
        }
        try {
            @SuppressLint("SoonBlockedPrivateApi")
            Field sField_TN = Toast.class.getDeclaredField("mTN");
            sField_TN.setAccessible(true);
            Field sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
            sField_TN_Handler.setAccessible(true);

            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler) sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn, new SafelyHandler(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Toast动画
     */
    private static void setupToastAnim(Toast toast, int animRes) {
        if (toast == null || Build.VERSION.SDK_INT >= 28) {
            return;
        }
        try {
            Object mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = animRes;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private static Object getField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    /**
     * hook INotificationManager
     */
    private static void hookINotificationManager(Toast toast, @NonNull Context mContext) {
        if (toast == null) {
            return;
        }
        if (NotificationManagerCompat.from(mContext).areNotificationsEnabled() || RomUtils.isWhiteList()) {
            return;
        }
        if (isValid4HookINotificationManager()) {
            if (iNotificationManagerProxy != null) {
                return;//代理不为空说明之前已设置成功
            }
            try {
                //生成INotificationManager代理
                @SuppressLint("SoonBlockedPrivateApi")
                Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
                getServiceMethod.setAccessible(true);
                final Object iNotificationManagerObj = getServiceMethod.invoke(null);

                Class<?> iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RomUtils.log(method.getName());
                        if ("enqueueToast".equals(method.getName()) || "enqueueToastEx".equals(method.getName())//华为p20 pro上为enqueueToastEx
                            || "cancelToast".equals(method.getName())
                        ) {
                            args[0] = "android";
                        }
                        return method.invoke(iNotificationManagerObj, args);
                    }
                });

                //使INotificationManager代理生效
                @SuppressLint("SoonBlockedPrivateApi")
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(toast, iNotificationManagerProxy);
            } catch (Exception e) {
                iNotificationManagerProxy = null;
                RomUtils.log("hook INotificationManager error:" + e.getMessage());
            }
        }
    }

    /**
     * 建议只在8.0和8.1版本下采用Hook INotificationManager的方案
     * 因为8.0以下时DovaToast可以完美处理，而9.0及以上时Android不允许使用非公开api
     */
    public static boolean isValid4HookINotificationManager() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1;
    }

    @Override
    public void show() {
        //此时如果还未设置contentView则使用内置布局
        assertContentViewNotNull();
        SystemToastHandler.instance().add(this);
    }

    @Override
    public void showLong() {
        this.setDuration(CustomToastUtils.DURATION_LONG).show();
    }

    @Override
    public void cancel() {
        SystemToastHandler.instance().cancelAll();
    }

    /**
     * 不允许被外部调用
     */
    void showInternal() {
        if (context == null || contentView == null) {
            return;
        }
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(gravity, xOffset, yOffset);
        if (duration == CustomToastUtils.DURATION_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        hookHandler(toast);
        hookINotificationManager(toast, context);
        setupToastAnim(toast, this.animation);
        toast.show();
    }

    void cancelInternal() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    @Override
    public View getView() {
        return assertContentViewNotNull();
    }

    @Override
    public SystemToast setView(View mView) {
        if (mView == null) {
            RomUtils.log("contentView cannot be null!");
            return this;
        }
        this.contentView = mView;
        return this;
    }

    private View assertContentViewNotNull() {
        if (contentView == null) {
            contentView = View.inflate(context, R.layout.layout_toast, null);
        }
        return contentView;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public SystemToast setDuration(@CustomToastUtils.Duration int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public SystemToast setAnimation(int animation) {
        this.animation = animation;
        return this;
    }

    @Override
    public SystemToast setGravity(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        return this;
    }

    public int getGravity() {
        return this.gravity;
    }

    @Override
    public SystemToast setGravity(int gravity) {
        setGravity(gravity, 0, 0);
        return this;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public SystemToast setPriority(int mPriority) {
        this.priority = mPriority;
        return this;
    }

    @Override
    public IToast setText(int id, String text) {
        TextView tv = assertContentViewNotNull().findViewById(id);
        if (tv != null) {
            tv.setText(text);
        }
        return this;
    }

    @Override
    public SystemToast clone() {
        SystemToast systemToast = null;
        try {
            systemToast = (SystemToast) super.clone();
            systemToast.context = this.context;
            systemToast.contentView = this.contentView;
            systemToast.duration = this.duration;
            systemToast.animation = this.animation;
            systemToast.gravity = this.gravity;
            systemToast.xOffset = this.xOffset;
            systemToast.yOffset = this.yOffset;
            systemToast.priority = this.priority;
        } catch (CloneNotSupportedException mE) {
            mE.printStackTrace();
        }
        return systemToast;
    }

}
