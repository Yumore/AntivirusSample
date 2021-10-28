package com.hjq.toast.draggable;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.hjq.toast.XToast;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XToast
 * time   : 2019/01/04
 * desc   : 拖拽抽象类
 */
public abstract class BaseDraggable implements View.OnTouchListener {

    private XToast<?> mToast;
    private View mDecorView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;

    /**
     * Toast 显示后回调这个类
     */
    public void start(XToast<?> toast) {
        mToast = toast;
        mDecorView = toast.getDecorView();
        mWindowManager = toast.getWindowManager();
        mWindowParams = toast.getWindowParams();

        mDecorView.setOnTouchListener(this);
    }

    protected XToast<?> getXToast() {
        return mToast;
    }

    protected WindowManager getWindowManager() {
        return mWindowManager;
    }

    protected WindowManager.LayoutParams getWindowParams() {
        return mWindowParams;
    }

    protected View getRootView() {
        return mDecorView;
    }

    /**
     * 获取当前 Window 的宽度
     */
    protected int getWindowWidth() {
        Rect rect = new Rect();
        getRootView().getWindowVisibleDisplayFrame(rect);
        return rect.right - rect.left;
    }

    /**
     * 获取当前 Window 的高度
     */
    protected int getWindowHeight() {
        Rect rect = new Rect();
        getRootView().getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }

    /**
     * 获取窗口不可见的宽度，一般情况下为横屏状态下刘海的高度
     */
    protected int getWindowInvisibleWidth() {
        Rect rect = new Rect();
        mDecorView.getWindowVisibleDisplayFrame(rect);
        return rect.left;
    }

    /**
     * 获取窗口不可见的高度，一般情况下为状态栏的高度
     */
    protected int getWindowInvisibleHeight() {
        Rect rect = new Rect();
        mDecorView.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 更新悬浮窗的位置
     *
     * @param x x 坐标
     * @param y y 坐标
     */
    protected void updateLocation(float x, float y) {
        updateLocation((int) x, (int) y);
    }

    /**
     * 更新 WindowManager 所在的位置
     */
    protected void updateLocation(int x, int y) {
        // 屏幕默认的重心
        int screenGravity = Gravity.TOP | Gravity.START;
        // 判断本次移动的位置是否跟当前的窗口位置是否一致
        if (mWindowParams.gravity == screenGravity && mWindowParams.x == x && mWindowParams.y == y) {
            return;
        }

        mWindowParams.x = x;
        mWindowParams.y = y;
        // 一定要先设置重心位置为左上角
        mWindowParams.gravity = screenGravity;
        try {
            mWindowManager.updateViewLayout(mDecorView, mWindowParams);
        } catch (IllegalArgumentException e) {
            // 当 WindowManager 已经消失时调用会发生崩溃
            // IllegalArgumentException: View not attached to window manager
            e.printStackTrace();
        }
    }
}