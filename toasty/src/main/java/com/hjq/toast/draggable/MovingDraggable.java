package com.hjq.toast.draggable;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XToast
 * time   : 2019/01/04
 * desc   : 移动拖拽处理实现类
 */
public class MovingDraggable extends BaseDraggable {

    /**
     * 手指按下的坐标
     */
    private float mViewDownX;
    private float mViewDownY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下的位置（相对 View 的坐标）
                mViewDownX = event.getX();
                mViewDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 记录移动的位置（相对屏幕的坐标）
                float rawMoveX = event.getRawX() - getWindowInvisibleWidth();
                float rawMoveY = event.getRawY() - getWindowInvisibleHeight();

                // 更新移动的位置
                updateLocation(rawMoveX - mViewDownX, rawMoveY - mViewDownY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                break;
        }
        return false;
    }
}