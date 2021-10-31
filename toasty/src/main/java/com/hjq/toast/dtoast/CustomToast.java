package com.hjq.toast.dtoast;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.toast.R;


public class CustomToast implements IToast, Cloneable {
    /**
     * 记录DovaToast连续抛出token null is not valid异常的次数
     */
    static long Count4BadTokenException = 0;

    Context context;
    boolean toastShowing;
    private View contentView;
    private int priority;
    private long timestamp;
    private int animation = android.R.style.Animation_Toast;
    private int gravity = Gravity.BOTTOM | Gravity.CENTER;
    private int xOffset;
    private int yOffset;
    private int width = WindowManager.LayoutParams.WRAP_CONTENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int duration = CustomToastUtils.DURATION_SHORT;

    public CustomToast(@NonNull Context context) {
        this.context = context;
    }

    public static void cancelAll() {
        CustomToastHandler.instance().cancelAll();
    }

    public static void cancelActivityToast(Activity mActivity) {
        CustomToastHandler.instance().cancelActivityToast(mActivity);
    }

    /**
     * 当DovaToast连续出现token null is not valid异常时，不再推荐使用DovaToast
     */
    public static boolean isBadChoice() {
        return Count4BadTokenException >= 5;
    }

    @Override
    public void show() {
        //此时如果还未设置contentView则使用内置布局
        assertContentViewNotNull();
        CustomToastHandler.instance().add(this);
    }

    @Override
    public void showLong() {
        this.setDuration(CustomToastUtils.DURATION_LONG).show();
    }

    /**
     * 取消Toast,会清除队列中所有Toast任务
     * 因为TN中使用的是{@link this#clone()}，外部没有Toast队列中单个任务的引用，所以外部无法单独取消一个Toast任务
     */
    @Override
    public void cancel() {
        CustomToastHandler.instance().cancelAll();
    }

    protected WindowManager.LayoutParams getWindowManagerParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //targetSdkVersion>=26且运行在8.0以上系统时，TYPE_TOAST可能会addView()失败，所以如果此条件下应用已获取到悬浮窗权限则使用TYPE_APPLICATION_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Settings.canDrawOverlays(context)) {
            //为什么是使用TYPE_APPLICATION_OVERLAY？
            //因为8.0+系统，使用SYSTEM_ALERT_WINDOW 权限的应用无法再使用TYPE_PHONE、TYPE_SYSTEM_ALERT、TYPE_SYSTEM_OVERLAY等窗口类型来显示弹窗(permission denied for this window type)
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        layoutParams.height = this.height;
        layoutParams.width = this.width;
        layoutParams.windowAnimations = this.animation;
        layoutParams.gravity = this.gravity;
        layoutParams.x = this.xOffset;
        layoutParams.y = this.yOffset;
        layoutParams.dimAmount = 0.75f;
        return layoutParams;
    }

    protected WindowManager getWindowManager() {
        if (context == null) {
            return null;
        }
        return (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    public View getView() {
        return assertContentViewNotNull();
    }

    @Override
    public CustomToast setView(View mView) {
        if (mView == null) {
            RomUtils.log("contentView cannot be null!");
            return this;
        }
        this.contentView = mView;
        return this;
    }

    View getViewInternal() {
        return this.contentView;
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
    public CustomToast setDuration(@CustomToastUtils.Duration int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public CustomToast setAnimation(int animation) {
        this.animation = animation;
        return this;
    }

    @Override
    public CustomToast setGravity(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    @Override
    public CustomToast setGravity(int gravity) {
        return setGravity(gravity, 0, 0);
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public CustomToast setPriority(int mPriority) {
        this.priority = mPriority;
        return this;
    }

    @Override
    public IToast setText(int id, String text) {
        TextView textView = assertContentViewNotNull().findViewById(id);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    long getTimestamp() {
        return timestamp;
    }

    CustomToast setTimestamp(long mTimestamp) {
        timestamp = mTimestamp;
        return this;
    }

    /**
     * Toast引用的contentView的可见性
     *
     * @return toast是否正在展示
     */
    boolean isToastShowing() {
        return toastShowing && contentView != null && contentView.isShown();
    }

    @Override
    protected CustomToast clone() {
        CustomToast customToast = null;
        try {
            customToast = (CustomToast) super.clone();
            customToast.context = this.context;
            customToast.contentView = this.contentView;
            customToast.duration = this.duration;
            customToast.animation = this.animation;
            customToast.gravity = this.gravity;
            customToast.height = this.height;
            customToast.width = this.width;
            customToast.xOffset = this.xOffset;
            customToast.yOffset = this.yOffset;
            customToast.priority = this.priority;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return customToast;
    }
}
